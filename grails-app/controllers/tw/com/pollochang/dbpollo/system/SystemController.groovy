package tw.com.pollochang.dbpollo.system

import groovy.sql.GroovyRowResult
import tw.com.pollochang.bs.NavService

class SystemController {

    NavService navService

    def index() {
        List<GroovyRowResult> groupL = navService.getUserUseGroupList()
        println "groupL = "+groupL
        List<GroovyRowResult> appL = navService.getUserAppList()
        println "appL = "+appL
        render view:"/system/index", model: [groupL:groupL,appL:appL]
    }

    def info(){

    }
}
