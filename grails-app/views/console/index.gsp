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
    <meta name="layout" content="main"/>
</head>

<body>
    <form id="${sqlScriptFrom}">

        <div class="accordion" id="accordion">
            <div class="card">
                <div class="card-header" id="headingOne">
                    <h2 class="mb-0">
                        <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse" data-target="#accordion-setting" aria-expanded="true" aria-controls="accordion-setting">
                            資料庫連線設定
                        </button>
                    </h2>
                </div>
            </div>
            <div id="accordion-setting" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
                <div class="card-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-3">
                                <div class="form-group col-form-label">
                                    <label for="db-type">db-type</label>
                                    <g:select name="db-type" class="form-control" from="${tw.com.pollochang.dbpollo.database.DBType}" value="POSTGRESQL" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-3">
                                <div class="form-group col-form-label">
                                    <label for="db-host">db-host</label>
                                    <g:textField name="db-host" class="form-control" value="127.0.0.1" />
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="form-group col-form-label">
                                    <label for="db-port">db-port</label>
                                    <g:textField name="db-port" class="form-control"  value="5432" />
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="form-group col-form-label">
                                    <label for="db-username">db-username</label>
                                    <g:passwordField name="db-username" class="form-control" value="dict_sa" />
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="form-group col-form-label">
                                    <label for="db-password">db-password</label>
                                    <g:passwordField name="db-password" class="form-control"  value="fd831554" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <div class="form-group col-form-label">
                        <label for="sql-text">sql</label>
                        <g:textArea name="sql-text" class="form-control" id="exampleFormControlTextarea1" rows="10" value="select * from bs_user bu " />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <button
                            type="button" class="btn btn-primary"
                            data-dict="ajax"
                            data-action="filter"
                            data-url="${createLink(controller: 'console', action: "sqlScript")}"
                            data-form-id="${sqlScriptFrom}"
                            data-filter-id="${filterId}"
                    >
                        ${message(code: "default.button.execute.label")}
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
<script type="text/javascript">
    document.addEventListener("keydown", function(e) {
        if (e.ctrlKey && e.code === "Enter"){ //KeyC

            let obj = new Map(); //要傳到下一個 function 的物件
            obj.url = "${createLink(controller: 'console', action: "sqlScript")}";
            obj.filterId = "${filterId}";
            obj.data = jQuery(document.getElementById("${sqlScriptFrom}")).serialize();
            obj.action = "filter";
            executeAjax(obj);
            e.preventDefault();
        }
    });
</script>
</body>
</html>