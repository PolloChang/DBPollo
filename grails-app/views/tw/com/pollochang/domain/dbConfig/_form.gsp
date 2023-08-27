<g:hiddenField name="dbConfig.version" value="${dbConfigI.version}" />
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
            <asset:image src="/icons/border-width.svg"/>
            資料庫連線資訊
        </th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <th>
            ${message(code: "dbConfig.name.label")}
        </th>
        <td>
            <dict:textField name="dbConfig.name" value="${dbConfigI.name}" />
        </td>
        <th>
            ${message(code: "dbConfig.type.label")}
        </th>
        <td>
            <g:select name="dbConfig.type" class="form-control" from="${tw.com.pollochang.dbpollo.database.DBType}" value="${dbConfigI.type}" />
        </td>
    </tr>
    <tr>
        <th>
            ${message(code: "dbConfig.host.label")}
        </th>
        <td>
            <dict:textField name="dbConfig.host" value="${dbConfigI.host}" />
        </td>
        <th>
            ${message(code: "dbConfig.port.label")}
        </th>
        <td>
            <dict:numberField name="dbConfig.port" value="${dbConfigI.port}" />
        </td>
    </tr>
    <tr>
        <th>
            ${message(code: "dbConfig.dbname.label")}
        </th>
        <td colspan="3">
            <dict:textField name="dbConfig.dbname" value="${dbConfigI.dbname}" />
        </td>
    </tr>
    <tr>
        <th>
            ${message(code: "dbConfig.username.label")}
        </th>
        <td>
            <dict:textField name="dbConfig.username" value="${dbConfigI.username}" autocomplete="off" />
        </td>
        <th>
            ${message(code: "dbConfig.password.label")}
        </th>
        <td>
            <g:passwordField class="form-control" name="dbConfig.password" value="${com.pollochang.util.AES.decrypt(dbConfigI.password,dbConfigI.passwordSalt)}" autocomplete="off" />
        </td>
    </tr>
    </tbody>
</table>