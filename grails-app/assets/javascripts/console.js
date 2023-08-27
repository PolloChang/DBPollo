// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-3.5.1.min
//= require popper.min
//= require bootstrap
//= require_self


/**
 * AJAX 觸發事件
 */
jQuery(document).off('click', 'button[data-dict="ajax"]');
jQuery(document).on('click','button[data-dict="ajax"]',function () {

    let formId = this.dataset.formId;

    let obj = new Map(); //要傳到下一個 function 的物件
    obj.url = (this.dataset.url).toString();
    obj.filterId = this.dataset.filterId;
    obj.data = jQuery(document.getElementById(formId)).serialize();
    obj.action = this.dataset.action;
    let confirmMessage = this.dataset.confirm;

    if(confirmMessage){
        jQuery.confirm({
            theme: 'supervan',
            title: confirmMessage,
            buttons: {
                confirm: function () {
                    executeAjax(obj);
                },
                cancel: function () {
                }
            }
        });
    }else{
        executeAjax(obj);
    }

});


/**
 * 驅動 Ajax
 * @param obj
 */
function executeAjax(obj){
    jQuery.ajax({
        url: obj.url.toString(),
        data: obj.data,
        dataType: "json",
        success: function (json) {
            console.log(json);
            switch (obj.action){
                case "filter":
                    showFilter(
                        obj.filterId,
                        json.data
                    );
                    shownMessage('successMessageId',json.success,'text-success');
                    shownMessage('errorMessageId',json.error ,'text-danger');

                    break;
                case "save":
                    if(json.type){
                        let alertMessageDiv = '<div class="alert alert-success" role="alert">更新完畢</div>'
                        document.getElementById('alert-message').innerHTML = alertMessageDiv;
                        setTimeout(function(){
                            window.location.href = json.page.go;
                        }, 3000);
                    }
                    break;
                case "refreshSelect2":

                    break;
                default:
                    console.log(json);
            }
        },
        error: function (jqXHR, textStatus, errorThrown ) {
            // endLoad();
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
            alert("發生錯誤。");
        }
    });
}

/**
 * 呈現資料
 * @param filterId
 * @param json
 */
function showFilter(filterId,json){
    jsonToHTML(filterId,json);
}

/**
 * 處理訊息
 * @param filterId
 * @param json
 * @param classes
 */
function shownMessage(filterId,json,classes) {
    let messageDiv = "";
    if(json){
        messageDiv = `<span class="${classes}">${json}</span>`;
    }
    document.getElementById(filterId).innerHTML = messageDiv;
}

/**
 * json to HTML table
 * @see <a href="後端查詢出之json格式， javascript如何轉成表格顯示出來">https://ithelp.ithome.com.tw/questions/10199870</a>
 * @param filterId
 * @param json
 */
const jsonToHTML = (filterId,json) => {
    let title,trs,tbody;
    let table = "";
    if(json) {
        if (json.length !== 0) {
            // json = JSON.parse(json);
            title = `<thead><tr>
            <th>#</th>
            ${Object.keys(json[0]).map((el) => `<th scope="col">${el}</th>`).join('')}
            </tr></thead>`;

            trs = json.map((el, index) =>
                `<td>${index + 1}</td>` + Object.values(el).map((td) => `<td>${td}</td>`).join('')
            );

            tbody = `<tbody>${trs.map((el) => `<tr>${el}</tr>`).join('')}</tbody>`;
            table = `<table class="table">${title}${tbody}</table>`;
        } else {
            table = `<div>查無資料</div>`;
        }
    }

    document.getElementById(filterId).innerHTML = table;
}

