package tw.com.pollochang.dbpollo.database

import grails.converters.JSON

class ConsoleController {

    def dbExecutionService
    def parserSQLService

    def index() { }

    /**
     * 執行SQL
     * @return
     */
    JSON executeSql(){

        LinkedHashMap result = [:]

        result = dbExecutionService.execute(params)

        render result as JSON
    }

    /**
     * 從SQL取得table所有欄位
     * @return
     */
    JSON getParserSQLAndGetTableColumns(){
        LinkedHashMap result = [:]

        parserSQLService.getParserSQLAndGetTableColumns(params)

        render result as JSON
    }

    /**
     * 取得資料庫所有Table
     * @return
     */
    JSON getTables(){
        LinkedHashMap result = [:]

        render result as JSON
    }

}
