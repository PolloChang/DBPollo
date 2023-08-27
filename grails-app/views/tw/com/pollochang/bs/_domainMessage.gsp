<table class="table table-bordered">
    <colgroup>
        <col width="15%">
        <col width="35%">
        <col width="15%">
        <col width="35%">
    </colgroup>
    <tbody>
    <tr>
        <th>
            ${message(code: "instance.manCreated.label")}
        </th>
        <td>
            ${instance?.manCreated}
        </td>
        <th>
            ${message(code: "instance.dateCreated.label")}
        </th>
        <td>
            <g:formatDate date="${instance?.dateCreated}" dateStyle="yyyy-MM-dd HH:mm:ss" />
        </td>
    </tr>
    <tr>
        <th>
            ${message(code: "instance.manLastUpdated.label")}
        </th>
        <td>
            ${instance?.manLastUpdated}
        </td>
        <th>
            ${message(code: "instance.lastUpdated.label")}
        </th>
        <td>
            <g:formatDate date="${instance?.lastUpdated}" dateStyle="yyyy-MM-dd HH:mm:ss" />
        </td>
    </tr>
</table>