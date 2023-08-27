package tw.com.pollochang.dbpollo.manage

import grails.converters.JSON
import org.grails.datastore.gorm.GormEntity
import tw.com.pollochang.common.CommonController
import tw.com.pollochang.dbpollo.database.DbConfig

class DbManageController extends CommonController{

    DbConfigService dbConfigService
    def dbExecutionService

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

        result =  afterInsert(
                result.bean as GormEntity,
                result.actionType,
                "DBMANAGE",
                result.bean?.name?:"",
                createLink(controller: 'dbManage',action: 'editPage',params: [id:result.bean?.id]) as String
        )
        render result as JSON
    }

    JSON update(){
        LinkedHashMap result = dbConfigService.update(params)

        result = afterUpdate(
            result.bean as GormEntity,
            result.actionType,
            "DBMANAGE",
            createLink(controller: 'dbManage',action: 'editPage',params: [id:result.bean?.id]) as String
        )


        render result as JSON
    }

    JSON delete(){
        LinkedHashMap result = dbConfigService.delete(params)
        result = afterDelete(
                result.bean as GormEntity,
                params?.id as long,
                result.actionType,
                "DBMANAGE"
        )
        render result as JSON
    }

    JSON testDbConnected(){
        LinkedHashMap result = [:]

        DbConfig dbConfig = DbConfig.read(params?.id as long)
        boolean testResult = dbExecutionService.testDbConnected(dbConfig)
        List scriptArrays = []
        if(testResult){
            scriptArrays << [mode: 'execute', script: """alert('測試成功');"""]
        }else {
            scriptArrays << [mode: 'execute', script: """alert('測試失敗');"""]
        }
        result.scriptArrays = scriptArrays
        render result as JSON
    }
}
