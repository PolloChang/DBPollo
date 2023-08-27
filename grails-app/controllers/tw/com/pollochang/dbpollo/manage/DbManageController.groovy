package tw.com.pollochang.dbpollo.manage

import grails.converters.JSON
import tw.com.pollochang.dbpollo.database.DbConfig

class DbManageController {

    DbConfigService dbConfigService

    def index() {
        render view: "/tw/com/pollochang/dbpollo/manage/dbManage/index"
    }

    def creatPage(){
        DbConfig dbConfigI = new DbConfig()
        render view: "/tw/com/pollochang/dbpollo/manage/dbManage/creatPage" ,model: [dbConfigI:dbConfigI]
    }

    def editPage(){
        DbConfig dbConfigI = DbConfig.read(params?.id as long)
        render view: "/tw/com/pollochang/dbpollo/manage/dbManage/editPage",model: [dbConfigI:dbConfigI]
    }

    JSON filter(){
        LinkedHashMap result = dbConfigService.filter(params)

        render result as JSON
    }

    JSON insert(){
        LinkedHashMap result = dbConfigService.insert(params)
        if(!result.actionType){
            result.actionMessage = g.render(template: '/tw/com/pollochang/bs/errorMessage' , model: [beanI:result.bean])
        }
        else{
            //更新tab資訊

            List scriptArrays = []
            scriptArrays << [mode: 'execute', script: """parent.openTab(
                    'DBMANAGE',
                    'DBMANAGE-${result.bean?.id}',
                    '${result.bean?.name?:""}',
                    '${createLink(controller: 'dbManage',action: 'editPage',params: [id:result.bean?.id])}'
                );"""]
            scriptArrays << [mode: 'execute', script: """parent.closeTab('DM_MANAGE-0');"""]
            result.scriptArrays = scriptArrays
        }
        render result as JSON
    }

    JSON update(){
        LinkedHashMap result = dbConfigService.update(params)
        if(!result.actionType){
            result.actionMessage = g.render(template: '/tw/com/pollochang/bs/errorMessage' , model: [beanI:result.bean])
        }
        else{
            //更新tab資訊

            List scriptArrays = []
            scriptArrays << [mode: 'execute', script: """parent.refreshTab({
                        tabId:'DBMANAGE-${result.bean?.id}',
                        src: '${createLink(controller: 'dbManage',action: 'editPage',params: [id:result.bean?.id])}'
                    }
                );"""]
            result.scriptArrays = scriptArrays
        }
        render result as JSON
    }

    JSON delete(){
        LinkedHashMap result = dbConfigService.delete(params)
        List scriptArrays = []
        if(!result.actionType){
            result.actionMessage = g.render(template: '/tw/com/pollochang/bs/errorMessage' , model: [beanI:result.bean])
        }else{
            scriptArrays << [mode: 'execute', script: """parent.closeTab('DBMANAGE-${result.bean?.id}');"""]
            result.scriptArrays = scriptArrays
        }
        render result as JSON
    }
}
