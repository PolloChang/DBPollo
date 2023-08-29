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
                <div class="col-12">
                    <div class="form-group col-form-label">
                        <label for="dbConfigId">資料庫</label>
                        <g:select name="dbConfigId" class="form-control"
                                  from="${tw.com.pollochang.dbpollo.database.DbConfig.list()}"
                                  noSelection="['':'請選擇']"
                                  optionKey="id" optionValue="name"
                        />
                    </div>
                    <script>

                        function getSchemaTables(){
                            jQuery.ajax({
                                url: "${createLink(controller: 'console', action: "listDatabaseSchemas")}",
                                data: {dbConfigId:jQuery('#dbConfigId').val()},
                                dataType: "json",
                                success: function (json) {
                                    console.log(json);
                                },
                            });
                        }

                        // window.onload = function() {
                        //
                        //
                        // }
                        jQuery( "#dbConfigId" ).on( "change", function() {
                            getSchemaTables();
                        } );
                    </script>
                </div>
                <div class="col-12">
                    <div class="form-group col-form-label">
                        <label for="schema">schema</label>
                        <select id="schema"><option></option></select>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <div class="form-group col-form-label">
                        <label for="sql-text">sql</label>
                        <g:textArea name="sql-text" class="form-control" id="sql-text" rows="10" value="select * from bs_user bu " />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <button
                            type="button" class="btn btn-primary"
                            onclick="executeSql()"
                    >
                        ${message(code: "default.button.execute.label")}
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
        executeAjax(obj);
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


</script>
</body>
</html>