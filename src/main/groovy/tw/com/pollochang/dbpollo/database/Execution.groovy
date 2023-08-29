package tw.com.pollochang.dbpollo.database

import com.pollochang.util.AES
import groovy.util.logging.Slf4j
import org.springframework.jdbc.datasource.DriverManagerDataSource

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

@Slf4j
class Execution {

    /**
     * 執行SQL
     * @param driverManagerDataSource
     * @param dbConfig
     * @param sqlStr
     * @return
     */
    LinkedHashMap executeSql(
            DriverManagerDataSource driverManagerDataSource,
            DbConfig dbConfig,
            String sqlStr
    ){
        DBUtil dbUtil = new DBUtil()
        String driverClassName = dbUtil.getDriverClassName(dbConfig.type)
        String jdbcUrl = dbUtil.getJdbcUrl(dbConfig.type,dbConfig.host,dbConfig.port as int,dbConfig.dbname)
        String password = AES.decrypt(dbConfig.password,dbConfig.passwordSalt)
        driverManagerDataSource.setDriverClassName(driverClassName)
        driverManagerDataSource.setUsername(dbConfig.username)
        driverManagerDataSource.setPassword(password)
        driverManagerDataSource.setUrl(jdbcUrl)

        LinkedHashMap result = executeSql(driverManagerDataSource,sqlStr)

        return result

    }

    /**
     * 執行SQL
     * @param driverManagerDataSource
     * @param sqlStr
     * @return
     */
    LinkedHashMap executeSql(
            DriverManagerDataSource driverManagerDataSource,
            String sqlStr
    ){
        LinkedHashMap result = [:]
        List resultList = null

        try(
                Connection connection = driverManagerDataSource.getConnection()
                PreparedStatement ps = connection.prepareStatement(sqlStr)
        ){

            resultList = executeResultSet(ps)
            result.success = "執行成功"
        }catch (SQLException ex){
            result.error = ex.message
            log.error(ex.message)
        }finally {
            result.data = resultList
            return result
        }


        return result
    }

    /**
     * 執行SQL
     * @param driverManagerDataSource
     * @param sqlStr
     * @param fieldList
     * @return
     */
    LinkedHashMap executeSql(
            DriverManagerDataSource driverManagerDataSource,
            String sqlStr,
            List fieldList
    ){
        LinkedHashMap result = [:]
        List resultList = null


        try(
                Connection connection = driverManagerDataSource.getConnection()
                PreparedStatement ps = connection.prepareStatement(sqlStr)
        ){
            fieldList.eachWithIndex { field,i ->
                if(field instanceof String){
                    ps.setString(i+1,field)
                }else if(field instanceof Integer){
                    ps.setInt(i+1,field)
                }
            }

            resultList = executeResultSet(ps)
            result.success = "執行成功"
        }catch (SQLException ex){
            result.error = ex.message
            log.error(ex.message)
        }finally {
            result.data = resultList
            return result
        }

        return result
    }

    /**
     * 執行 ResultSet
     * @param ps
     * @return
     */
    private List executeResultSet(PreparedStatement ps){
        List resultList
        DBUtil dbUtil = new DBUtil()
        try (ResultSet rs = ps.executeQuery()) {

            if(rs != null){
                resultList = dbUtil.resultSetToArrayList(rs)
            }else {
                resultList = []
            }
        }catch (SQLException ex){
            throw ex
        }

        return resultList
    }

}
