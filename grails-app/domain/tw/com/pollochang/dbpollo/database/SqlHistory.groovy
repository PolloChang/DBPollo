package tw.com.pollochang.dbpollo.database

import tw.com.pollochang.common.CommonDomain

class SqlHistory extends CommonDomain{

    DbConfig dbConfig
    boolean executeType = false
    String sqlContent
    String executeMessage

    static mapping = {
        table: 'sql_history'
        comment: 'sql執行紀錄'
        version: true

        manCreated			column:"MAN_CREATED",		comment:"建檔人員"
        dateCreated			column:"DATE_CREATED",		comment:"建檔時間"
        manLastUpdated		column:"MAN_LAST_UPDATED",	comment:"最後異動人員"
        lastUpdated			column:"LAST_UPDATED",		comment:"最後異動時間"
        dbConfig			column:"db_config_id",		comment:"連接資料庫"
        executeType			column:"execute_type",		comment:"執行狀況"
        sqlContent			column:"sql_content",		comment:"執行SQL內容",     type: "text"
        executeMessage  	column:"execute_message",	comment:"執行後的訊息"

    }

    static constraints = {
        manCreated			(nullable:false, blank: false, maxSize: 20)
        dateCreated			(nullable:false, blank: false)
        manLastUpdated		(nullable:true, maxSize: 50)
        lastUpdated			(nullable:true)

        dbConfig            (nullable: true)
        sqlContent          (nullable: true, blank: true)
        executeMessage      (nullable: true, blank: true)

    }
}
