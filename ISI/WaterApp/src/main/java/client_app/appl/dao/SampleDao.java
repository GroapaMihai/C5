package client_app.appl.dao;

import java.util.List;

import client_app.api.em.EntityManager;
import client_app.api.em.EntityManagerImpl;
import client_app.api.em.EntityUtils;
import client_app.appl.domain.entities.Sample;
import client_app.appl.domain.entities.WaterSensor;

public class SampleDao implements BaseDao<Sample> {
	private static SampleDao instance = null;
    private EntityManager em;

    private SampleDao() {
        em = new EntityManagerImpl();
    }
    
    public static SampleDao getInstance() {
    	if (instance == null) {
    		instance = new SampleDao();
    	}
    	
    	return instance;
    }
    
	@Override
	public Sample findById(Integer id) {
		return em.findById(Sample.class, id);
	}

	@Override
	public Integer getNextIdVal(String columnIdName) {
		return em.getNextIdVal(EntityUtils.getTableName(Sample.class), columnIdName);
	}

	@Override
	public Sample insert(Sample entity) {
		return (Sample) em.insert(entity);
	}

	@Override
	public List<Sample> findAll() {
		return em.findAll(Sample.class);
	}

	@Override
	public Sample update(Sample entity) {
		return em.update(entity);
	}

	@Override
	public void delete(Sample entity) {
		em.delete(entity);
	}

	@Override
	public List<Sample> findMatchingEntities(Sample patternEntity) {
		return em.findMatchingEntities(Sample.class, patternEntity);
	}

	public Sample getLastSampleOfWaterSensor(WaterSensor waterSensor) {
		List<Sample> matchingSamples;
		Sample lastSample = new Sample();
		
		lastSample.setWaterSensorId(waterSensor.getId());
		matchingSamples = findMatchingEntities(lastSample);
		
		lastSample = matchingSamples.stream().max((s1, s2) -> s1.getSampleTime().compareTo(s2.getSampleTime())).get();
		
		return lastSample;
	}

	/**
	 * Intoarce o lista cu toate mostrele obtinute de "waterSensor", ordonata descrescator dupa timp.
	 * @param waterSensor
	 * @return
	 */
	public List<Sample> getSamplesOfWaterSensorOrderedByTime(WaterSensor waterSensor) {
		List<Sample> matchingSamples;
		Sample sample = new Sample();
		
		sample.setWaterSensorId(waterSensor.getId());
		matchingSamples = findMatchingEntities(sample);

		matchingSamples.sort((Sample s1, Sample s2) -> s2.getSampleTime().compareTo(s1.getSampleTime()));
		
		return matchingSamples;
	}
}