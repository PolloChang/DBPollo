package tw.com.pollochang.dbpollo.manage

import com.pollochang.util.AES
import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
import org.apache.commons.lang3.RandomStringUtils
import tw.com.pollochang.common.CommonService
import tw.com.pollochang.dbpollo.database.DBType
import tw.com.pollochang.dbpollo.database.DbConfig

/**
 * 資料庫連線設定連線資料操作
 */
@Transactional
class DbConfigService extends CommonService {

    private final List include_col = [
            'name','type','host','port','dbname','username','password'
    ]

    /**
     * 查詢
     * @param params
     * @return
     */
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
                type               : it?.type?.toString(),
                host               : it?.host,
                port               : it?.port,
                dbname             : it?.dbname,
                username           : it?.username,
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
        return saveInstance(new DbConfig(), "dbConfig",include_col,params, { DbConfig dbConfigI ->
            DBType dbType = DBType.valueOf(params.dbConfig.type as String)
            String random = RandomStringUtils.randomAlphanumeric(10)
            params.dbConfig.password = AES.encrypt(params.dbConfig.password,random)
            params.dbConfig.type = dbType
            dbConfigI.passwordSalt = random
        })
    }

    /**
     * 更新資料
     * @param params
     * @return
     */
    LinkedHashMap update(GrailsParameterMap params) {

        return saveInstance(DbConfig.get(params.id as long), "dbConfig",include_col,params, { DbConfig dbConfigI ->
            DBType dbType = DBType.valueOf(params.dbConfig.type as String)
            String random = RandomStringUtils.randomAlphanumeric(10)
            params.dbConfig.password = AES.encrypt(params.dbConfig.password,random)
            params.dbConfig.type = dbType
            dbConfigI.passwordSalt = random
        })
    }

    /**
     * 刪除
     * @param params
     * @return
     */
    LinkedHashMap delete(GrailsParameterMap params) {

        return delete(
                DbConfig.get(params.id as long),
                "dbConfig",
                params
        )
    }
}
