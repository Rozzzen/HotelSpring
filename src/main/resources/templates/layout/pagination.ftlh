<#import "/spring.ftl" as spring>
<#macro pagination sort url page>
    <#assign currentPage = page.getNumber()>
    <#assign sortText = "&sort=">
    <#if url?contains("rooms") && !url?contains("edit")>
        <#if url?contains("applications")> <#assign dates = "checkin=${checkin}&checkout=${checkout}&id=${id}&">
        <#elseif url == "/rooms"> <#assign dates = "checkin=${checkin}&checkout=${checkout}&">
        </#if>
        <#assign
        head = (totalElements/page.getSize())?floor
        body = 0..totalElements/page.getSize()>
    <#else>
        <#assign dates = "">
        <#if page.getTotalPages() == 0>
            <#assign
            head = page.getTotalPages()
            body = 0..page.getTotalPages()>
        <#else>
            <#assign
            head = page.getTotalPages() - 1
            body = 0..page.getTotalPages() - 1>
        </#if>
    </#if>
    <div class="dropdown my-4" style="padding-left: 7%">
        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                data-bs-toggle="dropdown" aria-expanded="false">
            <@spring.message "sort.sort"/>
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <#if url?contains("rooms")>
                <li><a class="dropdown-item"
                       href="${url}?${dates}page=0${sortText}priceDesc"><@spring.message "sort.pricehl"/></a></li>
                <li><a class="dropdown-item"
                       href="${url}?${dates}page=0${sortText}price"><@spring.message "sort.pricelh"/></a></li>
                <li><a class="dropdown-item"
                       href="${url}?${dates}page=0${sortText}capacityDesc"><@spring.message "sort.capacityhl"/></a></li>
                <li><a class="dropdown-item"
                       href="${url}?${dates}page=0${sortText}capacity"><@spring.message "sort.capacitylh"/></a></li>
                <li><a class="dropdown-item"
                       href="${url}?${dates}page=0${sortText}roomtype"><@spring.message "sort.class"/></a>
                </li>
            <#else>
                <li><a class="dropdown-item"
                       href="${url}?${dates}page=0${sortText}checkin"><@spring.message "sort.olderapplications"/></a></li>
                <li><a class="dropdown-item"
                       href="${url}?${dates}page=0${sortText}checkinDesc"><@spring.message "sort.newapplications"/></a></li>
            </#if>
            <#if sort != "id">
                <li><a class="dropdown-item" href="${url}?${dates}page=0${sortText}id">
                        <@spring.message "sort.default"/>
                    </a></li>
            </#if>
        </ul>
    </div>
    <#nested>
    <nav aria-label="Pagination">
        <ul class="pagination justify-content-center">
            <#if currentPage == 0>
                <li class="page-item disabled"><a class="page-link"><@spring.message "pagination.previous"/></a></li>
            <#else>
                <li class="page-item"><a class="page-link"
                                         href="${url}?${dates}page=${currentPage - 1}${sortText}${sort}">
                        <@spring.message "pagination.previous"/></a></li>
            </#if>
            <#list body as i>
                <#if i == currentPage>
                    <li class="page-item disabled"><a class="page-link">${i + 1}</a></li>
                <#else>
                    <li class="page-item"><a class="page-link"
                                             href="${url}?${dates}page=${i}${sortText}${sort}">${i + 1}</a>
                    </li>
                </#if>
            </#list>
            <#if currentPage == head>
                <li class="page-item disabled"><a class="page-link"><@spring.message "pagination.next"/></a></li>
            <#else>
                <li class="page-item"><a class="page-link"
                                         href="${url}?${dates}page=${currentPage + 1}${sortText}${sort}">
                        <@spring.message "pagination.next"/></a></li>
            </#if>
        </ul>
    </nav>
</#macro>