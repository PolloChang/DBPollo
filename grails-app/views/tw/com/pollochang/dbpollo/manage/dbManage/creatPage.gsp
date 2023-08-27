<%--
  新增頁面
  Created by IntelliJ IDEA.
  User: jameschang
  Date: 9/5/21
  Time: 11:16 PM
--%>
<g:set var="formId" value="${UUID.randomUUID().toString()}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="appMain"/>
</head>

<body>
<div class="container-fluid">
    <div class="row">
        <div class="col">
            <form id="${formId}" enctype="multipart/form-data">
                <g:render template="/tw/com/pollochang/domain/dbConfig/form" model="[dbConfigI:dbConfigI]" />
            </form>
        </div>
    </div>

    <div class="row">
        <div class="col">

            <div class="btn-group" role="group">
                <dict:button
                        data-dict="ajax"
                        data-form-id="${formId}"
                        data-url="${createLink(controller: "dbManage",action: "insert")}"
                >
                    ${message(code: "default.button.creat.label")}
                </dict:button>
            </div>

        </div>
    </div>
</div>
</body>
</html>