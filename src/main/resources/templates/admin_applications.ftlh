<#import "layout/common.ftl" as c>
<#include "layout/security.ftl">
<#import "layout/pagination.ftlh" as p>
<#import "/spring.ftl" as spring>
<@c.page>
    <@p.pagination sort url page>
        <table class="table table-hover table-responsive mt-5">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><@spring.message "applications.user"/></th>
                <th scope="col"><@spring.message "register.phonenumber"/></th>
                <th scope="col"><@spring.message "application.roomtype"/></th>
                <th scope="col"><@spring.message "application.capacity"/></th>
                <th scope="col"><@spring.message "room.number"/></th>
                <th scope="col"><@spring.message "rooms.checkin"/></th>
                <th scope="col"><@spring.message "rooms.checkout"/></th>
                <th scope="col"><@spring.message "application.booked"/></th>
                <th scope="col"><@spring.message "application.paid"/></th>
                <th scope="col"><@spring.message "application.actions"/></th>
            </tr>
            </thead>
            <tbody>
            <#list page.content as application>
                <tr>
                    <td class="align-middle">${application.id}</td>
                    <td class="align-middle">${application.user.name} ${application.user.surname}</td>
                    <td class="align-middle">${application.user.phone}</td>
                    <td class="align-middle">${application.roomtype}</td>
                    <td class="align-middle">${application.capacity}</td>
                    <td class="align-middle">
                        <#if application.room??>${application.room.id}
                        <#else><@spring.message "application.notassigned"/>
                        </#if>
                    </td>
                    <td class="align-middle">${application.checkin}</td>
                    <td class="align-middle">${application.checkout}</td>
                    <td class="align-middle">
                        <#if application.booked == false><@spring.message "no"/>
                        <#else><@spring.message "yes"/>
                        </#if>
                    </td>
                    <td class="align-middle">
                        <#if application.paid == false><@spring.message "no"/>
                        <#else><@spring.message "yes"/>
                        </#if>
                    </td>
                    <td class="align-middle">
                        <#if !application.room??>
                            <form method="GET" action="/admin/applications/rooms" class="my-1">
                                <input type="hidden" name="checkin" value="${application.checkin}"/>
                                <input type="hidden" name="checkout" value="${application.checkout}"/>
                                <input type="hidden" name="id" value="${application.id}"/>
                                <button type="submit" class="btn btn-primary"><@spring.message "application.selectroom"/></button>
                            </form>
                        </#if>
                        <form method="POST" action="/admin/applications/delete" class="my-1">
                            <input type="hidden" name="applicationId" value="${application.id}"/>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-warning"><@spring.message "remove"/></button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </@p.pagination>
</@c.page>