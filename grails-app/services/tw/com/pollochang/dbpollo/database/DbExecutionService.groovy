package tw.com.pollochang.dbpollo.database


import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.jdbc.datasource.DriverManagerDataSource

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

@Transactional
class DbExecutionService {

    DriverManagerDataSource driverManagerDataSource

    LinkedHashMap execute(GrailsParameterMap params) {

        LinkedHashMap result = [:]

        DBUtil dbUtil = new DBUtil()

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

//        Connection connection
        List resultList
        //try-with-resources
        try(
                Connection connection = driverManagerDataSource.getConnection()
                PreparedStatement ps = connection.prepareStatement(sqlText)
        ){

            try (ResultSet rs = ps.executeQuery()) {

                if(rs != null){
                    resultList = dbUtil.resultSetToArrayList(rs)
                }else {
                    resultList = []
                }

                result.success = "執行成功"
            }
        }catch (SQLException ex){
            result.error = ex.message
            log.error(ex.message)
        }finally {
            result.data = resultList
            return result
        }
    }
    

}
