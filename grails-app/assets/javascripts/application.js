// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-3.6.0
//= require bootstrap
//= require popper
//= require_self
//= encoding UTF-8


/**
 * 關閉App
 */
jQuery(document).off('click', 'button[data-dict="closeApp"]');
jQuery(document).on('click','button[data-dict="closeApp"]',function () {
    let tabPaneId = this.dataset.dictAppNo;
    let tabId = tabPaneId+"-li";
    let tabPaneI = jQuery(document.getElementById(tabPaneId));
    let tabIdI = jQuery(document.getElementById(tabId));

    if(tabIdI.length){
        tabIdI.remove();
    }

    if(tabPaneI.length){
        tabPaneI.remove();
    }

    jQuery('#dict-app-tab li:last-child a').tab('show');

});

/**
 * 關閉APP內容
 */
jQuery(document).off('click', 'button[data-dict-click="close-app-content"]');
jQuery(document).on('click','button[data-dict-click="close-app-content"]',function () {
    let screenTabsId = this.dataset.dictAppId;
    let tabPaneId = this.dataset.dictId;
    let tabId = tabPaneId+"-li";
    let tabPaneI = jQuery(document.getElementById(tabPaneId));
    let tabIdI = jQuery(document.getElementById(tabId));

    if(tabIdI.length){
        tabIdI.remove();
    }

    if(tabPaneI.length){
        tabPaneI.remove();
    }

    jQuery('#'+screenTabsId+' li:last-child a').tab('show');
});

/**
 * 開啟程式
 */
jQuery(document).off('click', 'button[data-dic="openApp"]');
jQuery(document).on('click','button[data-dic="openApp"]',function () {

    let dicAppNo = this.dataset.dicAppNo;
    let dicAppName = this.dataset.dicAppName;
    let dicAppUrl = this.dataset.dicAppUrl;

    let newAppContentId = "app-"+dicAppNo;
    let newTabLiId = newAppContentId+"-li";
    let newTabAId = newAppContentId+"-tab";
    let newAppContentTabLUlId = newAppContentId+"-second-tab";
    let newAppContentTabLiId = newAppContentId+"-second-li";
    let newAppContentTabAId = newAppContentId+"-second-tab-a";
    let newAppContentContentId = newAppContentId+"-second";
    let newAppContentDivId = newAppContentId+"-second-div";

    let changItem = jQuery(document.getElementById(newTabAId));
    if(changItem.length) { //判斷有元素
        jQuery(changItem).tab('show');
    }else{
        let appTabDiv = jQuery(document.getElementById('dict-app-tab'));
        let appContentDiv = jQuery(document.getElementById('dict-app-content'));
        let newTabLi = jQuery(document.createElement("li"));
        let newTabA = jQuery(document.createElement("a"));
        let newTabButton = jQuery(document.createElement("button"));
        let newAppContent = jQuery(document.createElement("div"))
        let newAppContentDiv = jQuery(document.createElement("div"));

        newTabLi.attr("id",newTabLiId);
        newTabLi.attr("class","nav-item border");

        newAppContentDiv.attr("id",newAppContentDivId);
        newAppContentDiv.attr("class","tab-content");

        newTabA.attr("id",newTabAId);
        newTabA.attr("class","nav-link d-inline border-0");
        newTabA.attr("data-toggle","tab");
        newTabA.attr("href","#"+newAppContentId);
        newTabA.attr("role","tab");
        newTabA.attr("aria-controls",newAppContentId);
        newTabA.attr("aria-selected","false");
        newTabA.text(dicAppName);
        newTabLi.append(newTabA);

        newTabButton.attr("class","btn btn-link btn-sm");
        newTabButton.attr("data-dict","closeApp");
        newTabButton.attr("data-dict-app-no",newAppContentId);
        newTabButton.text("x");
        newTabLi.append(newTabButton);

        appTabDiv.append(newTabLi);

        newAppContent.attr("id",newAppContentId);
        newAppContent.attr("class","tab-pane fade");
        newAppContent.attr("role","tabpanel");
        newAppContent.attr("aria-labelledby",newTabAId);


        newAppContent.append(openTabTitle(
            newAppContentTabLUlId,
            newAppContentTabLiId,
            newAppContentTabAId,
            newAppContentContentId,
            "程式首頁"
        ));

        newAppContentDiv.append(openTabContent(
            newAppContentContentId,
            newAppContentTabAId,
            dicAppUrl
        ));

        newAppContent.append(newAppContentDiv);

        appContentDiv.append(newAppContent);

        jQuery(newTabA).tab('show');
        setTimeout(function(){
            jQuery('#'+newAppContentTabAId).tab('show');
        }, 100);


    }


});

/**
 * 開啟tab
 * @param appControllerName
 * @param tabId
 * @param tabTitle
 * @param url
 */
