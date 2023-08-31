package tw.com.pollochang.dbpollo.database

import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.SQLException

/**
 * 資料庫工具
 */
class DBUtil {

    /**
     *
     * @param dbType 支援的資料庫類型
     * @return
     */
    String getDriverClassName(DBType dbType){
        String driverClassName

        switch (dbType){
            case DBType.POSTGRESQL : driverClassName = "org.postgresql.Driver"
                break
            case [DBType.ORACLE , DBType.ORACLE_SID] :
                driverClassName = "oracle.jdbc.OracleDriver"
                break
            case DBType.DB2 : driverClassName = "com.ibm.db2.jcc.DB2Driver"
                break
            case DBType.MYSQL : driverClassName = "com.mysql.cj.jdbc"
                break
            case DBType.MSSQL : driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
                break
            default : throw new RuntimeException("No supper database.")
        }

        return driverClassName
    }

    /**
     *
     * @param dbType 支援的資料庫類型
     * @return
     */
    String getDialect(DBType dbType){
        String dialect

        switch (dbType){
            case DBType.POSTGRESQL : dialect = "org.hibernate.dialect.PostgreSQLDialect"
                break
            case [DBType.ORACLE , DBType.ORACLE_SID] : dialect = "org.hibernate.dialect.Oracle12cDialect"
                break
            case DBType.DB2 : dialect = "org.hibernate.dialect.DB297Dialect"
                break
            case DBType.MYSQL : dialect = "org.hibernate.dialect.MySQL8Dialect"
                break
            case DBType.MSSQL : dialect = "org.hibernate.dialect.SQLServerDialect "
                break
            default : throw new RuntimeException("No supper database.")
        }

        return dialect
    }

    /**
     * 組成 jdbcURL
     * @param dbType 支援的資料庫類型
     * @param host 資料庫位置
     * @param port port
     * @param dbName 資料庫名稱
     * @return jdbcURL
     */
    String getJdbcUrl(DBType dbType,String host,int port,String dbName){
        StringBuilder jdbcUrl = new StringBuilder()

        switch (dbType){
            case DBType.POSTGRESQL :
                // jdbc:postgresql://host:port/database
                jdbcUrl.append("jdbc:postgresql://")
                        .append(host)
                        .append(":").append(port)
                        .append("/").append(dbName)
                
                break
            case DBType.ORACLE :
                // jdbc:oracle:thin:[<user>/<password>]@//<host>[:<port>]/<service>
                jdbcUrl.append("jdbc:oracle:thin:@//")
                        .append(host)
                        .append(":").append(port)
                        .append("/").append(dbName)
                
                break
            case DBType.ORACLE_SID :
                // jdbc:oracle:thin:[<user>/<password>]@<host>[:<port>]:<SID>
                jdbcUrl.append("jdbc:oracle:thin:@")
                        .append(host)
                        .append(":").append(port)
                        .append(":").append(dbName)
                
                break
            case DBType.DB2 :
                // jdbc:db2://<host>:<port>/<database>
                jdbcUrl.append("jdbc:db2://")
                        .append(host)
                        .append(":").append(port)
                        .append("/").append(dbName)
                
            break
            case DBType.MYSQL :
                // jdbc:mysql://<host>:<port>/
                jdbcUrl.append("jdbc:mysql://")
                        .append(host)
                        .append(":").append(port)
                        .append("/").append(dbName)
                
            break
            case DBType.MSSQL :
                // jdbc:sqlserver://serverName=localhostport=1433databaseName=master
                jdbcUrl.append("jdbc:sqlserver://")
                        .append("serverName=").append(host)
                        .append("port=").append(port)
                        .append("databaseName=").append(dbName)
                
            break
            default : throw new RuntimeException("No supper database.")
        }

        return jdbcUrl.toString()
    }

    String listDatabaseSchemas(DBType dbType){
        String result

        switch (dbType){
            case DBType.POSTGRESQL : result = "select schema_name from information_schema.schemata"
                break
            case [DBType.ORACLE , DBType.ORACLE_SID] : result = ""
                break
            case DBType.DB2 : result = ""
                break
            case DBType.MYSQL : result = ""
                break
            case DBType.MSSQL : result = ""
                break
            default : throw new RuntimeException("No supper database.")
        }

        return result
    }

    /**
     * 取得 schema 所有的的資料表名稱
     * @param dbType
     * @return
     */
    String getSchemaAllTables(DBType dbType){
        String result

        switch (dbType){
            case DBType.POSTGRESQL : result = "select table_name from information_schema.tables where table_schema = ? order by table_name "
                break
            case [DBType.ORACLE , DBType.ORACLE_SID] : result = ""
                break
            case DBType.DB2 : result = ""
                break
            case DBType.MYSQL : result = ""
                break
            case DBType.MSSQL : result = ""
                break
            default : throw new RuntimeException("No supper database.")
        }

        return result
    }

    String getColumnSchema(DBType dbType){

        String result

        switch (dbType){
            case DBType.POSTGRESQL : result = "select column_name column_name from information_schema.columns where table_schema = ? and table_name = ? order by ordinal_position"
                break
            case [DBType.ORACLE , DBType.ORACLE_SID] : result = ""
                break
            case DBType.DB2 : result = ""
                break
            case DBType.MYSQL : result = ""
                break
            case DBType.MSSQL : result = ""
                break
            default : throw new RuntimeException("No supper database.")
        }

        return result
    }

    String testSql(DBType dbType){
        String result

        switch (dbType){
            case DBType.POSTGRESQL : result = "select count(1) cnt"
                break
            case [DBType.ORACLE , DBType.ORACLE_SID] : result = ""
                break
            case DBType.DB2 : result = ""
                break
            case DBType.MYSQL : result = ""
                break
            case DBType.MSSQL : result = ""
                break
            default : throw new RuntimeException("No supper database.")
        }

        return result
    }

    List resultSetToArrayList(ResultSet rs) throws SQLException{
        ResultSetMetaData md = rs.getMetaData()
        int columns = md.getColumnCount()
        ArrayList list = []
        while (rs.next()){
            HashMap row = new HashMap(columns)
            for(int i=1 ; i<=columns ; ++i){
                row.put(md.getColumnName(i),rs.getObject(i))
            }
            list.add(row)
        }

        return list
    }

}
