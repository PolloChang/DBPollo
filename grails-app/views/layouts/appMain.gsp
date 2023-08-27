<%--
  Created by IntelliJ IDEA.
  User: jameschang
  Date: 9/5/21
  Time: 8:56 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <asset:link rel="icon" href="icon.svg" type="image/x-ico"/>
    <asset:stylesheet src="applicationMain.css"/>
    <asset:javascript src="applicationMain.js"/>
    <g:layoutHead/>

</head>

<body>
<nav aria-label="breadcrumb">
    <ol class="breadcrumb bg-secondary">
        <li class="breadcrumb-item active text-white" aria-current="page">${message(code: "${controllerName}.${actionName}.label")}[${controllerName}/${actionName}]</li>
    </ol>
</nav>
<g:hiddenField name="appControllerName" value="${controllerName.toUpperCase()}"/>
<g:layoutBody/>
</body>

</html>