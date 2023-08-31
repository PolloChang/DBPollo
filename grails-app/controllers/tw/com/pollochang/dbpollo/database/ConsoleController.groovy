package tw.com.pollochang.dbpollo.database

import grails.converters.JSON
import tw.com.pollochang.dbpollo.codeMirror.HintOptions
import tw.com.pollochang.util.JsonUtil

class ConsoleController {

    def dbExecutionService
    def parserSQLService

    def index() { }

    /**
     * 執行SQL
     * @return
     */
    JSON executeSql(){

        long dbConfigId = params?. dbConfigId as long
        DbConfig dbConfig = DbConfig.read(dbConfigId)
        String sqlStr = params?."sql-text"

        LinkedHashMap result = dbExecutionService.execute(dbConfig,sqlStr)

        render result as JSON
    }

    /**
     * 從SQL取得table所有欄位
     * @return
     */
    JSON getParserSQLAndGetTableColumns(){

        long dbConfigId = params?. dbConfigId as long
        String schema = params?.schema
        DbConfig dbConfig = DbConfig.read(dbConfigId)
        String sqlStr = params?."sql-text"
        HintOptions hintOptions = parserSQLService.getParserSQLAndGetTableColumns(dbConfig,schema,sqlStr)
        JSON renderJSON = hintOptions as JSON
        log.debug(renderJSON.toString())
        render hintOptions as JSON
    }

    /**
     * 取得資料庫所有Table
     * @return
     */
    JSON getTables(){
        long dbConfigId = params?. dbConfigId as long
        String schema = params?.schema
        DbConfig dbConfig = DbConfig.read(dbConfigId)
        HintOptions hintOptions = parserSQLService.getSchemaTables(dbConfig,schema)
        log.debug((hintOptions as JSON).toString())
        render hintOptions as JSON
    }

    /**
     * 列出資料庫的schema
     * @return
     */
    JSON listDatabaseSchemas(){
        long dbConfigId = params?. dbConfigId as long
        DbConfig dbConfig = DbConfig.read(dbConfigId)
        LinkedHashMap result = parserSQLService.listDatabaseSchemas(dbConfig)

        render result as JSON
    }

}
