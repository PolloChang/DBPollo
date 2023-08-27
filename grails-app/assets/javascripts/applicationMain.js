// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-3.6.0
//= require popper.min
//= require bootstrap
//= require bootstrap.bundle
//= require bootstrap-table
//= require jquery-confirm
//= require formatter
//= encoding UTF-8


jQuery(document).off('click', 'button[data-dict="ajax"]');
jQuery(document).on('click','button[data-dict="ajax"]',function () {
    let url = this.dataset.url;
    let formId = this.dataset.formId;
    let data = jQuery(document.getElementById(formId)).serialize();
    let confirmMessage = this.dataset.confirm;

    if(confirmMessage){
        jQuery.confirm({
            theme: 'supervan',
            title: confirmMessage,
            buttons: {
                confirm: function () {
                    executeAjax(url,data);
                },
                cancel: function () {
                }
            }
        });
    }else{
        executeAjax(url,data);
    }



});

jQuery(document).off('click', 'button[data-dict="openEditTab"]');
jQuery(document).on('click','button[data-dict="openEditTab"]',function () {

    let url = this.dataset.url;
    let bsTarget = this.dataset.bsTarget;
    bsTarget = bsTarget.replaceAll("#","");
    let appendDiv = jQuery('#'+bsTarget);

    if(appendDiv.children().length === 0){
        jQuery.ajax({
            url: url,
            type: "POST",
            dataType: "html",
            success: function (html) {
                appendDiv.append(html);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert('系統發生錯誤');
            },
            complete: function () {

            }
        });
    }

});
/**
 * 驅動 Ajax
 * @param url
 * @param data
 */
function executeAjax(url,data){
    jQuery.ajax({
        url: url,
        data: data,
        type: "POST",
        dataType: "json",
        beforeSend: function (jqXHR, settings) {

        },
        success: function (json) {
            let scriptArrays = json.scriptArrays;
            if(scriptArrays){
                scriptArrays.forEach((item, index, arr) => {
                    let mode = item.mode;
                    if(mode === "execute"){
                        let scriptString = item.script;
                        let scriptDiv = document.createElement('script');
                        scriptDiv.type = 'text/javascript';
                        document.body.append(scriptDiv);
                        scriptDiv.text = scriptString;
                        document.body.removeChild(scriptDiv);
                    }
                });
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert('系統發生錯誤');
        },
        complete: function () {

        }
    });
}

/**
 * 顯示錯誤訊息
 * @param replaceId
 * @param actionMessageHtml
 */
function showAlert(replaceId,actionMessageHtml){
    let changItem = jQuery(document.getElementById(replaceId));
    changItem.html(actionMessageHtml);
}

/**
 * 新增 tab
 */
jQuery(document).off('click', 'button[data-dict="openTab"]');
jQuery(document).on('click','button[data-dict="openTab"]',function () {

    let appControllerName = jQuery('#appControllerName').val();
    let url = this.dataset.url;
    let tabTitle = this.dataset.tabTitle;
    let tabId = appControllerName+'-'+this.dataset.tabId;


    parent.openTab(
        appControllerName,
        tabId,
        tabTitle,
        url
    );
});

/**
 * 開啟tab
 */
jQuery(document).off('click', 'a[data-dict="openTab"]');
jQuery(document).on('click','a[data-dict="openTab"]',function () {

    let appControllerName = jQuery('#appControllerName').val();
    let url = this.dataset.url;
    let tabTitle = this.dataset.tabTitle;
    let tabId = appControllerName+'-'+this.dataset.tabId;


    parent.openTab(
        appControllerName,
        tabId,
        tabTitle,
        url
    );
});

/**
 * 開啟model
 */
jQuery(document).off('click', 'button[data-dict="openModel"],a[data-dict="openModel"]');
jQuery(document).on('click','button[data-dict="openModel"],a[data-dict="openModel"]',function () {
    let modelId = this.dataset.modelId;
    let modelTitle = this.dataset.modelTitle;
    let dictModel = new bootstrap.Modal(document.getElementById(modelId), {
        keyboard: false
    });
    let url = this.dataset.url;
    let modelContent = jQuery('#'+modelId+' .modal-content');

    refreshModel(
        modelId,
        modelTitle,
        url
    );

});

/**
 * 刷新model
 * @param modelId
 * @param modelTitle
 * @param url
 */
function refreshModel(
    modelId,
    modelTitle,
    url
){
    let myModalEl = document.getElementById(modelId)
    let dictModel = bootstrap.Modal.getInstance(myModalEl);
    let modelContent = jQuery('#'+modelId+' .modal-content');

    jQuery.ajax({
        url: url,
        data: {"modelTitle":modelTitle,"modelId":modelId},
        type: "POST",
        dataType: "html",
        beforeSend: function (jqXHR, settings) {
            dictModel.dispose();
        },
        success: function (html) {
            modelContent.html(html);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert('系統發生錯誤');
        },
        complete: function () {
            let myModal = new bootstrap.Modal(myModalEl);
            myModal.show(myModalEl);
        }
    });
}

/**
 * 關閉model
 * @param modelId
 */
function closeModel(modelId){
    let myModalEl = document.getElementById(modelId)
    let dictModel = bootstrap.Modal.getInstance(myModalEl);
    let modelContent = jQuery('#'+modelId+' .modal-content');
    modelContent.html("");
    dictModel.hide();
}