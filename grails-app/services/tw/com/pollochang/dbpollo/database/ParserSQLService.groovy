package tw.com.pollochang.dbpollo.database

import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
import net.sf.jsqlparser.parser.CCJSqlParserUtil
import net.sf.jsqlparser.schema.Table
import net.sf.jsqlparser.statement.Statement
import net.sf.jsqlparser.statement.select.PlainSelect
import net.sf.jsqlparser.statement.select.Select
import net.sf.jsqlparser.statement.select.SelectItem
import net.sf.jsqlparser.util.TablesNamesFinder
import org.springframework.jdbc.datasource.DriverManagerDataSource
import tw.com.pollochang.dbpollo.codeMirror.HintOptions

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

/**
 * 解析SQL語法
 */
@Transactional
class ParserSQLService {

    DriverManagerDataSource driverManagerDataSource

    HintOptions getParserSQLAndGetTableColumns(GrailsParameterMap params) {

        HintOptions hintOptions = new HintOptions()
        DBUtil dbUtil = new DBUtil()
        Execution execution = new Execution()

        DBType dbType = DBType.valueOf(params?."db-type" as String)
        String dbHost = params?."db-host"
        String dbPort = params?."db-port"
        String dbName = params?."db-name"
        String username = params?."db-username"
        String password = params?."db-password"
        String sqlText = params?."sql-text"

        String querySchemaSql = dbUtil.geColumnSchema(dbType)
        String driverClassName = dbUtil.getDriverClassName(dbType)
        String jdbcUrl = dbUtil.getJdbcUrl(dbType,dbHost,dbPort as int,dbName)
        Statement statement = CCJSqlParserUtil.parse(sqlText)

        Select selectStatement = (Select) statement
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder()
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement)
        log.debug("tableList = "+tableList)

        log.debug("querySchemaSql = "+querySchemaSql)
        driverManagerDataSource = new DriverManagerDataSource()
        driverManagerDataSource.setDriverClassName(driverClassName)
        driverManagerDataSource.setUsername(username)
        driverManagerDataSource.setPassword(password)
        driverManagerDataSource.setUrl(jdbcUrl)
        tableList.each {tableName ->
            LinkedHashMap tableSchema = execution.executeSql(driverManagerDataSource,querySchemaSql,[tableName])

            List resultColumnList = tableSchema.data as List

            List columnList = []

            resultColumnList.each { column ->
                columnList << column.column_name
            }

            HashMap<String,List> table = ["${tableName}":columnList]
            hintOptions.tables << table
        }

        return hintOptions
    }
}
