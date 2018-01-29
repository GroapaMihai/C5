package client_app.gui.views;

import javax.swing.*;

import org.jdatepicker.impl.JDatePickerImpl;

import client_app.api.annotations.Clear;
import client_app.gui.components.JNumberTextField;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewCleaner {
    private static ViewCleaner instance = null;

    private ViewCleaner() {
    }

    public static ViewCleaner getInstance() {
        if (instance == null) {
            instance = new ViewCleaner();
        }

        return instance;
    }

    private List<Field> getFieldsByAnnotations(Class clazz, Class annotation) {
        List<Field> fields = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.getAnnotation(annotation) != null) {
                fields.add(declaredField);
            }
        }

        return fields;
    }

    private boolean isInteger(String s) {
        try {
        	Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }

        return true;
    }

    private boolean isDouble(String s) {
        try {
        	Double.parseDouble(s);
        } catch(NumberFormatException e) { 
            return false; 
        }

        return true;
    }

    public void clearView(View view) {
        Class clazz = view.getClass();
        List<Field> fields = getFieldsByAnnotations(clazz, Clear.class);

        try {
        	for (Field field : fields) {
                field.setAccessible(true);

                if (field.getType() == JTextField.class) {
                    JTextField textField = (JTextField) field.get(view);
                    textField.setText("");
                } else if (field.getType() == JNumberTextField.class) {
                	Clear clear = field.getAnnotation(Clear.class);
                    JNumberTextField numberTextField = (JNumberTextField) field.get(view);

                    if (isInteger(clear.defaultValue())) {
                    	numberTextField.setInt(Integer.valueOf(clear.defaultValue()));
                    } else if (isDouble(clear.defaultValue())) {
                    	numberTextField.setDouble(Double.valueOf(clear.defaultValue()));
                    }
                } else if (field.getType() == JPasswordField.class) {
                	JPasswordField passwordField = (JPasswordField) field.get(view);
                	passwordField.setText("");
                } else if(field.getType() == JDatePickerImpl.class) {
                	Clear clear = field.getAnnotation(Clear.class);
        			JDatePickerImpl datePicker = (JDatePickerImpl) field.get(view);
        			String defaultDateAsString = clear.defaultValue();
        			String[] tokensOfDate = defaultDateAsString.split("-");
        			
        			if (tokensOfDate.length >= 3) {
        				datePicker.getModel().setDate(
    						Integer.valueOf(tokensOfDate[0]),
    						Integer.valueOf(tokensOfDate[1]) - 1,
    						Integer.valueOf(tokensOfDate[2])
						);	
        			}
        		} else if (field.getType() == JComboBox.class) {
        			JComboBox comboBox = (JComboBox) field.get(view);
        			comboBox.removeAllItems();
        		} else if (field.getType() == JSpinner.class) {
        			Clear clear = field.getAnnotation(Clear.class);

        			java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    					.parse(clear.defaultValue());

        			JSpinner spinner = (JSpinner) field.get(view);
        			spinner.getModel().setValue(utilDate);
        		} else if (field.getType() == JNumberTextField.class) {
        			Clear clear = field.getAnnotation(Clear.class);
        			
        			JNumberTextField numberTextField = (JNumberTextField) field.get(view);
        			numberTextField.setInt(Integer.valueOf(clear.defaultValue()));
        		} else if (field.getType() == JTextArea.class) {
        			Clear clear = field.getAnnotation(Clear.class);
        			
        			JTextArea textArea = (JTextArea) field.get(view);
        			textArea.setText(clear.defaultValue());
        		}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
