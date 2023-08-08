package tw.com.pollochang.dbpollo.database;

public class DBUtil {

    public String getDriverClassName(DBType dbType){
        String driverClassName = "";

        switch (dbType){
            case POSTGRESQL : driverClassName = "org.postgresql.Driver";
            break;
            case ORACLE :
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

    public String getDialect(DBType dbType){
        String dialect = "";

        switch (dbType){
            case POSTGRESQL : dialect = "org.hibernate.dialect.PostgreSQLDialect"; break;
            case ORACLE : dialect = "org.hibernate.dialect.Oracle12cDialect"; break;
            case DB2 : dialect = "org.hibernate.dialect.DB297Dialect"; break;
            case MYSQL : dialect = "org.hibernate.dialect.MySQL8Dialect"; break;
            case MSSQL : dialect = "org.hibernate.dialect.SQLServerDialect "; break;
            default : throw new RuntimeException("No supper database.");
        }

        return dialect;
    }


}
