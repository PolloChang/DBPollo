package tw.com.pollochang.dbpollo.database


import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.jdbc.datasource.DriverManagerDataSource

@Transactional
class DbExecutionService {

    DriverManagerDataSource driverManagerDataSource

    LinkedHashMap execute(GrailsParameterMap params) {

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

        log.debug("dbType = "+dbType)
        log.debug("dbHost = "+dbHost)
        log.debug("dbPort = "+dbPort)
        log.debug("username = "+username)
        log.debug("password = "+password)
        log.debug("sqlText = "+sqlText)
        log.debug("driverClassName = "+driverClassName)
        log.debug("jdbcUrl = "+jdbcUrl)

        driverManagerDataSource = new DriverManagerDataSource()
        driverManagerDataSource.setDriverClassName(driverClassName)
        driverManagerDataSource.setUsername(username)
        driverManagerDataSource.setPassword(password)
        driverManagerDataSource.setUrl(jdbcUrl)

        result = execution.executeSql(driverManagerDataSource,sqlText)

        return result
    }


    boolean testDbConnected(DbConfig dbConfig){

        LinkedHashMap result = [:]
        DBUtil dbUtil = new DBUtil()
        Execution execution = new Execution()
        String driverClassName
        String jdbcUrl
        String sqlText
        driverClassName = dbUtil.getDriverClassName(dbConfig.type)
        jdbcUrl = dbUtil.getJdbcUrl(dbConfig.type,dbConfig.host,dbConfig.port as int,dbConfig.dbname)
        sqlText = dbUtil.testSql(dbConfig.type)

        driverManagerDataSource = new DriverManagerDataSource()
        driverManagerDataSource.setDriverClassName(driverClassName)
        driverManagerDataSource.setUsername(dbConfig.username)
        driverManagerDataSource.setPassword(dbConfig.password)
        driverManagerDataSource.setUrl(jdbcUrl)
        result = execution.executeSql(driverManagerDataSource,sqlText)
        if(result.success){
            return true
        }else{
            return false
        }
    }

}
