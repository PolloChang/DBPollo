<%--
  Created by IntelliJ IDEA.
  User: jameschang
  Date: 9/1/21
  Time: 11:31 PM
--%>
<g:set var="filterId" value="${UUID.randomUUID().toString()}"/>
<g:set var="searchFormId" value="${UUID.randomUUID().toString()}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="zh-Hant-TW">
<head>
    <meta name="layout" content="appMain"/>
    <script type="text/javascript">
        function filterSearch(){
            jQuery('#${filterId}').bootstrapTable('refresh', {
                url: '${createLink(controller: 'dbManage' ,action: 'filter')}/?' + jQuery('#${searchFormId}').serialize()
            });
        }
    </script>
</head>

<body>
<div class="container-fluid">
    <form id="${searchFormId}">
        <div class="row">
            <div class="col">
                <app:searchTable>
                    <tbody>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="4">
                            <div class="btn-group" role="group">
                                <dict:button
                                        onclick="filterSearch();"
                                >
                                    ${message(code: "default.button.search.label")}
                                </dict:button>

                                <dict:button
                                        class="btn-outline-secondary"
                                >
                                    ${message(code: "default.button.reset.label")}
                                </dict:button>

                                <dict:button
                                        class="btn-light btn-outline-dark"
                                >
                                    ${message(code: "default.button.print.label")}
                                </dict:button>
                            </div>

                            <div class="btn-group" role="group">
                                <dict:button
                                        class="btn-success"
                                        data-dict="openTab"
                                        data-tab-id="0"
                                        data-tab-title="新增"
                                        data-url="${createLink(controller: "dbManage",action: "creatPage")}"
                                >
                                    ${message(code: "default.button.creat.label")}
                                </dict:button>
                            </div>
                        </td>
                    </tr>
                    </tfoot>
                </app:searchTable>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <asset:image src="/icons/border-width.svg"/>
                查詢結果
            </div>
        </div>

        <div class="row">
            <div class="col">
                <g:render template="/tw/com/pollochang/dbpollo/manage/dbManage/filter" model="[filterID:filterId]" />
            </div>
        </div>
    </form>
</div>
</body>
</html>