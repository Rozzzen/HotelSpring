<#import "layout/common.ftl" as c>
<#import "/spring.ftl" as spring>
<@c.page>
    <div class="p-5 text-center bg-light">
        <h1 class="mb-3"><@spring.message "403"/></h1>
        <a class="btn btn-primary" href="/" role="button"><@spring.message "404.href"/></a>
    </div>
</@c.page>