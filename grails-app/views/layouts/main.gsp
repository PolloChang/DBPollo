<!doctype html>
<html lang="zh-Hant-TW" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
    <g:layoutTitle default="${message(code: "default.layoutTitle.label")}"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:link rel="icon" href="icon.svg" type="image/x-ico"/>

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>

<body>

<header class="navbar navbar-expand-lg navbar-dark navbar-static-top" role="navigation">
    <a class="navbar-brand" href="/#"><asset:image src="icon.svg" alt="dict Logo"/></a>

    <div class="collapse navbar-collapse" aria-expanded="false" style="height: 0.8px;" id="navbarContent">
        <ul class="nav navbar-nav ml-auto">
            <g:pageProperty name="page.nav"/>
        </ul>
    </div>
    <ul class="float-right">
        <sec:ifLoggedIn>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                    <sec:loggedInUserInfo field='username'/>
                </a>
                <div class="dropdown-menu navbar-dark">
                    <g:form controller="logout">
                        <g:submitButton class="dropdown-item navbar-dark color-light" name="Submit" value="Logout" style="color:gray" />
                    </g:form>
                </div>
            </li>
        </sec:ifLoggedIn>
    </ul>

</header>

<g:layoutBody/>

<footer class="footer row" role="contentinfo">
    <div class="col">

    </div>
</footer>

<asset:javascript src="application.js"/>
</body>
</html>
