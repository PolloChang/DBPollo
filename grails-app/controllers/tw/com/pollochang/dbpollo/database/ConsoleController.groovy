package tw.com.pollochang.dbpollo.database

import grails.converters.JSON

class ConsoleController {

    def dbExecutionService

    def index() { }

    JSON sqlScript(){
        println params

        dbExecutionService.execute(params)

        render params as JSON
    }
}
