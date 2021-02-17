<#import "layout/common.ftl" as c>
<#import "/spring.ftl" as spring>
<@c.page>
    <div class="mx-auto w-25 my-5">
        <form method="post" action="/login">
            <div class="mb-3">
                <label for="email" class="form-label"><@spring.message "login.email"/></label>
                <input type="email" class="form-control" id="email" required name="email">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label"><@spring.message "login.password"/></label>
                <input type="password" class="form-control" id="password" required name="password">
            </div>
            <button type="submit" class="btn btn-primary"><@spring.message "header.login"/></button>
            <div class="text-center mt-2">
                <p><@spring.message "login.questiontext"/><a class="text-decoration-none" href="register"><@spring.message "login.registerhref"/></a></p>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</@c.page>