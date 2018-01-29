package client_app.api.em;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Query {
    private Object tableName;
    private List<ColumnInfo> queryColumns;
    private QueryType queryType;
    private List<Condition> conditions;

    public Query(QueryBuilder builder) {
        tableName = builder.getTableName();
        queryColumns = builder.getQueryColumns();
        queryType = builder.getQueryType();
        conditions = builder.getConditions();
    }

    public String createSQLQuery() {
    	String query = null;

        switch (queryType) {
            case SELECT: query = createSelectQuery(); break;
            case INSERT: query = createInsertQuery(); break;
            case UPDATE: query = createUpdateQuery(); break;
            case DELETE: query = createDeleteQuery(); break;
        }

        return query;
    }

    private String getValueForQuery(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return "'" + value + "'";
        } else if (value instanceof Timestamp) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return "'" + dateFormat.format(value) + "'";
        } else {
            return value.toString();
        }
    }

    private String createSelectQuery() {
        boolean isFirst = true;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");

        for(ColumnInfo columnInfo : queryColumns) {
            if(!isFirst) {
                sql.append(", ");
            }
            sql.append(columnInfo.getDbColumnName());
            isFirst = false;
        }
        sql.append(" FROM " + tableName);

        boolean whereAdded = false;
        if(conditions != null && !conditions.isEmpty()) {
            for(Condition condition : conditions) {
                sql.append(whereAdded ? " AND " : " WHERE ").append(condition.getColumnName()).append("=")
                        .append(getValueForQuery(condition.getValue()));
                whereAdded = true;
            }
        }
        return sql.toString();
    }

    private String createDeleteQuery() {
        boolean whereAdded = false;
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(tableName);

        if (conditions != null  && !conditions.isEmpty()) {
            for (Condition condition : conditions) {
                sql.append(whereAdded ? " AND " : " WHERE ").append(condition.getColumnName()).append("=").append(getValueForQuery(condition.getValue()));
                whereAdded = true;
            }
        }

        return sql.toString();
    }

    private String createUpdateQuery() {
        boolean first = true;
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(tableName).append(" SET ");

        for (ColumnInfo column : queryColumns) {
            if (!first) {
                sql.append(",");
            } else {
                first = false;
            }

            sql.append(column.getDbColumnName()).append("=").append(getValueForQuery(column.getValue()));
        }

        boolean whereAdded = false;
        if (conditions != null  && !conditions.isEmpty()) {
            for (Condition condition : conditions) {
                sql.append(whereAdded ? " AND" : " WHERE ").append(condition.getColumnName()).append("=").append(getValueForQuery(condition.getValue()));
                whereAdded = true;
            }
        }

        return sql.toString();
    }

    private String createInsertQuery() {
        boolean first = true;
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableName).append(" (");
        StringBuilder sqlValues = new StringBuilder(" VALUES (");

        for (ColumnInfo columnInfo : queryColumns) {
            if (!first) {
                sql.append(",");
                sqlValues.append(",");
            } else {
                first = false;
            }

            sql.append(columnInfo.getDbColumnName());
            sqlValues.append(getValueForQuery(columnInfo.getValue()));
        }

        sql.append(") ");
        sqlValues.append(")");
        sql.append(sqlValues);

        return sql.toString();
    }
}
