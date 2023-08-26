package tw.com.pollochang.security

import org.springframework.http.HttpMethod

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes=['configAttribute', 'httpMethod', 'url'])
@ToString(includes=['configAttribute', 'httpMethod', 'url'], cache=true, includeNames=true, includePackage=false)
class BsRequestMap implements Serializable {

	private static final long serialVersionUID = 1

	String		manCreated
	Date		dateCreated = new Date()
	String		manLastUpdated
	Date		lastUpdated
	String		note
	String		appNo
	String		appName
	Integer		idx = 0
	Boolean		isShow = true
	String		configAttribute
	HttpMethod  httpMethod
	String		url
	String 		controller
	String		action


	static mapping = {
		table:'BS_REQUEST_MAP'
		comment:'系統角色可使用程式對應檔'
		version:true
		id					column:"id", generator:"sequence", params: [sequence: "hibernate_sequence"]
		manCreated			column:"MAN_CREATED",		comment:"建檔人員"
		dateCreated			column:"DATE_CREATED",		comment:"建檔時間"
		manLastUpdated		column:"MAN_LAST_UPDATED",	comment:"最後異動人員"
		lastUpdated			column:"LAST_UPDATED",		comment:"最後異動時間"
		note				column:"NOTE",				comment:"資料註記"
		appNo				column:"APP_NO",			comment:"程式代號"
		appName				column:"APP_NAME",			comment:"程式名稱"
		idx					column:"IDX",				comment:"排序"
		isShow				column:"IS_SHOW",			comment:"顯示"
		configAttribute		column:"CONFIG_ATTRIBUTE",	comment:"配置屬性"
		httpMethod			column:"HTTP_METHOD",		comment:"http_method"
		url					column:"URL",				comment:"url"
		controller			column:"controller",		comment:"controller"
		action				column:"action",			comment:"action"
	}


	static constraints = {
		manCreated			(nullable:false, blank: false, maxSize: 20)
		dateCreated			(nullable:false, blank: false)
		manLastUpdated		(nullable:true, maxSize: 50)
		lastUpdated			(nullable:true)
		note				(nullable:true, maxSize: 50)
		appNo				(nullable:false, blank: false, maxSize: 5,unique: true)
		appName				(nullable:false, blank: false, maxSize: 10)
		idx					(nullable:false, blank: false)
		isShow				(nullable:false, blank: false)
		configAttribute		(nullable:false, blank: false, maxSize: 50)
		httpMethod			(nullable:true, maxSize: 10)
		url					(nullable:false, blank: false, maxSize: 50)
		controller			(nullable:true, maxSize: 10)
		action				(nullable:true, maxSize: 10)
	}

}

