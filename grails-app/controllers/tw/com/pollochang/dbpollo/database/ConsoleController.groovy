package tw.com.pollochang.dbpollo.database

import grails.converters.JSON

class ConsoleController {

    def dbExecutionService

    def index() { }

    JSON sqlScript(){

        LinkedHashMap result = [:]

        result.data = dbExecutionService.execute(params)

        render result as JSON
    }

}