function openTab(
    appControllerName,
    tabId,
    tabTitle,
    url
){
    let tabLUlId     = 'app-'+appControllerName+'-second-tab';
    let appContentDivId = 'app-'+appControllerName+'-second-div';
    let tabLiId      = "app-"+tabId+"-li";
    let tabAId       = "app-"+tabId+"-a";
    let tabContentId = "app-"+tabId+"-content";

    let changItem = jQuery(document.getElementById(tabLiId));
    if(changItem.length) { //判斷有元素
        jQuery(changItem).tab('show');


    }else{

        let tabLUl = jQuery(document.getElementById(tabLUlId));
        let newAppContentDiv = jQuery(document.getElementById(appContentDivId));

        tabLUl.append(appendTab(
            tabLiId,
            tabAId,
            tabContentId,
            tabTitle,
            false,
            tabId
        ));

        newAppContentDiv.append(openTabContent(
            tabContentId,
            tabAId,
            url
        ));

        setTimeout(
            function(){
                jQuery('#'+tabAId).tab('show');
            },
            20
        );

    }
}

/**
 * 更新tab內容
 * @param tab
 */
function refreshTab(tab){
    let tabContentId = "app-"+tab.tabId+"-content-frame";
    let refreshFrameI = document.getElementById(tabContentId);
    refreshFrameI.src = refreshFrameI.src;

}

/**
 * 啟動tab 標籤
 * @param tabLUlId
 * @param tabLiId
 * @param tabAId
 * @param tabContentId
 * @param tabTitle tab標題
 */
function openTabTitle(
    tabLUlId,
    tabLiId,
    tabAId,
    tabContentId,
    tabTitle
){
    let returnDiv;
    let tabLUl = jQuery(document.createElement("ul"));


    tabLUl.attr("id",tabLUlId);
    tabLUl.attr("class","nav nav-tabs app-second-tab");
    tabLUl.attr("role","tablist");


    tabLUl.append(appendTab(
        tabLiId,
        tabAId,
        tabContentId,
        tabTitle
    ));
    returnDiv = tabLUl;

    return returnDiv;
}

/**
 * 新增tab
 * @param tabLiId
 * @param tabAId
 * @param tabContentId
 * @param tabTitle
 * @param isAppTab
 * @param tabId
 * @returns {*|jQuery|HTMLElement}
 */
function appendTab(
    tabLiId,
    tabAId,
    tabContentId,
    tabTitle,
    isAppTab = true,
    tabId = ''
){
    let returnDiv;
    let tabLi = jQuery(document.createElement("li"));
    let tabA = jQuery(document.createElement("a"));

    tabLi.attr("id",tabLiId);
    tabLi.attr("class","nav-item border");

    tabA.attr("id",tabAId);
    tabA.attr("class","nav-link d-inline border-0");
    tabA.attr("data-toggle","tab");
    tabA.attr("href","#"+tabContentId);
    tabA.attr("role","tab");
    tabA.attr("aria-controls",tabContentId);
    tabA.attr("aria-selected","true");
    tabA.text(tabTitle);

    tabLi.append(tabA);
    if(!isAppTab){
        // 子頁面才有
        let newAppContentTabButton = jQuery(document.createElement("button"));
        newAppContentTabButton.attr("class","btn btn-link btn-sm");
        newAppContentTabButton.attr("data-dict-tab-id",tabId);
        newAppContentTabButton.attr("data-dict","closeTab");
        newAppContentTabButton.text("x");
        tabLi.append(newAppContentTabButton);
    }

    returnDiv = tabLi;
    return returnDiv;
}

/**
 * 啟動Tab內容
 * @param tabContentId
 * @param tabAId
 * @param url
 * @returns {*|jQuery|HTMLElement}
 */
function openTabContent(
    tabContentId,
    tabAId,
    url
){
    let returnDiv;
    let content = jQuery(document.createElement("div"));
    let contentIframe = jQuery(document.createElement("iframe"));

    content.attr("id",tabContentId);
    content.attr("class","tab-pane fade");
    content.attr("role","tabpanel");
    content.attr("aria-labelledby",tabAId);
    content.attr('style','height:740px;');
    contentIframe.attr('id',tabContentId+'-frame');
    contentIframe.attr('src',url);
    contentIframe.attr('width','100%');
    contentIframe.attr('frameborder','0');
    contentIframe.attr('style','height: inherit;')
    content.append(contentIframe);

    returnDiv = content;

    return returnDiv;
}

/**
 * 關閉程式 tab
 */
jQuery(document).off('click', 'button[data-dict="closeTab"]');
jQuery(document).on('click','button[data-dict="closeTab"]',function () {
    let tabId = this.dataset.dictTabId;
    closeTab(tabId);

});

/**
 * 關閉tab
 * @param tabId
 */
function closeTab(
    tabId
){
    let tabIdA = String(tabId).split('-');
    let screenTabsId = 'app-'+tabIdA[0]+'-second-tab';
    let tabLiId      = "app-"+tabId+"-li";
    let tabContentId = "app-"+tabId+"-content";

    let tabPaneI = jQuery(document.getElementById(tabContentId));
    let tabIdI = jQuery(document.getElementById(tabLiId));
    if(tabIdI.length){
        tabIdI.remove();
    }

    if(tabPaneI.length){
        tabPaneI.remove();
    }

    setTimeout(
        function(){
            jQuery('#'+screenTabsId+' li:last-child a').tab('show');
        },
        20
    );

}