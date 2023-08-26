package tw.com.pollochang.security

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.codehaus.groovy.util.HashCodeHelper
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class BsUserRole implements Serializable {

	private static final long serialVersionUID = 1

	String		manCreated
	Date		dateCreated = new Date()
	String		manLastUpdated
	Date		lastUpdated
	String		note
	BsUser		user
	BsRole		role


	static mapping = {
		table:'BS_USER_ROLE'
		comment:'帳號與角色對表'
		version:true

		id					column:"id", generator:"sequence", params: [sequence: "hibernate_sequence"]
		manCreated			column:"MAN_CREATED",		comment:"建檔人員"
		dateCreated			column:"DATE_CREATED",		comment:"建檔時間"
		manLastUpdated		column:"MAN_LAST_UPDATED",	comment:"最後異動人員"
		lastUpdated			column:"LAST_UPDATED",		comment:"最後異動時間"
		note				column:"NOTE",				comment:"資料註記"
		user				column:"USER_ID",			comment:"帳號"
		role				column:"ROLE_ID",			comment:"角色"
	}


	static constraints = {
		manCreated			(nullable:false, blank: false, maxSize: 20)
		dateCreated			(nullable:false, blank: false)
		manLastUpdated		(nullable:true, maxSize: 50)
		lastUpdated			(nullable:true)
		note				(nullable:true, maxSize: 50)
		user				(nullable:false, blank: false)
		role				(nullable:false, blank: false)
	}

	@Override
	boolean equals(other) {
		if (other instanceof BsUserRole) {
			other.userId == user?.id && other.roleId == role?.id
		}
	}

	@Override
	int hashCode() {
		int hashCode = HashCodeHelper.initHash()
		if (user) {
			hashCode = HashCodeHelper.updateHash(hashCode, user.id)
		}
		if (role) {
			hashCode = HashCodeHelper.updateHash(hashCode, role.id)
		}
		hashCode
	}

	static get(long userId, long roleId) {
		criteriaFor(userId, roleId).get()
	}

	static boolean exists(long userId, long roleId) {
		criteriaFor(userId, roleId).count()
	}

	private static DetachedCriteria criteriaFor(long userId, long roleId) {
		BsUserRole.where {
			user == BsUser.load(userId) &&
					role == BsRole.load(roleId)
		}
	}

	static BsUserRole create(BsUser user, BsRole role, boolean flush = false) {
		def instance = new BsUserRole(user: user, role: role)
		instance.save(flush: flush)
		instance
	}

	static boolean remove(BsUser u, BsRole r) {
		if (u != null && r != null) {
			BsUserRole.where { user == u && role == r }.deleteAll()
		}
	}

	static int removeAll(BsUser u) {
		u == null ? 0 : BsUserRole.where { user == u }.deleteAll() as int
	}

	static int removeAll(BsRole r) {
		r == null ? 0 : BsUserRole.where { role == r }.deleteAll() as int
	}

}

