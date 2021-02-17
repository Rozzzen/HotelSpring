<#import "layout/common.ftl" as c>
<#include "layout/security.ftl">
<#import "layout/pagination.ftlh" as p>
<#import "/spring.ftl" as spring>
<@c.page>
    <@p.pagination sort url page>
        <div>
        <table class="table table-hover table-responsive mt-5">
            <thead>
            <tr>
                <th scope="col"><@spring.message "applications.image"/></th>
                <th scope="col"><@spring.message "application.roomtype"/></th>
                <th scope="col"><@spring.message "application.capacity"/></th>
                <th scope="col"><@spring.message "application.roomnumber"/></th>
                <th scope="col"><@spring.message "application.price"/></th>
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
                    <td class="align-middle">
                        <#if application.room??>
                            <img src="img/${application.room.imgName}" style="width:250px; height:150px">
                        <#else><img src="img/empty.jpg" style="width:250px; height:150px">
                        </#if>
                    </td>
                    <td class="align-middle">${application.roomtype}</td>
                    <td class="align-middle">${application.capacity}</td>
                    <td class="align-middle">
                        <#if application.room??>${application.room.id}
                        <#else><@spring.message "application.notassigned"/>
                        </#if>
                    </td>
                    <td class="align-middle">
                        <#if application.room??>${application.room.getTotalPrice(application.checkin, application.checkout)} UAH
                        <#else><@spring.message "application.notassigned"/>
                        </#if>
                    </td>
                    <td class="align-middle">${application.checkin}</td>
                    <td class="align-middle">${application.checkout}</td>
                    <td class="align-middle">
                        <#if application.booked == false><@spring.message "no"/>
                        <#else> <@spring.message "yes"/>
                        </#if>
                    </td>
                    <td class="align-middle">
                        <#if application.paid == false><@spring.message "no"/>
                        <#else> <@spring.message "yes"/>
                        </#if>
                    </td>
                    <td class="align-middle">
                        <#if !application.room?has_content> Waiting for admin to pick you a room
                        <#elseif application.booked == false && application.paid == false>
                            <form method="POST" action="/applications/reject" class="my-1">
                                <input type="hidden" name="applicationId" value="${application.id}"/>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn btn-warning"><@spring.message "application.requestchange"/></button>
                            </form>
                            <form method="POST" action="/applications/confirm" class="my-1">
                                <input type="hidden" name="applicationId" value="${application.id}"/>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn btn-success"><@spring.message "application.confirm"/></button>
                            </form>
                        <#elseif application.booked == true && application.paid = false>
                            <form method="POST" action="/applications/payment" class="my-1">
                                <input type="hidden" name="applicationId" value="${application.id}"/>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn btn-primary"><@spring.message "application.paymentconfirm"/></button>
                            </form>
                        <#else> <@spring.message "application.successbook"/>
                        </#if>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </@p.pagination>
</@c.page>