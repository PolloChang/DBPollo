package tw.com.pollochang.common

import grails.plugin.springsecurity.SpringSecurityService
import grails.web.databinding.DataBinder
import grails.web.servlet.mvc.GrailsParameterMap
import groovy.util.logging.Slf4j
import org.grails.datastore.gorm.GormEntity
import org.springframework.context.MessageSource

@Slf4j
class CommonService implements DataBinder {

    SpringSecurityService springSecurityService
    MessageSource messageSource

    /**
     * 共同處理
     * @param domainI domain
     * @param domainName domain名稱
     * @param include_col 共用儲存欄位
     * @param params 前端頁面參數
     * @param closure 特殊處理閉包
     * @return
     */
    protected LinkedHashMap saveInstance(
            GormEntity domainI,
            String domainName,
            include_col,
            GrailsParameterMap params,
            Closure<?> closure
    ) {
        LinkedHashMap result = [
                actionType:false,
                acrtionMessage:''
        ]

        result.bean = domainI

        if(params.id){
            domainI.manLastUpdated = springSecurityService.currentUser?.username?:"N/A"
        }else{
            domainI.manCreated = springSecurityService.currentUser?.username?:"N/A"
        }

        closure(domainI)

        domainI.validate()

        bindData(domainI, params["${domainName}"], [include:include_col])

        int pageDataVersion = params.dbConfig.version?(params."${domainName}"?.version as int):0
        if(domainI.version != pageDataVersion && params."${domainName}".id){

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


    /**
     * 共用刪除方法
     * @param domainI domain
     * @param domainName domain名稱
     * @param params 前端頁面參數
     * @return
     */
    protected LinkedHashMap delete(
        GormEntity domainI,
        String domainName,
        GrailsParameterMap params
    ) {
        LinkedHashMap result = [
                actionType:false,
                acrtionMessage:''
        ]

        int pageDataVersion = params."${domainName}".version?(params."${domainName}"?.version as int):0
        if(domainI.version != pageDataVersion && params."${domainName}".id){
            result.dataVersionDifferent = true
            domainI.discard()
        }else{

            try{
                domainI.delete()
                result.actionType = true
            }catch(Exception ex){
                result.actionType = false
                log.error(ex.getMessage())
                domainI.discard()
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
