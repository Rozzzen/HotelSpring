<#include "security.ftl">
<nav class="navbar navbar-expand-lg navbar-light bg-light" style="padding-left: 7%; padding-right: 7%">
    <div class="container-fluid">
        <a class="navbar-brand" href="/"><@spring.message "hotel.name"/></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <div class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="" id="dropdown09" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        <#if .locale = "ru"><span class="flag-icon flag-icon-ru"> </span>
                        Русский</a>
                        <#elseif .locale = "uk_UA"><span class="flag-icon flag-icon-ua"> </span>
                            Українська</a>
                        <#else><span class="flag-icon flag-icon-us"></span> English</a>
                        </#if>
                    <div class="dropdown-menu" aria-labelledby="dropdown09">
                        <a class="dropdown-item" href="?language=en"><span class="flag-icon flag-icon-us"></span>
                            English</a>
                        <a class="dropdown-item" href="?language=ru"><span class="flag-icon flag-icon-ru"> </span>
                            Русский</a>
                        <a class="dropdown-item" href="?language=uk_UA"><span class="flag-icon flag-icon-ua"> </span>
                            Українська</a>
                    </div>
                </div>
                <#if role != "ADMIN">
                    <li class="nav-item mx-3">
                        <a class="nav-link active" aria-current="page" href="/booking"><@spring.message "header.booking"/></a>
                    </li>
                </#if>
                <#if name != "unknown">
                    <li class="nav-item dropdown mx-3">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            ${user.name} ${user.surname}</a>
                        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                            <#if role == "USER">
                                <li><a class="dropdown-item" href="/applications"><@spring.message "header.myapplications"/></a></li>
                            </#if>
                            <#if role == "ADMIN">
                                <li><a class="dropdown-item" href="/admin/applications"><@spring.message "header.userapplications"/></a></li>
                                <li><a class="dropdown-item" href="/admin/edit/rooms"><@spring.message "header.editrooms"/></a></li>
                            </#if>
                            <li>
                                <hr class="dropdown-divider"/>
                            </li>
                            <li>
                                <form action="/logout" method="post">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <button class="dropdown-item" type="submit"><@spring.message "header.logout"/></button>
                                </form>
                            </li>
                        </ul>
                    </li>
                </#if>
            </ul>
            <#if name == "unknown">
                <div class="nav-item">
                    <a href="/login" class="btn btn-success" role="button"><@spring.message "header.login"/></a>
                </div>
            </#if>
        </div>
    </div>
</nav>