<#import "layout/common.ftl" as c>
<#include "layout/security.ftl">
<#import "/spring.ftl" as spring>
<@c.page>
    <#if alert?has_content><div class="alert alert-danger" role="alert">${alert}</div></#if>
    <div class="mx-auto w-25 my-5">
        <form method="post" action="/booking">
            <div class="mb-4">
                <select class="form-control" id="roomtype" required name="roomtype">
                    <option value="" selected disabled><@spring.message "booking.roomtype"/></option>
                    <#list roomtypes as roomtype>
                        <option value="${roomtype}">${roomtype}</option>
                    </#list>
                </select>
            </div>
            <div class="mb-4">
                <select class="form-control" id="capacity" required name="capacity">
                    <option value="" selected disabled><@spring.message "application.capacity"/></option>
                    <#list [1, 2, 3, 4] as capacity>
                        <option value="${capacity}">${capacity}</option>
                    </#list>
                </select>
            </div>
            <div class="row mb-4">
                <div class="col-sm-6">
                    <label for="check_in" class="form-label"><@spring.message "rooms.checkin"/></label>
                    <input class="form-control" id="check_in"
                           value="" type="date" name="checkin"
                           onchange="check_out.min = this.value" required>
                </div>
                <div class="col-sm-6">
                    <label for="check_out" class="form-label"><@spring.message "rooms.checkout"/></label>
                    <input class="form-control" id="check_out"
                           value="" type="date" name="checkout" required>
                </div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary mb-3"><@spring.message "booking.submitapplication"/></button>
        </form>
    </div>
</@c.page>