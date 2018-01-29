package client_app.api.em;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder
 */
public class QueryBuilder {
    private Object tableName;
    private List<ColumnInfo> queryColumns;
    private QueryType queryType;
    private List<Condition> conditions;

    public QueryBuilder() {
    }

    public Object getTableName() {
        return tableName;
    }

    public List<ColumnInfo> getQueryColumns() {
        return queryColumns;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public QueryBuilder withCondition(Condition condition){
        if (conditions == null){
            conditions = new ArrayList<>();
        }

        conditions.add(condition);
        return this;
    }

    public QueryBuilder withTableName(Object tableName) {
        this.tableName = tableName;

        return this;
    }

    public QueryBuilder withQueryColumns(List<ColumnInfo> queryColumns) {
        if (this.queryColumns == null){
            this.queryColumns = new ArrayList<>();
        }

        this.queryColumns.addAll(queryColumns);

        return this;
    }

    public QueryBuilder withQueryType(QueryType queryType){
        this.queryType = queryType;
        return this;
    }

    public Query build() {
        return new Query(this);
    }
}
