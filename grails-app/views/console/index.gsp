<%--
  Created by IntelliJ IDEA.
  User: pollochang
  Date: 8/8/23
  Time: 10:38 PM
--%>
<%@page import="tw.com.pollochang.dbpollo.database.DBType; database.*" %>
<g:set var="sqlScriptFrom" value="${UUID.randomUUID().toString()}"/>
<g:set var="filterId" value="${UUID.randomUUID().toString()}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="console"/>
    <asset:javascript src="sqlParser.js" />
    <asset:stylesheet src="codemirror/lib/codemirror.css" />
    <asset:stylesheet src="codemirror/addon/hint/show-hint.css" />
    <asset:javascript src="codemirror/lib/codemirror.js" />
    <asset:javascript src="codemirror/sql.js" />
    <asset:javascript src="codemirror/addon/hint/show-hint.js"/>
    <asset:javascript src="codemirror/addon/hint/sql-hint.js"/>
    <style>
    .CodeMirror {
        border-top: 1px solid black;
        border-bottom: 1px solid black;
    }
    </style>
</head>

<body>
<main>
    <form id="${sqlScriptFrom}">

%{--        <div class="accordion" id="accordion">--}%
%{--            <div class="card">--}%
%{--                <div class="card-header" id="headingOne">--}%
%{--                    <h2 class="mb-0">--}%
%{--                        <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse" data-target="#accordion-setting" aria-expanded="true" aria-controls="accordion-setting">--}%
%{--                            資料庫連線設定--}%
%{--                        </button>--}%
%{--                    </h2>--}%
%{--                </div>--}%
%{--            </div>--}%
%{--            <div id="accordion-setting" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">--}%
%{--                <div class="card-body">--}%
%{--                    <div class="container-fluid">--}%
%{--                        <div class="row">--}%
%{--                            <div class="col-3">--}%
%{--                                <div class="form-group col-form-label">--}%
%{--                                    <label for="db-type">db-type</label>--}%
%{--                                    <g:select name="db-type" class="form-control" from="${tw.com.pollochang.dbpollo.database.DBType}" value="POSTGRESQL" />--}%
%{--                                </div>--}%
%{--                            </div>--}%
%{--                            <div class="col-3">--}%
%{--                                <div class="form-group col-form-label">--}%
%{--                                    <label for="db-name">db-name</label>--}%
%{--                                    <g:textField name="db-name" class="form-control" value="dictdb" />--}%
%{--                                </div>--}%
%{--                            </div>--}%
%{--                        </div>--}%
%{--                        <div class="row">--}%
%{--                            <div class="col-3">--}%
%{--                                <div class="form-group col-form-label">--}%
%{--                                    <label for="db-host">db-host</label>--}%
%{--                                    <g:textField name="db-host" class="form-control" value="127.0.0.1" />--}%
%{--                                </div>--}%
%{--                            </div>--}%
%{--                            <div class="col-3">--}%
%{--                                <div class="form-group col-form-label">--}%
%{--                                    <label for="db-port">db-port</label>--}%
%{--                                    <g:textField name="db-port" class="form-control"  value="5432" />--}%
%{--                                </div>--}%
%{--                            </div>--}%
%{--                            <div class="col-3">--}%
%{--                                <div class="form-group col-form-label">--}%
%{--                                    <label for="db-username">db-username</label>--}%
%{--                                    <g:passwordField name="db-username" class="form-control" value="dict_sa" />--}%
%{--                                </div>--}%
%{--                            </div>--}%
%{--                            <div class="col-3">--}%
%{--                                <div class="form-group col-form-label">--}%
%{--                                    <label for="db-password">db-password</label>--}%
%{--                                    <g:passwordField name="db-password" class="form-control"  value="fd831554" />--}%
%{--                                </div>--}%
%{--                            </div>--}%
%{--                        </div>--}%
%{--                    </div>--}%
%{--                </div>--}%
%{--            </div>--}%
%{--        </div>--}%
        <div class="container-fluid">
            <div class="row">
                <div class="col-6">
                    <div class="form-group col-form-label">
                        <label for="dbConfigId">資料庫</label>
                        <g:select name="dbConfigId" class="form-control"
                                  from="${tw.com.pollochang.dbpollo.database.DbConfig.list()}"
                                  noSelection="['':'請選擇']"
                                  optionKey="id" optionValue="name"
                        />
                    </div>
                </div>
                <div class="col-6">
                    <div id="schemaDiv" class="form-group col-form-label">
                        <label for="schema">schema</label>
                        <select id="schema" name="schema" class="form-control"><option></option></select>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <div class="form-group col-form-label">
                        <label for="sql-text">sql</label>
                        <g:textArea name="sql-text" class="form-control" id="sql-text" rows="10" value="CREATE TABLE TEST1 (COL1 int);

SELECT * FROM TEST0;
SELECT * FROM TEST1;SELECT *
FROM TEST2;

INSERT INTO BIOBANK.TEST1(COL1) VALUES(1);
SELECT * FROM TEST1;

DROP TABLE TEST1;" />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <button
                            type="button" class="btn btn-primary"
                            onclick="executeSql()"
                    >
                        ${message(code: "default.button.execute.label")}(Ctrl + Enter)
                    </button>
                    <button
                            type="button" class="btn btn-primary"
                            onclick="changeHintOptions()"
                    >
                        changeHintOptions
                    </button>
                </div>
            </div>
        </div>
    </form>
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <div id="${filterId}" ></div>
            </div>
        </div>
    </div>
</main>
<div class="navbar navbar-inverse navbar-fixed-bottom">
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <div id="errorMessageId" ></div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div id="successMessageId" ></div>
            </div>
        </div>
    </div>
</div>
</footer>
<script type="text/javascript">

    var editor;
    var sqlParser = window.sqlParser;

    window.onload = function() {
        let mime = 'text/x-plsql';
        // get mime type
        if (window.location.href.indexOf('mime=') > -1) {
            mime = window.location.href.substr(window.location.href.indexOf('mime=') + 5);
        }
        editor = CodeMirror.fromTextArea(document.getElementById('sql-text'), {
            mode: mime,
            indentWithTabs: true,
            smartIndent: true,
            lineNumbers: true,
            matchBrackets : true,
            autofocus: true,
            extraKeys: {"Ctrl-\\": "autocomplete"},
            hintOptions: {
                completeSingle: false,
                tables: {
                    users: ["name", "score", "birthDate"],
                    countries: ["name", "population", "size"]
                }
            }
        });

        editor.on('keypress',() =>{
           editor.showHint();
            changeHintOptions();
        });
    };

    document.addEventListener("keydown", function(e) {
        if (e.ctrlKey && e.code === "Enter"){ //KeyC
            executeSql();
            e.preventDefault();
        }
    });

    /**
     * 執行SQL
     */
    function executeSql(){
        document.getElementById('sql-text').value = editor.getValue();
        let obj = new Map(); //要傳到下一個 function 的物件
        obj.url = "${createLink(controller: 'console', action: "executeSql")}";
        obj.filterId = "${filterId}";
        obj.data = jQuery(document.getElementById("${sqlScriptFrom}")).serialize();
        obj.action = "filter";
        //let sqlStr = editor.getValue(); //取得全部內容
        let i = 0;
        let line = parseInt(editor.doc.getCursor().line);   //Cursor line
        let getSqlStr = ""; //取得SQL字串

        console.debug(editor.getLine(line).length);
        console.debug(getLineValue(editor,line));

        // 往前找段落
        i = line ;
        while (i >= 0){
            let tmpSQL = "";
            tmpSQL = getLineValue(editor,i);
            console.debug("目前分析到第"+i+"行");
            console.debug(tmpSQL);
            // 檢查空白
            if(tmpSQL===""){
                console.debug("此行內容是空白");
                break;
            }

            let semicolonCh = tmpSQL.indexOf(";");

            // 檢查有分號，但是游標當前的行不算
            if(semicolonCh > -1 && i !== line){

                console.debug("有分號");
                console.debug("切字串起始字元"+semicolonCh+1);
                console.debug("切字串結束字元"+editor.getLine(i).length);

                console.debug(tmpSQL.substring(semicolonCh+1,editor.getLine(i).length));
                tmpSQL = tmpSQL.substring(semicolonCh+1,editor.getLine(i).length);
            }


            getSqlStr = tmpSQL+getSqlStr;
            i--;
        }

        console.log("=====================getSqlStr START=====================");
        console.log(getSqlStr)
        console.log("=====================getSqlStr END  =====================");

        // 後找段落

        // console.log(sqlStr)
        // executeAjax(obj);
    }

    function getLineValue(editor,line){
        let returnVal = "";

        returnVal = editor.getLine(line).substr(0,editor.getLine(line).length);
        return returnVal;
    }

    function changeHintOptions(){
        let sqlContent = editor.getValue();
        try {
            sqlParser.parse(sqlContent);
            shownMessage('errorMessageId',null ,'text-danger');
        }catch (e){
            shownMessage('errorMessageId','語法不正確' ,'text-danger');
            return;
        }
        document.getElementById('sql-text').value = editor.getValue();

        jQuery.ajax({
            url: "${createLink(controller: 'console', action: "getParserSQLAndGetTableColumns")}",
            data: jQuery(document.getElementById("${sqlScriptFrom}")).serialize(),
            dataType: "json",
            success: function (json) {
                editor.setOption("hintOptions", json);
            },
        });
    }

    /**
     * 列出資料庫的schema
     */
    function listDatabaseSchemas(value){
        jQuery.ajax({
            url: "${createLink(controller: 'console', action: "listDatabaseSchemas")}",
            data: {dbConfigId:value},
            dataType: "json",
            success: function (json) {
                if(json.success){
                    jsonToSelect("schema",json.data);
                }

            },
        });
    }

    function getTables(value){
        let dbConfigId = jQuery("#dbConfigId").val();
        jQuery.ajax({
            url: "${createLink(controller: 'console', action: "getTables")}",
            data: {dbConfigId:dbConfigId,schema:value},
            dataType: "json",
            success: function (json) {
                editor.setOption("hintOptions", json);
            },
        });
    }



    jQuery( "#dbConfigId" ).on( "change", function() {listDatabaseSchemas(this.value);} );
    jQuery( "#schema" ).on( "change", function() {getTables(this.value);} );
</script>
</body>
</html>