package tw.com.pollochang.dbpollo.database

import tw.com.pollochang.base.BsDomain

class DbConfig extends BsDomain{
    static auditable = [ignore: ['dateCreated', 'lastUpdated', 'manCreated', 'manLastUpdated']]

    String name
    DBType type
    String host
    int port = 0
    String dbname
    String username
    String password

    static mapping = {
        table: 'db_config'
        comment: '資料庫設定檔'
        version: true

        id                  column: "id",               generator:'identity'
        manCreated			column:"MAN_CREATED",		comment:"建檔人員"
        dateCreated			column:"DATE_CREATED",		comment:"建檔時間"
        manLastUpdated		column:"MAN_LAST_UPDATED",	comment:"最後異動人員"
        lastUpdated			column:"LAST_UPDATED",		comment:"最後異動時間"
        name                column: "name",             comment: "資料庫連線名稱"
        type                column: "type",             comment: "資料庫類型"
        host                column: "host",             comment: "資料庫位址"
        port                column: "port",             comment: "連接埠"
        dbname              column: "dbname",           comment: "資料庫名稱"
        username            column: "username",         comment: "使用者名稱"
        password            column: "password",         comment: "使用者密碼"
    }

    static constraints = {
        manCreated			(nullable:false, blank: false, maxSize: 20)
        dateCreated			(nullable:false, blank: false)
        manLastUpdated		(nullable:true, maxSize: 50)
        lastUpdated			(nullable:true)

        name                (nullable: false,blank: false,maxSize: 20)
        type                (nullable: false)
        host                (nullable: false,blank: false,maxSize: 20)
        dbname              (nullable: false,blank: false,maxSize: 20)
        username            (nullable: false,blank: false,maxSize: 20)
        password            (nullable: false,blank: false,maxSize: 20)
    }
}
