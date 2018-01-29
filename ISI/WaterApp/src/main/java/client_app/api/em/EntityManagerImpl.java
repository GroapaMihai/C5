package client_app.api.em;

import client_app.api.database.DBManager;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManagerImpl implements EntityManager {
	private ResultSet resultSet;
	private Statement statement;
	private Connection connection;

	public EntityManagerImpl() {
		connection = DBManager.getConnection();
	}
	
    private void closeResultSet() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void closeStatement() {
        if (statement != null) {
            try {
            	statement.close();
            } catch (SQLException e) {
            	e.printStackTrace();
                System.exit(1);
            }
        }
    }

    @Override
    public <T> T findById(Class<T> entityClass, Integer id) {
        String tableName = EntityUtils.getTableName(entityClass);
        List<ColumnInfo> columnInfo = EntityUtils.getColumns(entityClass);

        QueryBuilder queryBuilder = new QueryBuilder();
        Query query;
        Field fieldInstance;
        T instance = null;

        queryBuilder = queryBuilder.
            withTableName(tableName).
            withQueryColumns(columnInfo).
            withQueryType(QueryType.SELECT);

        // clauza WHERE
        Condition condition = new Condition();
        for (ColumnInfo info : columnInfo) {
            if (info.isId()) {
                condition.setValue(id);
                condition.setColumnName(tableName + "." + info.getDbColumnName());
                queryBuilder = queryBuilder.withCondition(condition);
                break;
            }
        }

        try {
            query = new Query(queryBuilder);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query.createSQLQuery());

            while (resultSet.next()) {
                instance = entityClass.newInstance();

                for (ColumnInfo info : columnInfo) {
                    fieldInstance = instance.getClass().getDeclaredField(info.getColumnName());
                    fieldInstance.setAccessible(true);
                    fieldInstance.set(instance, EntityUtils.castFromSqlType(resultSet.getObject(info.getDbColumnName()), fieldInstance.getType()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet();
            closeStatement();
        }

        return instance;
    }

    @Override
    public Integer getNextIdVal(String tableName, String columnIdName) {
        String sqlQuery = "SELECT MAX(" + columnIdName + ") FROM " + tableName;
        int max = -1;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                max = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet();
            closeStatement();
        }

        return max + 1;
    }

    @Override
    public <T> Object insert(T entity) {
        String tableName = EntityUtils.getTableName(entity.getClass());
        List<ColumnInfo> columnInfo = EntityUtils.getColumns(entity.getClass());
        int insertedId = -1;

        QueryBuilder queryBuilder = new QueryBuilder();
        Query query;

        for (ColumnInfo column : columnInfo) {
            if (column.isId()) {
                insertedId = getNextIdVal(tableName, column.getDbColumnName());
                column.setValue(insertedId);
            } else {
                try {
                    Field field = entity.getClass().getDeclaredField(column.getColumnName());
                    field.setAccessible(true);
                    column.setValue(field.get(entity));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

        queryBuilder = queryBuilder.
            withTableName(tableName).
            withQueryColumns(columnInfo).
            withQueryType(QueryType.INSERT);

        query = new Query(queryBuilder);

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query.createSQLQuery());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement();
        }

        return findById(entity.getClass(), insertedId);
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {
        String tableName = EntityUtils.getTableName(entityClass);
        List<ColumnInfo> columnInfo = EntityUtils.getColumns(entityClass);

        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder = queryBuilder.
            withTableName(tableName).
            withQueryColumns(columnInfo).
            withQueryType(QueryType.SELECT);

        Query query = new Query(queryBuilder);
        ArrayList<T> result = new ArrayList<>();
        T instance;
        Field field;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query.createSQLQuery());

            while (resultSet.next()) {
                instance = entityClass.newInstance();

                for (ColumnInfo column : columnInfo) {
                    field = instance.getClass().getDeclaredField(column.getColumnName());
                    field.setAccessible(true);
                    field.set(instance, EntityUtils.castFromSqlType(resultSet.getObject(column.getDbColumnName()), field.getType()));
                }

                result.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet();
            closeStatement();
        }

        return result;
    }

    @Override
    public <T> T update(T entity) {
        String tableName = EntityUtils.getTableName(entity.getClass());
        List<ColumnInfo> columnInfo = EntityUtils.getColumns(entity.getClass());

        Condition condition = new Condition();
        Field field;
        int idToUpdate = -1;
        int affectedRowsByUpdate;
        QueryBuilder queryBuilder = new QueryBuilder();
        Query query;

        for (ColumnInfo column : columnInfo) {
            try {
                field = entity.getClass().getDeclaredField(column.getColumnName());
                field.setAccessible(true);
                column.setValue(field.get(entity));

                if (column.isId()) {
                    if (column.getValue() == null) {
                        System.out.println("Error: " + column.getDbColumnName() + " can't be null!");
                        return null;
                    }

                    idToUpdate = (int) column.getValue();
                    condition.setValue(idToUpdate);
                    condition.setColumnName(column.getDbColumnName());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        queryBuilder = queryBuilder.
            withTableName(tableName).
            withQueryColumns(columnInfo).
            withQueryType(QueryType.UPDATE).
            withCondition(condition);

        query = new Query(queryBuilder);

        try {
            statement = connection.createStatement();
            affectedRowsByUpdate = statement.executeUpdate(query.createSQLQuery());

            if (affectedRowsByUpdate <= 0) {
                System.out.println("No rows were affected during update!");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement();
        }

        return (T) findById(entity.getClass(), idToUpdate);
    }

    @Override
    public void delete(Object entity) {
        String tableName = EntityUtils.getTableName(entity.getClass());
        List<ColumnInfo> columnInfo = EntityUtils.getColumns(entity.getClass());

        Condition condition = new Condition();
        Field field;
        QueryBuilder queryBuilder = new QueryBuilder();
        Query query;

        for (ColumnInfo column : columnInfo) {
            try {
                field = entity.getClass().getDeclaredField(column.getColumnName());
                field.setAccessible(true);
                column.setValue(field.get(entity));

                if (column.isId()) {
                    if (column.getValue() == null) {
                        System.out.println("Error: " + column.getDbColumnName() + " can't be null!");
                        return;
                    }

                    condition.setValue((int) column.getValue());
                    condition.setColumnName(column.getDbColumnName());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        queryBuilder = queryBuilder.
            withTableName(tableName).
            withQueryColumns(columnInfo).
            withQueryType(QueryType.DELETE).
            withCondition(condition);

        query = new Query(queryBuilder);

        try {
            statement = connection.createStatement();
            int affectedRowsByUpdate = statement.executeUpdate(query.createSQLQuery());

            if (affectedRowsByUpdate <= 0) {
                System.out.println("No rows were affected during deletion!");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: Could not complete deletion because of an integrity constraint!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement();
        }
    }

    @Override
    public <T> List<T> findMatchingEntities(Class<T> entityClass, T patternEntity) {
        String tableName = EntityUtils.getTableName(entityClass);
        List<ColumnInfo> columnInfo;
        List<ColumnInfo> notNullColumnInfo;

		try {
			columnInfo = EntityUtils.getColumns(entityClass);
			notNullColumnInfo = EntityUtils.getNotNullColumnsOfEntity(entityClass, patternEntity);
		} catch (Exception e1) {
			return null;
		}

        QueryBuilder queryBuilder = new QueryBuilder();
        Query query;
        Field fieldInstance;
        T instance = null;
        ArrayList<T> result = new ArrayList<>();

        queryBuilder = queryBuilder.
            withTableName(tableName).
            withQueryColumns(columnInfo).
            withQueryType(QueryType.SELECT);

        // clauza WHERE
        Condition condition;
        for (ColumnInfo column : notNullColumnInfo) {
            condition = new Condition();
            condition.setColumnName(column.getDbColumnName());
            condition.setValue(column.getValue());
            queryBuilder = queryBuilder.withCondition(condition);
        }

        query = queryBuilder.build();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query.createSQLQuery());

            while (resultSet.next()) {
                instance = entityClass.newInstance();

                for (ColumnInfo info : columnInfo) {
                    fieldInstance = instance.getClass().getDeclaredField(info.getColumnName());
                    fieldInstance.setAccessible(true);
                    fieldInstance.set(instance, EntityUtils.castFromSqlType(resultSet.getObject(info.getDbColumnName()), fieldInstance.getType()));
                }

                result.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet();
            closeStatement();
        }

        return result;
    }
}