package client_app.api.em;

import client_app.api.annotations.Column;
import client_app.api.annotations.Id;
import client_app.api.annotations.Table;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialBlob;

public class EntityUtils {

    public static String getTableName(Class entityClass) {
        Table tableAnnotation = (Table) entityClass.getAnnotation(Table.class);
        return "".equals(tableAnnotation.name()) ? entityClass.getClass().getSimpleName() : tableAnnotation.name();
    }

    public static List<ColumnInfo> getColumns(Class entityClass) {
        List<ColumnInfo> columns = new ArrayList<>();

        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            Id id = field.getAnnotation(Id.class);

            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setColumnName(field.getName());
            columnInfo.setColumnType(field.getType());

            if (column == null && id == null) {
                continue;
            }

            if (column != null) {
                columnInfo.setDbColumnName(column.name());
            } else {
                columnInfo.setDbColumnName(id.name());
                columnInfo.setId(true);
            }

            columns.add(columnInfo);
        }
        return columns;
    }

    public static <T> List<ColumnInfo> getNotNullColumnsOfEntity(Class entityClass, T entity) {
        List<Field> notNullFieldsOfEntity = EntityUtils.getFieldsByAnnotation(entityClass, Column.class);
        List<ColumnInfo> columns = new ArrayList<>();
        Column column;
        ColumnInfo columnInfo;

        notNullFieldsOfEntity.forEach(field -> field.setAccessible(true));        
        notNullFieldsOfEntity = notNullFieldsOfEntity.stream()
        	.filter(field -> {
				try {
					return field.get(entity) != null;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			})
        	.collect(Collectors.toList());
        
        for (Field field : notNullFieldsOfEntity) {
        	column = field.getAnnotation(Column.class);
            
        	columnInfo = new ColumnInfo();
            columnInfo.setColumnName(field.getName());
            columnInfo.setColumnType(field.getType());
            
        	if (column != null) {
        		 columnInfo.setDbColumnName(column.name());
        	}
        	
        	if (entity != null) {
        		try {
					columnInfo.setValue(field.get(entity));
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        	
        	columns.add(columnInfo);
        }
        
    	return columns;
    }

    public static Object castFromSqlType(Object value, Class wantedType) {
        if (value != null) {
            if (value instanceof BigDecimal) {
                BigDecimal bdValue = (BigDecimal) value;
                return wantedType.equals(Integer.class) ? bdValue.intValue() :
                        wantedType.equals(Long.class) ? bdValue.longValue() :
                                wantedType.equals(Float.class) ? bdValue.floatValue() :
                                        wantedType.equals(Double.class) ? bdValue.doubleValue() : value;
            } else if (value instanceof byte[]) {
            	try {
					return new SerialBlob((byte[]) value);
				} catch (SQLException e) {
					e.printStackTrace();
				}
            } else {
            	return value;
            }
        }
        return null;
    }

    public static List<Field> getFieldsByAnnotation(Class clazz, Class annotation) {
        List<Field> fields = new ArrayList<>();

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.getAnnotation(annotation) != null) {
                fields.add(declaredField);
            }
        }
        return fields;
    }

    public static Object getSqlValue(Object object) throws IllegalAccessException {
        if (object == null)
            return null;

        if (object.getClass().getAnnotation(Table.class) != null) {
            Field idField = getFieldsByAnnotation(object.getClass(), Id.class).get(0);
            idField.setAccessible(true);
            return idField.get(object);
        } else {
            return object;
        }
    }
}
