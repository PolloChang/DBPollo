package tw.com.pollochang.dbpollo.manage

import grails.gorm.transactions.Transactional
import grails.web.databinding.DataBinder
import grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.context.MessageSource
import tw.com.pollochang.dbpollo.database.DBType
import tw.com.pollochang.dbpollo.database.DbConfig
import tw.com.pollochang.security.BsRequestMap

@Transactional
class DbConfigService implements DataBinder {

    MessageSource messageSource
    def springSecurityService

    LinkedHashMap filter(GrailsParameterMap params) {
        LinkedHashMap result = [:]

        String[] strL = ['type',]
        String[] booleanL = []
        String[] likeL = ['name','host','username','dbname']
        String[] mulSelectL = []
        String[] longL = []

        String[] dtL = []
        def dtSEL = []
        dtL.each {
            dtSEL << "${it}1"
            dtSEL << "${it}2"
        }

        def dbConfigL = DbConfig.createCriteria().list(params) {

            strL.each {
                if(params?."${it}"){
                    eq(it, params?."${it}")
                }
            }

            longL.each {
                if(params?."${it}"){
                    eq(it, params?.long("${it}"))
                }
            }

            booleanL.each {
                if(params?."${it}"){
                    eq(it, Boolean.parseBoolean(params?."${it}"))
                }
            }

            likeL.each {
                if(params?."${it}"){
                    ilike("${it}", "%"+params?."${it}"+"%")
                }
            }

            dtL.each {
                if(params?."${it}1"){
                    ge("${it}",params?."${it}S")
                }
                if(params?."${it}2"){
                    le("${it}",params?."${it}E")
                }
            }

            mulSelectL.each {
                if(params?."${it}"){
                    def selectL = params?.list("${it}")
                    def columnName = abService.changeStyle(it,false)
                    or {
                        selectL.each { it2 ->
                            sqlRestriction(" instr(',' || '${it2}' || ',' , ',' || this_.${columnName} || ',') > 0  ")
                        }
                    }
                }
            }

            order("id", "asc")
        }

        result.rows = dbConfigL.collect { it ->
            [

                    id                 : it?.id,
                    name               : it?.name,
                    type               : it?.type.toString(),
                    host               : it?.host,
                    port               : it?.port,
                    dbname             : it?.dbname,
                    username           : it?.username,
            ]
        }
        
        return result
    }

    LinkedHashMap insert(GrailsParameterMap params) {
        return saveInstance(new DbConfig(), params, { DbConfig dbConfigI ->
            dbConfigI.manCreated = '系統管理員'
            dbConfigI.validate()
        })
    }

    LinkedHashMap update(GrailsParameterMap params) {
        println springSecurityService.currentUser?.username
        return saveInstance(DbConfig.get(params.id as long), params, { DbConfig dbConfigI ->
            dbConfigI.manLastUpdated = '系統管理員'
            dbConfigI.validate()
        })
    }

    LinkedHashMap delete(GrailsParameterMap params) {
        LinkedHashMap result = [
                actionType:false,
                acrtionMessage:''
        ]

        DbConfig domain = result.bean = DbConfig.get(params.id as long)
        int pageDataVersion = params.dbConfig.version?(params.dbConfig?.version as int):0
        if(domain.version != pageDataVersion && params.dbConfig.id){

            result.dataVersionDifferent = true
            domain.discard()
        }else{

            try{
                domain.delete()
                result.actionType = true
            }catch(Exception ex){
                result.actionType = false
                log.error(ex.getMessage())
                domain.discard()
            }

            if(result.actionType){
                result.actionMessage = messageSource.getMessage("default.deleted.message", [] as Object[], Locale.TAIWAN)
            }
            else{
                result.actionMessage = messageSource.getMessage("default.not.deleted.message", [] as Object[], Locale.TAIWAN)
            }
        }

        return result
    }

    /**
     * 共同處理
     * @param ex100I
     * @param params
     * @param closure
     * @return
     */
    private LinkedHashMap saveInstance(
            DbConfig domainI,
            GrailsParameterMap params,
            Closure<?> closure
    ) {
        LinkedHashMap result = [
                actionType:false,
                acrtionMessage:''
        ]
        result.bean = domainI
        DBType dbType = DBType.valueOf(params.dbConfig.type as String)
        params.dbConfig.type = dbType
        closure(domainI)

        List include_col = [
                'name','type','host','port','dbname','username','password'
        ]

        bindData(domainI, params["dbConfig"], [include:include_col])
        int pageDataVersion = params.dbConfig.version?(params.dbConfig?.version as int):0
        if(domainI.version != pageDataVersion && params.dbConfig.id){

            result.dataVersionDifferent = true
            domainI.discard()
        } else if (domainI.hasErrors()) { //失敗

            ArrayList errorColumn = []
            domainI.errors.allErrors.eachWithIndex  {item, index ->
                errorColumn[index] = [item?.arguments,item?.defaultMessage]
            }
            domainI.discard()
            result.actionType = false
        }
        else{
            try{
                domainI.save(flush: true)
                result.actionType = true
            }catch(Exception ex){
                result.actionType = false
                ex.printStackTrace()
                domainI.discard()
            }

            if(result.actionType){
                result.actionMessage = messageSource.getMessage("default.updated.message", [] as Object[], Locale.TAIWAN)
            }
            else{
                result.actionMessage = messageSource.getMessage("default.updated.message", [] as Object[], Locale.TAIWAN)
            }
        }

        return result
    }
}
