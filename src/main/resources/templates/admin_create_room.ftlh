<#import "layout/common.ftl" as c>
<#include "layout/security.ftl">
<#import "/spring.ftl" as spring>
<@c.page>
    <div class="mx-auto w-25 my-5">
        <form method="post" action="/admin/create/rooms">
            <div class="mb-4">
                <label for="roomNum"><@spring.message "room.number"/></label>
                <input pattern="^[0-9]{3,4}$" type="text" class="form-control" id="roomNum" placeholder="" required name="id">
            </div>
            <div class="mb-3">
                <label for="price" class="form-label"><@spring.message "room.price"/></label>
                <input pattern="^[0-9]{3,4}$" class="form-control" id="price" type="number" required name="price">
            </div>
            <div class="mb-4">
                <label for="capacity" class="form-label"><@spring.message "application.capacity"/></label>
                <select class="form-control" id="capacity" required name="capacity">
                    <option value="" selected disabled></option>
                    <#list [1, 2, 3, 4] as capacity>
                        <option value="${capacity}">${capacity}</option>
                    </#list>
                </select>
            </div>
            <div class="mb-4">
                <label for="roomtype" class="form-label"><@spring.message "room.class"/></label>
                <select class="form-control" id="roomtype" required name="roomtype">
                    <option value="" selected disabled></option>
                    <#list roomtypes as roomtype>
                        <option value="${roomtype}">${roomtype}</option>
                    </#list>
                </select>
            </div>
            <div class="mb-3">
                <label for="img" class="form-label"><@spring.message "admin.imgfilename"/></label>
                <input class="form-control" id="img" type="text" required name="imgName">
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary mb-3"><@spring.message "create.room"/></button>
        </form>
    </div>
</@c.page>