<#import "layout/common.ftl" as c>
<#include "layout/security.ftl">
<#import "layout/pagination.ftlh" as p>
<#import "/spring.ftl" as spring>
<@c.page>
    <#if role != "ADMIN">
        <form method="GET" action="rooms" style="padding-left: 7%">
            <div class="row mt-4 mb-2">
                <div class="col-sm-5">
                    <label for="check_in" class="form-label"><@spring.message "rooms.checkin"/></label>
                    <input class="form-control" id="check_in"
                           value="" type="date" name="checkin"
                           onchange="check_out.min = this.value" required="required"/>
                </div>
                <div class="col-sm-5">
                    <label for="check_out" class="form-label"><@spring.message "rooms.checkout"/></label>
                    <input class="form-control" id="check_out"
                           value="" type="date" name="checkout" required="required"/>
                </div>
                <div class="col-sm-2 mt-auto">
                    <button type="submit" class="btn btn-success"><@spring.message "rooms.search"/></button>
                </div>
            </div>
        </form>
    </#if>
    <@p.pagination sort url page>
        <div class="row mx-4 my-4" id = "card-container">
            <#list page.content as room>
                <div class="col-sm-4 my-3">
                    <div class="card">
                        <div class="card-header"><@spring.message "room.number"/>: ${room.id}</div>
                        <div class="card-body">
                            <img src="/img/${room.imgName}" class="card-img-top"
                                 alt="room"/>
                            <ul class="list-group">
                                <li class="list-group-item"><@spring.message "application.price"/>: ${room.price}UAH
                                </li>
                                <li class="list-group-item"><@spring.message "application.capacity"/>
                                    : ${room.capacity}</li>
                                <li class="list-group-item"><@spring.message "room.class"/>: ${room.roomtype}</li>
                            </ul>
                            <div class="col text-center">
                                <#if room.status == "FREE">
                                    <#if role == "ADMIN">
                                        <form method="POST" action="/admin/applications/rooms">
                                            <input type="hidden" name="roomId" value="${room.id}"/>
                                            <input type="hidden" name="applicationId" value="${id}"/>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <button type="submit"
                                                    class="btn btn-primary mt-3 px-4"><@spring.message "application.selectroom"/></button>
                                        </form>
                                    <#else>
                                        <form method="POST" action="/rooms/payment">
                                            <input type="hidden" name="roomId" value="${room.id}"/>
                                            <input type="hidden" name="checkin" value="${checkin}"/>
                                            <input type="hidden" name="checkout" value="${checkout}"/>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <button type="submit"
                                                    class="btn btn-success mt-3 px-4"><@spring.message "room.book"/></button>
                                        </form>
                                    </#if>
                                <#elseif room.status == "OCCUPIED">
                                    <a class="btn btn-warning disabled mt-3 px-4"><@spring.message "room.occupied"/></a>
                                <#elseif room.status == "BOOKED">
                                    <a class="btn btn-primary disabled mt-3 px-4"><@spring.message "application.booked"/></a>
                                <#elseif room.status == "REPAIRMENT">
                                    <a class="btn btn-warning disabled mt-3 px-4"><@spring.message "room.repairment"/></a>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </@p.pagination>
</@c.page>