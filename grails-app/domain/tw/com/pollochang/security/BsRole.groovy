package tw.com.pollochang.security

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
class BsRole implements Serializable {

	private static final long serialVersionUID = 1

	String		manCreated
	Date		dateCreated = new Date()
	String		manLastUpdated
	Date		lastUpdated
	String		note
	String		authority
	String		authorityName


	static mapping = {
		table:'BS_ROLE'
		comment:'系統角色'
		version:true
		//---1.pk PK0
			id					column:"id", generator:"sequence", params: [sequence: "hibernate_sequence"]
			manCreated			column:"MAN_CREATED",		comment:"建檔人員"
			dateCreated			column:"DATE_CREATED",		comment:"建檔時間"
			manLastUpdated		column:"MAN_LAST_UPDATED",	comment:"最後異動人員"
			lastUpdated			column:"LAST_UPDATED",		comment:"最後異動時間"
			note				column:"NOTE",				comment:"資料註記"
			authority			column:"AUTHORITY",			comment:"角色"
			authorityName		column:"AUTHORITY_NAME",	comment:"角色說明"
	}


	static constraints = {
		manCreated			(nullable:false, blank: false, maxSize: 20)
		dateCreated			(nullable:false, blank: false)
		manLastUpdated		(nullable:true, maxSize: 50)
		lastUpdated			(nullable:true)
		note				(nullable:true, maxSize: 50)
		authority			(nullable:false, blank: false, maxSize: 10)
		authorityName		(nullable:false, blank: false, maxSize: 20)
	}

}

