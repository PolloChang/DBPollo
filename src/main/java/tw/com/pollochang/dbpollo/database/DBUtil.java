package tw.com.pollochang.dbpollo.database;

/**
 * 資料庫工具
 */
public class DBUtil {

    /**
     *
     * @param dbType 支援的資料庫類型
     * @return
     */
    public String getDriverClassName(DBType dbType){
        String driverClassName = "";

        switch (dbType){
            case POSTGRESQL : driverClassName = "org.postgresql.Driver";
            break;
            case ORACLE , ORACLE_SID :
                driverClassName = "oracle.jdbc.OracleDriver";
                break;
            case DB2 : driverClassName = "com.ibm.db2.jcc.DB2Driver";
                break;
            case MYSQL : driverClassName = "com.mysql.cj.jdbc";
                break;
            case MSSQL : driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                break;
            default : throw new RuntimeException("No supper database.");
        }

        return driverClassName;
    }

    /**
     *
     * @param dbType 支援的資料庫類型
     * @return
     */
    public String getDialect(DBType dbType){
        String dialect = "";

        switch (dbType){
            case POSTGRESQL : dialect = "org.hibernate.dialect.PostgreSQLDialect"; break;
            case ORACLE , ORACLE_SID : dialect = "org.hibernate.dialect.Oracle12cDialect"; break;
            case DB2 : dialect = "org.hibernate.dialect.DB297Dialect"; break;
            case MYSQL : dialect = "org.hibernate.dialect.MySQL8Dialect"; break;
            case MSSQL : dialect = "org.hibernate.dialect.SQLServerDialect "; break;
            default : throw new RuntimeException("No supper database.");
        }

        return dialect;
    }

    /**
     * 組成 jdbcURL
     * @param dbType 支援的資料庫類型
     * @param host 資料庫位置
     * @param port port
     * @param dbName 資料庫名稱
     * @return jdbcURL
     */
    public String geJDBCtUrl(DBType dbType,String host,int port,String dbName){
        StringBuilder jdbcUrl = new StringBuilder();

        switch (dbType){
            case POSTGRESQL :
                // jdbc:postgresql://host:port/database
                jdbcUrl.append("jdbc:postgresql://")
                        .append(host)
                        .append(":").append(port)
                        .append("/").append(dbName)
                ;
                break;
            case ORACLE :
                // jdbc:oracle:thin:[<user>/<password>]@//<host>[:<port>]/<service>
                jdbcUrl.append("jdbc:oracle:thin:@//")
                        .append(host)
                        .append(":").append(port)
                        .append("/").append(dbName)
                ;
                break;
            case ORACLE_SID :
                // jdbc:oracle:thin:[<user>/<password>]@<host>[:<port>]:<SID>
                jdbcUrl.append("jdbc:oracle:thin:@")
                        .append(host)
                        .append(":").append(port)
                        .append(":").append(dbName)
                ;
                break;
            case DB2 :
                // jdbc:db2://<host>:<port>/<database>
                jdbcUrl.append("jdbc:db2://")
                        .append(host)
                        .append(":").append(port)
                        .append("/").append(dbName)
                ;
            break;
            case MYSQL :
                // jdbc:mysql://<host>:<port>/
                jdbcUrl.append("jdbc:mysql://")
                        .append(host)
                        .append(":").append(port)
                        .append("/").append(dbName)
                ;
            break;
            case MSSQL :
                // jdbc:sqlserver://;serverName=localhost;port=1433;databaseName=master
                jdbcUrl.append("jdbc:sqlserver://;")
                        .append("serverName=").append(host)
                        .append(";port=").append(port)
                        .append(";databaseName=").append(dbName)
                ;
            break;
            default : throw new RuntimeException("No supper database.");
        }

        return jdbcUrl.toString();
    }


}
