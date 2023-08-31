package tw.com.pollochang.dbpollo.database

import grails.gorm.transactions.Transactional
import net.sf.jsqlparser.parser.CCJSqlParserUtil
import net.sf.jsqlparser.statement.Statement
import net.sf.jsqlparser.statement.select.Select
import net.sf.jsqlparser.util.TablesNamesFinder
import org.springframework.jdbc.datasource.DriverManagerDataSource
import tw.com.pollochang.dbpollo.codeMirror.HintOptions


/**
 * 解析SQL語法
 */
@Transactional
class ParserSQLService {

    HintOptions getParserSQLAndGetTableColumns(
            DbConfig dbConfig,
            String schema,
            String sqlStr
    ) {

        HintOptions hintOptions = new HintOptions()
        DBUtil dbUtil = new DBUtil()
        Execution execution = new Execution()


        String queryColumnSchemaSql = dbUtil.getColumnSchema(dbConfig.type)
        Statement statement = CCJSqlParserUtil.parse(sqlStr)

        Select selectStatement = (Select) statement
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder()
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement)

        hintOptions = getSchemaTables(dbConfig,schema)

        hintOptions.tables.each { key,value ->
            String tableName = key
            List columnList = []
            if(tableList.indexOf(tableName) >= 0){

                LinkedHashMap tableSchema = execution.executeSql(new DriverManagerDataSource(),dbConfig,queryColumnSchemaSql,[schema,tableName])
                List resultColumnList = tableSchema.data as List
                resultColumnList.each { column ->
                    columnList << column.column_name
                }
                log.debug("columnList = "+columnList)
            }
            hintOptions.tables.put(key,columnList)

        }

        return hintOptions
    }

    /**
     * 列出資料庫的schema
     * @param dbConfig
     * @return
     */
    LinkedHashMap listDatabaseSchemas(DbConfig dbConfig){
        DBUtil dbUtil = new DBUtil()
        Execution execution = new Execution()
        String listDatabaseSchemasSql = dbUtil.listDatabaseSchemas(dbConfig.type)

        LinkedHashMap result = execution.executeSql(new DriverManagerDataSource(),dbConfig,listDatabaseSchemasSql)

        return result
    }

    HintOptions getSchemaTables(
            DbConfig dbConfig,
            String schema
    ){
        DBUtil dbUtil = new DBUtil()
        Execution execution = new Execution()
        String getSchemaAllTablesSql = dbUtil.getSchemaAllTables(dbConfig.type)

        HintOptions hintOptions = new HintOptions()

        LinkedHashMap result = execution.executeSql(new DriverManagerDataSource(),dbConfig,getSchemaAllTablesSql,[schema])

        List resultTableList = result.data as List

        resultTableList.each { resultTable ->
            HashMap<String,List> table = ["${resultTable.table_name}":[]]
            hintOptions.tables << table
        }

        return hintOptions
    }
}
