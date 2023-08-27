package tw.com.pollochang.common

import org.grails.datastore.gorm.GormEntity

class CommonController {

    protected LinkedHashMap afterInsert(
            GormEntity domainI,
            boolean actionType,
            String tabGroup,
            String tabName,
            String link
    ){
        LinkedHashMap result = [:]
        if(!actionType){
            result.actionMessage = g.render(template: '/tw/com/pollochang/bs/errorMessage' , model: [beanI:result.bean])
        }
        else{
            //更新tab資訊

            List scriptArrays = []
            scriptArrays << [mode: 'execute', script: """parent.openTab(
                    '${tabGroup}',
                    '${tabGroup}-${domainI?.id}',
                    '${tabName}',
                    '${link}'
                );"""]
            scriptArrays << [mode: 'execute', script: """parent.closeTab('${tabGroup}-0');"""]
            result.scriptArrays = scriptArrays
        }

        return result
    }

    protected LinkedHashMap afterUpdate(
            GormEntity domainI,
            boolean actionType,
            String tabGroup,
            String link
    ){
        LinkedHashMap result = [:]
        if(!actionType){
            result.actionMessage = g.render(template: '/tw/com/pollochang/bs/errorMessage' , model: [beanI:domainI])
        }
        else{
            //更新tab資訊

            List scriptArrays = []
            scriptArrays << [mode: 'execute', script: """parent.refreshTab({
                        tabId:'${tabGroup}-${domainI?.id}',
                        src: '${link}'
                    }
                );"""]
            result.scriptArrays = scriptArrays
        }
        return result
    }

    protected afterDelete(
            GormEntity domainI,
            long domainId,
            boolean actionType,
            String tabGroup
    ){
        LinkedHashMap result = [:]
        if(!actionType){
            result.actionMessage = g.render(template: '/tw/com/pollochang/bs/errorMessage' , model: [beanI:domainI])
        }else{
            List scriptArrays = []
            scriptArrays << [mode: 'execute', script: """parent.closeTab('${tabGroup}-${domainId}');"""]
            result.scriptArrays = scriptArrays
        }

        return result
    }
}
