package tw.com.pollochang.security

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class BsUser implements Serializable {
	String		manCreated
	Date		dateCreated = new Date()
	String		manLastUpdated
	Date		lastUpdated
	String		note
	String		username
	String		password
	boolean		enabled = true
	boolean		accountExpired = false
	boolean		accountLocked = false
	boolean		passwordExpired = false
	String		email
	String		firstName
	String		lastName


	static mapping = {
		table:'BS_USER'
		comment:'系統使用者帳號'
		version:true
		//---1.pk PK0
			id					column:"id", generator:"sequence", params: [sequence: "hibernate_sequence"]
			manCreated			column:"MAN_CREATED",		comment:"建檔人員"
			dateCreated			column:"DATE_CREATED",		comment:"建檔時間"
			manLastUpdated		column:"MAN_LAST_UPDATED",	comment:"最後異動人員"
			lastUpdated			column:"LAST_UPDATED",		comment:"最後異動時間"
			note				column:"NOTE",				comment:"資料註記"
			username			column:"USERNAME",			comment:"帳號"
			password			column:"PASSWORD",			comment:"密碼"
			enabled				column:"ENABLED",			comment:"帳戶是否可用"
			accountExpired		column:"ACCOUNT_EXPIRED",	comment:"帳戶是否過期"
			accountLocked		column:"ACCOUNT_LOCKED",	comment:"帳戶是否被鎖定"
			passwordExpired		column:"PASSWORD_EXPIRED",	comment:"密碼是否過期"
			email				column:"EMAIL",				comment:"電子信箱"
			firstName			column:"FIRST_NAME",		comment:"姓氏"
			lastName			column:"LAST_NAME",			comment:"名字"
	}


	static constraints = {
		manCreated			(nullable:false, blank: false, maxSize: 20)
		dateCreated			(nullable:false, blank: false)
		manLastUpdated		(nullable:true, maxSize: 50)
		lastUpdated			(nullable:true)
		note				(nullable:true, maxSize: 50)
		username			(nullable:false, blank: false, maxSize: 20)
		password			(nullable:false, blank: false, maxSize: 500)
		enabled				(nullable:false, blank: false)
		accountExpired		(nullable:false, blank: false)
		accountLocked		(nullable:false, blank: false)
		passwordExpired		(nullable:false, blank: false)
		email				(nullable:true, maxSize: 100)
		firstName			(nullable:true, maxSize: 10)
		lastName			(nullable:false, blank: false, maxSize: 50)
	}

	Set<BsRole> getAuthorities() {
		(BsUserRole.findAllByUser(this) as List<BsUserRole>)*.role as Set<BsRole>
	}

}

