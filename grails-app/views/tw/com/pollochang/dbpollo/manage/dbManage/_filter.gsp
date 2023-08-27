<app:filterTable
        id="${filterID}"
>
    <tr>
        <th data-formatter="indexFormatter" data-align="center" data-width="50">#</th>
        <th data-field="id" data-formatter="linkFormatter" data-width="50">${message(code: "bsRequestMap.appNo.label")}</th>
        <th data-field="name">${message(code: "dbConfig.name.label")}</th>
        <th data-field="type">${message(code: "dbConfig.type.label")}</th>
        <th data-field="host">${message(code: "dbConfig.host.label")}</th>
        <th data-field="port">${message(code: "dbConfig.port.label")}</th>
        <th data-field="dbname">${message(code: "dbConfig.dbname.label")}</th>
        <th data-field="username">${message(code: "dbConfig.username.label")}</th>
    </tr>
</app:filterTable>
<script type="text/javascript">


    /**
     * 顯示編輯按鈕
     * @param value
     * @param row
     * @returns {string}
     */
    function linkFormatter(value, row) {
        return '<a ' +
            'class="btn btn-link"'+
            'data-dict="openTab"'+
            'data-tab-id="'+row.id+'"'+
            'data-tab-title="'+row.name+'"'+
            'data-url="${createLink(controller: "dbManage",action: "editPage")}/' + row.id+'"'+
            '>' +
            row.name +
            '</a>';
    }
</script>