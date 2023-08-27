package tw.com.pollochang


class AppTagLib {
//    static defaultEncodeAs = [taglib:'html']
    static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    static namespace = "app"

    /**
     * 查詢條件table
     */
    Closure searchTable = {attrs,body ->


        out <<
                """
        <div class="row">
            <div class="col">
                <table class="table table-bordered">
                    <colgroup>
                        <col width="15%">
                        <col width="35%">
                        <col width="15%">
                        <col width="35%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th colspan="4">
                            ${asset:img(src:"/icons/border-width.svg")}
                            查詢條件
                        </th>
                    </tr>
                    </thead>
        """
        out << body()
        out <<
                """    
                </table>
            </div>
        </div>
        """
    }

    Closure searchFilter = {attrs,body ->

        out <<
                """
            <div class="row">
                <div class="col">
                    ${asset.image(src: '/icons/border-width.svg')}
                    查詢結果
                </div>
            </div>
            """

        out <<
                """
            <div class="row">
                <div class="col">
            """
        out << body()
        out <<
                """
                </div>
            </div>
            """
    }

    /**
     * 查詢結果
     */
    Closure filterTable = {attrs,body ->
        String id = attrs.remove("id")

        out <<
                """
        <table
            id="${id}"
            data-toggle="table"
            data-pagination="true"
        >
            <thead>
        """
        out << body()
        out <<
                """
            </thead>
        </table>
        <script type='text/javascript'>
            jQuery(document.getElementById('${id}')).bootstrapTable();
        </script>
        """
    }

    /**
     * 作業表單
     */
    Closure formTable = {attrs,body ->

        String tableTitle = attrs.remove("tableTitle")

        out <<
                """
        <table class="table table-bordered">
            <colgroup>
                <col width="15%">
                <col width="35%">
                <col width="15%">
                <col width="35%">
            </colgroup>
            <thead>
                <tr>
                    <th colspan="4">
                        ${asset:img(src:"/icons/border-width.svg")}
                        ${tableTitle}
                    </th>
                </tr>
            </thead>
            <tbody>
        """
        out << body()
        out <<
                """
            </tbody>
        </table>
        """
    }

    /**
     * 編輯頁面
     */
    Closure editContainer = {attrs,body ->
        out << """<div class="container-fluid">"""
        out << body()
        out << """</div>"""
    }

    /**
     * 編輯頁面 body
     */
    Closure editContainerBody = {attrs,body ->
        String formId = attrs.remove("formId")
        def instance = attrs.remove("instance")
        String alertId = attrs.remove("alert-id")?:"form-alert"
        out <<
                """
            <div class="row">
                <div class="col">
                    <span id="${alertId}"></span>
                </div>
            </div>
            <div class="row">
                <div class="col">
            """
        out << g.form(name:"${formId}",enctype:"multipart/form-data",body())

        if(instance?.id){
            out << g.render(template:"/bs/dictAdmin/domainMessage",model:[instance:instance])

        }
        out <<
                """
                    </form>
                </div>
            </div>
            """
    }

    /**
     * 編輯頁面 footer
     */
    Closure editContainerFooter = {attrs,body ->
        out <<
                """
            <div class="row">
                <div class="col">
            """
        out << body()
        out <<
                """
                </div>
            </div>
            """
    }

    /**
     * 編輯頁面 tabs
     */
    Closure editContainerTabs = {attrs,body ->

        String tabId = attrs?.remove("tabId")?:UUID.randomUUID().toString()
        String tabContentId = attrs?.remove("tabContentId")?:tabId
        List<HashMap> tabList = attrs?.remove("tabList")

        out <<
                """
            <div class="row">
                <div class="col">
                    <ul class="nav nav-tabs" id="${tabId}" role="tablist">
            """
        tabList.eachWithIndex { tabI,idx ->
            out << """<li class="nav-item" role="presentation">"""
            out <<
                    """
                <button 
                    id="${tabI?.tabId}-tab"
                    class="nav-link ${(tabI?.active)?'active':''}"
                    data-dict="openEditTab"
                    data-bs-toggle="tab"
                    data-url="${tabI?.url}"
                    data-bs-target="#${tabI?.tabId}" 
                    type="button" 
                    role="tab" 
                    aria-controls="${tabI?.tabId}" 
                    aria-selected="${(tabI?.active)}"
                >${tabI?.tabName}</button>
                """
            out << """</li>"""
        }
        out <<
                """
            </ul>
            <div class="tab-content" id="${tabContentId}">
            """
        tabList.eachWithIndex { tabI,idx ->
            out << """<div class="tab-pane fade ${(tabI?.active)?'show active':''}" id="${tabI?.tabId}" role="tabpanel" aria-labelledby="${tabI?.tabId}-tab"></div>"""
        }
        out <<
                """
                    </div>
                </div>
            </div>
            """

        //自動載入 active 分頁
        out <<
                """
            <script type="text/javascript">
                jQuery("#${tabId} li[role='presentation'] > button[aria-selected='true']").trigger('click');
            </script>
            """
    }
}

