package tw.com.pollochang.dbpollo.database

import groovy.util.logging.Slf4j
import org.springframework.jdbc.datasource.DriverManagerDataSource

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

@Slf4j
class Execution {

    LinkedHashMap executeSql(
            DriverManagerDataSource driverManagerDataSource,
            String sqlStr
    ){
        LinkedHashMap result = [:]
        List resultList = null
        DBUtil dbUtil = new DBUtil()

        try(
                Connection connection = driverManagerDataSource.getConnection()
                PreparedStatement ps = connection.prepareStatement(sqlStr)
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


        return result
    }

}
