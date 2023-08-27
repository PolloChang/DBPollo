package tw.com.pollochang.bs

import grails.gorm.transactions.Transactional
import groovy.sql.GroovyRowResult
import groovy.sql.Sql

@Transactional
class NavService {

    def dataSource

    def getUserUseGroupList() {
        String queryString = """
                            select 
                            a.app_group_no,
                            (select t.app_group_name from bs_app_group t where t.app_group_no = a.app_group_no) app_group_name
                            from(
                            select distinct 
                            a.app_group_no 
                            from bs_app_list_group a 
                            where exists(select 1 from bs_request_map t where t.app_no = a.app_no and strpos(t.config_attribute,'ROLE_ADMIN') > 0 )
                            ) a
                            """
        Sql queryResult = new Sql(dataSource)
        List<GroovyRowResult> groupL =  queryResult.rows(queryString)

        return groupL
    }

    def getUserAppList(){
        String queryString = """
                        select 
                        a.app_no ,
                        a.app_name,
                        (select t.app_group_no from bs_app_list_group t where t.app_no = a.app_no) app_group_no,
                        a.idx ,
                        a.controller ,
                        a.action
                        from bs_request_map a 
                        where strpos(a.config_attribute,'ROLE_ADMIN') > 0
                        and exists(select 1 from bs_app_list_group t where t.app_no = a.app_no)
                        """
        Sql queryResult = new Sql(dataSource)
        List<GroovyRowResult> appL =  queryResult.rows(queryString)

        return appL
    }
}

