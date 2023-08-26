package tw.com.pollochang.security

import grails.gorm.transactions.Transactional
import grails.web.databinding.DataBinder
import grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.context.MessageSource
import tw.com.pollochang.security.BsRequestMap

/**
 * 系統程式
 */
@Transactional
class BsRequestMapService implements DataBinder {

    MessageSource messageSource

    LinkedHashMap filter(GrailsParameterMap params){

        LinkedHashMap result = [:]

        String[] strL = []
        String[] booleanL = []
        String[] likeL = ['appNo','appName']
        String[] mulSelectL = []
        String[] longL = []

        String[] dtL = []
        def dtSEL = []
        dtL.each {
            dtSEL << "${it}1"
            dtSEL << "${it}2"
        }

        def bsRequestMapL = BsRequestMap.createCriteria().list(params) {

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

        result.rows = bsRequestMapL.collect { it ->
            [

                    id                  : it?.id,
                    appNo               : it?.appNo,
                    appName             : it?.appName,
                    isShow              : it?.isShow?"是":"否",
                    configAttribute     : it?.configAttribute,
                    httpMethod          : it?.httpMethod,
                    url                 : it?.url,
                    idx                 : it?.idx,
            ]
        }


        return result

    }

    /**
     * 新增資料
     * @param params
     * @return
     */
    LinkedHashMap insert(GrailsParameterMap params) {
        return _saveInstance(new BsRequestMap(), params, { BsRequestMap bsRequestMapI ->
            bsRequestMapI.manCreated = '系統管理員'
            bsRequestMapI.validate()
        })
    }

    /**
     * 更新資料
     * @param params
     * @return
     */
    LinkedHashMap update(GrailsParameterMap params){
        return _saveInstance(BsRequestMap.get(params.id as long), params, { BsRequestMap bsRequestMapI ->
            bsRequestMapI.manLastUpdated = '系統管理員'
            bsRequestMapI.validate()
        })
    }

    /**
     * 共同處理
     * @param ex100I
     * @param params
     * @param closure
     * @return
     */
    LinkedHashMap _saveInstance(BsRequestMap bsRequestMapI, GrailsParameterMap params, Closure<?> closure) {
        LinkedHashMap result = [
                actionType:false,
                acrtionMessage:''
        ]
        result.bean = bsRequestMapI
        closure(bsRequestMapI)

        List include_col = [
                'strings','integers','atms','status','article'
        ]

        bindData(bsRequestMapI, params["bsRequestMap"], [include:include_col])
        int pageDataVersion = params.bsRequestMap.version?(params.bsRequestMap?.version as int):0
        if(bsRequestMapI.version != pageDataVersion && params.bsRequestMap.id){

            result.dataVersionDifferent = true
            bsRequestMapI.discard()
        } else if (bsRequestMapI.hasErrors()) { //失敗

            ArrayList errorColumn = []
            bsRequestMapI.errors.allErrors.eachWithIndex  {item, index ->
                errorColumn[index] = [item?.arguments,item?.defaultMessage]
            }
            bsRequestMapI.discard()
            result.actionType = false
        }
        else{
            try{
                bsRequestMapI.save(flush: true)
                result.actionType = true
            }catch(Exception ex){
                result.actionType = false
                ex.printStackTrace()
                bsRequestMapI.discard()
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

    /**
     * 刪除資料
     * @param params
     * @return
     */
    LinkedHashMap delete(GrailsParameterMap params){

        LinkedHashMap result = [
                actionType:false,
                acrtionMessage:''
        ]

        BsRequestMap bsRequestMapI = result.bean = BsRequestMap.get(params.id as long)
        int pageDataVersion = params.ex100.version?(params.bsRequestMap?.version as int):0
        if(bsRequestMapI.version != pageDataVersion && params.bsRequestMap.id){

            result.dataVersionDifferent = true
            bsRequestMapI.discard()
        }else{

            try{
                bsRequestMapI.delete()
                result.actionType = true
            }catch(Exception ex){
                result.actionType = false
                ex.printStackTrace()
                bsRequestMapI.discard()
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
}
