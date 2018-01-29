package client_app.gui.components;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

import org.jdatepicker.impl.JDatePickerImpl;

public class DateLabelFormatter extends AbstractFormatter {
	private static final long serialVersionUID = 1L;
	private static DateLabelFormatter instance = null;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private DateLabelFormatter() {
    }
    
    public static DateLabelFormatter getInstance() {
    	if (instance == null) {
    		instance = new DateLabelFormatter();
    	}
    	
    	return instance;
    }
    
    @Override
    public Object stringToValue(String text) throws ParseException {
    	return dateFormatter.parseObject(text);
    }
 
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
         
        return "";
    }

	public Timestamp extractDateFromDatePicker(JDatePickerImpl datePicker) throws ParseException {	    
	    return new Timestamp(dateFormatter.parse(datePicker.getJFormattedTextField().getText()).getTime());
	}
}
