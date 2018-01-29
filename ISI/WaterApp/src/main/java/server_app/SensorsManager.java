package server_app;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import client_app.appl.domain.entities.Sample;
import client_app.appl.domain.entities.User;
import client_app.api.annotations.Digits;
import client_app.api.annotations.Range;
import client_app.appl.dao.SampleDao;
import client_app.appl.dao.UserDao;
import client_app.appl.dao.WaterSensorDao;
import client_app.appl.domain.entities.WaterSensor;
import client_app.mail.WaterMonitoringMailSender;
import client_app.mail.WaterMonitoringOutOfRangeSampleDataMail;
import client_app.mail.WaterMonitoringResetPasswordMail;

public class SensorsManager {
	private static SensorsManager instance = null;
	private final double stepPercent = 0.15;
	private final int hoursBetweenMails = 24;
	private final int minutesBetweenMails = 0;
	private final int secondsBetweenMails = 0;

	private UserDao userDao;
	private SampleDao sampleDao;
	private WaterSensorDao waterSensorDao;
	private List<WaterSensor> waterSensors;

	private SensorsManager() {
		userDao = UserDao.getInstance();
		sampleDao = SampleDao.getInstance();
		waterSensorDao = WaterSensorDao.getInstance();
	}

	public static SensorsManager getInstance() {
		if (instance == null) {
			instance = new SensorsManager();
		}

		return instance;
	}

	/**
	 * Pornind de la timestamp-ul referinta "timestamp", adauga "hours" ore, "minutes" minute, 
	 * "seconds" secunde si intoarce valoarea de timp obtinuta.
	 * @param timestamp
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	private Timestamp addTimeToTimestamp(Timestamp timestamp, int hours, int minutes, int seconds) {
	    Calendar calendar = Calendar.getInstance();

	    calendar.setTimeInMillis(timestamp.getTime());
	    calendar.add(Calendar.HOUR, hours);
	    calendar.add(Calendar.MINUTE, minutes);
	    calendar.add(Calendar.SECOND, seconds);

	    return new Timestamp(calendar.getTime().getTime());                
	}

	/**
	 * Verifica daca "value" se afla in parametrii normali ai proprietatii "fieldRange":
	 * <br>
	 * Altfel spus, value cuprins in intervalul [fieldRange.normal_min(), fieldRange.normal_max()].
	 * @param value
	 * @param fieldRange
	 * @return
	 */
	private boolean valueInNormalRange(Object value, Range fieldRange) {
		double minNormal = fieldRange.normal_min();
		double maxNormal = fieldRange.normal_max();
		double doubleValue;
		int intValue;

		if (value instanceof Double) {
			doubleValue = (Double) value;
			return doubleValue >= minNormal && doubleValue <= maxNormal;
		} else if (value instanceof Integer) {
			intValue = (Integer) value;
			return intValue >= minNormal && intValue <= maxNormal;
		}

		return false;
	}

	/**
	 * Verifica daca a trecut un interval de "hoursBetweenMails" ore,
	 * "minutesBetweenMails" minute si "secondsBetweenMails" secunde fata
	 * de momentul de referinta "lastEmailTime" si intoarce true in caz afirmativ, false altfel.
	 * @param lastEmailTime
	 * @return
	 */
	private boolean timeForNewEmail(Timestamp lastEmailTime) {
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		Timestamp timeOfNewEmail;
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(lastEmailTime);
		timeOfNewEmail = addTimeToTimestamp(
			lastEmailTime,
			hoursBetweenMails,
			minutesBetweenMails,
			secondsBetweenMails
		);

		return currentTime.after(timeOfNewEmail);
	}

	private double getRandomDoubleInRange(double min, double max) {		
		return ThreadLocalRandom.current().nextDouble(min, max);
	}

	private double truncateDouble(double x, int decimals)
	{
		if (x > 0) {
	        return new BigDecimal(String.valueOf(x)).setScale(decimals, BigDecimal.ROUND_FLOOR).doubleValue();
	    } else {
	        return new BigDecimal(String.valueOf(x)).setScale(decimals, BigDecimal.ROUND_CEILING).doubleValue();
	    }
	}

	/**
	 * Obtine random, cu o anumita toleranta, o noua valoare pentru un parametru masurat de senzor,
	 * pornind de la valoarea veche si tinand cont de constrangerile de interval specificate
	 * prin "fieldRange".
	 * <br>
	 * De asemena, se asigura corespondenta cu modelul de date din baza,
	 * specificand numarul de cifre maxim admis pentru partea intreaga si cea zecimala prin
	 * parametrul "fieldDigits".
	 */
	private Object getNewValueForSampleField(Object value, Range fieldRange, Digits fieldDigits) {
		Double min = fieldRange.min();
		Double max = fieldRange.max();
		Double normalMin = fieldRange.normal_min();
		Double normalMax = fieldRange.normal_max();
		Double maxStep = stepPercent * (normalMax - normalMin);
		Double step;
		Double doubleValue;
		Integer intValue;

		if (value instanceof Double) {
			doubleValue = (Double) value;
			step = getRandomDoubleInRange(-maxStep, maxStep);
			doubleValue += step;
			doubleValue = Math.max(min, doubleValue);
			doubleValue = Math.min(max, doubleValue);

			// ma asigur ca se incadreaza ca parte intreaga in modelul de date
			doubleValue %= Math.pow(10, fieldDigits.integers());

			// trunchiez la numarul de zecimale maxim permis pentru parametrul masurat
			doubleValue = truncateDouble(doubleValue, fieldDigits.decimals());

			return doubleValue;
		} else if (value instanceof Integer) {
			intValue = (Integer) value;
			step = getRandomDoubleInRange(-maxStep, maxStep);
			intValue += step.intValue();
			intValue = Math.max(min.intValue(), intValue);
			intValue = Math.min(max.intValue(), intValue);

			// ma asigur ca se incadreaza ca parte intreaga in modelul de date
			intValue %= (int) Math.pow(10, fieldDigits.integers());

			return intValue;
		}

		return value;
	}

