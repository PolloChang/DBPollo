<nav class="col-12 col-md-3 col-xl-2 bd-sidebar">
    <div class="accordion" id="app-accordion">
        <g:each in="${groupL}" var="groupI" status="i">
            <g:set var="appHeadingId" value="app-heading-${i}" />
            <g:set var="ariaControlsId" value="app-collapse-${i}" />
            <div class="card">
                <div class="card-header" id="${appHeadingId}">
                    <h5 class="mb-0">
                        <button class="btn btn-link" type="button" data-toggle="collapse"
                                data-target="#${ariaControlsId}" aria-controls="${ariaControlsId}" aria-expanded="true" >
                            ${groupI?.app_group_name}
                        </button>
                    </h5>
                </div>

                <div id="${ariaControlsId}" class="collapse list-group ${(i==0)?'show':''}" aria-labelledby="${appHeadingId}" data-parent="#app-accordion">
                    <%
                        def appLC = appL.findAll{it.app_group_no == groupI.app_group_no}.sort{it.idx}
                    %>
                    <g:each in="${appLC}" var="appI">
                        <button type="button" class="list-group-item list-group-item-action"
                                data-dic="openApp"
                                data-dic-app-no="${appI.app_no}"
                                data-dic-app-name="${appI.app_name}"
                                data-dic-app-url="${createLink(controller: appI.controller,action: appI.action)}"
                        >
                            ${appI.app_no}-${appI.app_name}
                        </button>
                    </g:each>
                </div>
            </div>
        </g:each>
    </div>
</nav>