package tw.com.pollochang.dbpollo.database


import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class DbExecutionService {

    def execute(GrailsParameterMap params) {

        DBUtil dbUtil = new DBUtil()

        String driverClassName

        DBType dbType = params?."db-typ"
        String dbHost = params?."db-host"
        String dbPort = params?."db-port"
        String username = params?."db-username"
        String password = params?."db-password"
        String sqlText = params?."sql-text"

        driverClassName = dbUtil.getDriverClassName(dbType)

        log.debug("dbType = "+dbType)
        log.debug("dbHost = "+dbHost)
        log.debug("dbPort = "+dbPort)
        log.debug("username = "+username)
        log.debug("password = "+password)
        log.debug("sqlText = "+sqlText)
        log.debug("driverClassName = "+driverClassName)



    }
}
