<#import "layout/common.ftl" as c>
<#include "layout/security.ftl">
<#import "/spring.ftl" as spring>
<@c.page>
    <#if alert?has_content><div class="alert alert-danger" role="alert">${alert}</div></#if>
    <#if role != "ADMIN">
        <form method="GET" action="/rooms" style = "padding-left: 7%">
            <div class="row mt-4 mb-2">
                <div class="col-sm-5">
                    <label for="check_in" class="form-label"><@spring.message "rooms.checkin"/></label>
                    <input class="form-control" id="check_in"
                           value="" type="date" name="checkin"
                           onchange="check_out.min = this.value" required>
                </div>
                <div class="col-sm-5">
                    <label for="check_out" class="form-label"><@spring.message "rooms.checkout"/></label>
                    <input class="form-control" id="check_out"
                           value="" type="date" name="checkout" required>
                </div>
                <div class="col-sm-2 mt-auto">
                    <button type="submit" class="btn btn-success"><@spring.message "rooms.search"/></button>
                </div>
            </div>
        </form>
    </#if>
    <div class="p-5 text-center mt-3 mb-3">
        <h1 class="mb-3"><@spring.message "hotel.name"/></h1>
        <p><@spring.message "project.desctiption1"/></p>
        <p><@spring.message "project.description2"/></p>
        <p><@spring.message "project.description3"/></p>
        <p><@spring.message "project.description4"/></p>
        <p><@spring.message "project.description5"/></p>
        <p><@spring.message "project.description6"/></p>
        <p><@spring.message "project.description7"/></p>
    </div>
</@c.page>