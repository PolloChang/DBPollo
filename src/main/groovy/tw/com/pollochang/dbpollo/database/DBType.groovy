package tw.com.pollochang.dbpollo.database

/**
 * 支援的資料庫類型
 */
enum DBType {

    POSTGRESQL ("POSTGRESQL"),
    ORACLE ("ORACLE"),
    ORACLE_SID ("ORACLE_SID"),
    DB2 ("DB2"),
    MYSQL ("MYSQL"),
    MSSQL ("MSSQL")

    private final String name

    DBType(String s) {
        this.name = s
    }

    boolean equals(String otherName) {
        return name == otherName
    }

    String toString() {
        return this.name
    }
}