	/**
	 * Calculeaza noi valori pentru fiecare dintre parametrii monitorizati de un senzor,
	 * trimite mail de avertizare pentru detinatorul senzorului in cazul unor valori
	 * in afara limitelor normale si daca a trecut suficient timp de la ultimul trimis.
	 * @param lastSample
	 * @param waterSensor
	 * @param waterSensorOwner
	 * @return
	 */
	private Sample getNewSample(Sample lastSample, WaterSensor waterSensor, User waterSensorOwner) {
		WaterMonitoringOutOfRangeSampleDataMail mail;
		Field[] sampleFields;
		Range fieldRange;
		Digits fieldDigits;
		Object fieldValue;
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());

		// obtin field-urile carora vreau sa le simulez comportamentul
		sampleFields = Sample.class.getDeclaredFields();

		for (Field field : sampleFields) {
			field.setAccessible(true);

			if (!(field.isAnnotationPresent(Range.class))) {
				continue;
			}

			if (!(field.isAnnotationPresent(Digits.class))) {
				continue;
			}

			fieldRange = field.getAnnotation(Range.class);
			fieldDigits = field.getAnnotation(Digits.class);

			try {
				fieldValue = field.get(lastSample);
				fieldValue = getNewValueForSampleField(fieldValue, fieldRange, fieldDigits);
				field.set(lastSample, fieldValue);

				// daca valoarea pentru field depaseste limitele normale
				// si daca a trecut un anumit timp de la ultimul email trimis,
				// trimit un nou email de avertizare
				if (!valueInNormalRange(fieldValue, fieldRange) && timeForNewEmail(waterSensorOwner.getLastReceivedEmailTime())) {
					waterSensorOwner.setLastReceivedEmailTime(currentTime);
					userDao.update(waterSensorOwner);

		    		mail = new WaterMonitoringOutOfRangeSampleDataMail();
		    		mail.setMessageWithDetails(
	    				lastSample,
	    				waterSensor,
	    				waterSensorOwner
    				);

		    		if (!WaterMonitoringMailSender.getInstance().send(waterSensorOwner.getEmail(), mail)) {
		    			System.out.println("Email send failed for user " + waterSensorOwner.getEmail() + "!");
		    		} else {
		    			System.out.println("Email sent succesfully at user " + waterSensorOwner.getEmail() + "!");
		    		}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return lastSample;
	}

	private void takeSamples(Sample lastSample, WaterSensor waterSensor, User waterSensorOwner) {
		Timestamp sampleRate = waterSensor.getSampleRate();
		Timestamp lastSampleTime = lastSample.getSampleTime();
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());

		// extrag ore, minute, secunde din sample rate
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sampleRate);
		int hours = calendar.get(Calendar.HOUR);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);

		// calculez noul sampleTime adaugand sample rate la timpul ultimei masuratori
		lastSampleTime = addTimeToTimestamp(lastSampleTime, hours, minutes, seconds);

		// urmatorul sample e dincolo de timpul prezent (masuratoarea se va face in viitor)
		if (lastSampleTime.after(currentTime)) {
			return;
		}

		// "calculez" valorile afisate de senzor la noul sample si il salvez in baza
		lastSample = getNewSample(lastSample, waterSensor, waterSensorOwner);
		lastSample.setSampleTime(lastSampleTime);
		lastSample = sampleDao.insert(lastSample);

		takeSamples(lastSample, waterSensor, waterSensorOwner);
	}
	
	/**
	 * Simuleaza masuratorile facute de senzori. Se porneste de la ultima proba luata de
	 * fiecare senzor, se adauga "sampleRate" (interval de timp specific fiecarui senzor)
	 * la timpul ultimei masuratori, si daca timpul obtinut este in trecut fata de momentul actual,
	 * se simuleaza un nou set de parametrii, se creeaza un nou sample asociat senzorului,
	 * avand ca timp al masuratorii momentul prezent si se salveaza in baza.
	 * Procesul se opreste pentru un senzor cand "lastSampleTime" + "sampleRate"
	 * depaseste momentul curent.
	 */
	public void simulate() {
		waterSensors = waterSensorDao.findAll();
		Sample lastSample;
		User waterSensorOwner;

		for (WaterSensor waterSensor : waterSensors) {
			waterSensorOwner = userDao.findById(waterSensor.getAuthorityId());
			lastSample = sampleDao.getLastSampleOfWaterSensor(waterSensor);
			takeSamples(lastSample, waterSensor, waterSensorOwner);
		}
	}
}
