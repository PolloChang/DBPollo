package tw.com.pollochang.common

import grails.validation.Validateable

abstract class CommonDomain implements Validateable{

    static auditable = [ignore: ['dateCreated', 'lastUpdated', 'manCreated', 'manLastUpdated']]

    String		manCreated
    Date		dateCreated = new Date()
    String		manLastUpdated
    Date		lastUpdated
}
