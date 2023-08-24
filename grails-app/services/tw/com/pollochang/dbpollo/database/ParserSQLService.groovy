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

    LinkedHashMap getParserSQLAndGetTableColumns(GrailsParameterMap params) {

        LinkedHashMap result = [:]

        DBUtil dbUtil = new DBUtil()
        Execution execution = new Execution()

        String driverClassName
        String jdbcUrl

        DBType dbType = DBType.valueOf(params?."db-type" as String)
        String dbHost = params?."db-host"
        String dbPort = params?."db-port"
        String dbName = params?."db-name"
        String username = params?."db-username"
        String password = params?."db-password"
        String sqlText = params?."sql-text"

        driverClassName = dbUtil.getDriverClassName(dbType)
        jdbcUrl = dbUtil.getJdbcUrl(dbType,dbHost,dbPort as int,dbName)

        Statement statement = CCJSqlParserUtil.parse(sqlText)
        println 21
        Select selectStatement = (Select) statement
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder()
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement)
        println tableList


        String querySchemaSql = dbUtil.geColumnSchema(dbType)

        driverManagerDataSource = new DriverManagerDataSource()
        driverManagerDataSource.setDriverClassName(driverClassName)
        driverManagerDataSource.setUsername(username)
        driverManagerDataSource.setPassword(password)
        driverManagerDataSource.setUrl(jdbcUrl)

//        tableList.each {tableName ->
//
//        }
        println "querySchemaSql = "+querySchemaSql
        println execution.executeSql(driverManagerDataSource,querySchemaSql,['bs101'])



        return result
    }
}
