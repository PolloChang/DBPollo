package tw.com.pollochang.dbpollo.database

import com.pollochang.util.AES
import grails.gorm.transactions.Transactional
import org.springframework.jdbc.datasource.DriverManagerDataSource

@Transactional
class DbExecutionService {

    DriverManagerDataSource driverManagerDataSource

    LinkedHashMap execute(
            DbConfig dbConfig,
            String sqlStr
    ) {

        Execution execution = new Execution()

        LinkedHashMap result = execution.executeSql(new DriverManagerDataSource(),dbConfig,sqlStr)

        return result
    }


    boolean testDbConnected(DbConfig dbConfig){

        LinkedHashMap result = [:]
        DBUtil dbUtil = new DBUtil()
        Execution execution = new Execution()
        String driverClassName
        String jdbcUrl
        String sqlText
        String password = AES.decrypt(dbConfig.password,dbConfig.passwordSalt)
        driverClassName = dbUtil.getDriverClassName(dbConfig.type)
        jdbcUrl = dbUtil.getJdbcUrl(dbConfig.type,dbConfig.host,dbConfig.port as int,dbConfig.dbname)
        sqlText = dbUtil.testSql(dbConfig.type)

        driverManagerDataSource = new DriverManagerDataSource()
        driverManagerDataSource.setDriverClassName(driverClassName)
        driverManagerDataSource.setUsername(dbConfig.username)
        driverManagerDataSource.setPassword(password)
        driverManagerDataSource.setUrl(jdbcUrl)
        result = execution.executeSql(driverManagerDataSource,sqlText)
        if(result.success){
            return true
        }else{
            return false
        }
    }

}
