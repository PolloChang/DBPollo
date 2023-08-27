package tw.com.pollochang.dbpollo.system

import groovy.sql.GroovyRowResult
import tw.com.pollochang.bs.NavService

class SystemController {

    NavService navService

    def index() {
        List<GroovyRowResult> groupL = navService.getUserUseGroupList()
        List<GroovyRowResult> appL = navService.getUserAppList()
        render view:"/system/index", model: [groupL:groupL,appL:appL]
    }

    def info(){

    }
}
