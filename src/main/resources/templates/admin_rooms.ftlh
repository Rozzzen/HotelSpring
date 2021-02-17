<#import "layout/common.ftl" as c>
<#include "layout/security.ftl">
<#import "layout/pagination.ftlh" as p>
<#import "/spring.ftl" as spring>
<@c.page>
    <#if alert?has_content><div class="alert alert-danger" role="alert">${alert}</div></#if>
    <div class="mt-4" style="padding-left: 7%">
        <a href="/admin/create/rooms" class="btn btn-primary" type="button">
            <@spring.message "create.room"/>
        </a>
    </div>
    <@p.pagination sort url page>
        <div class="row mx-4 my-4">
            <#list page.content as room>
                <div class="col-sm-4 my-3">
                    <div class="card">
                        <div class="card-header"><@spring.message "room.number"/>: ${room.id}</div>
                        <div class="card-body">
                            <form action="/admin/edit/rooms" method="POST" class = "my-1">
                                <img src="/img/${room.imgName}" class="card-img-top"
                                     alt="${room.imgName}">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <div class="form-group row">
                                            <label for="img" class="col-sm-6 col-form-label"><@spring.message "admin.imgfilename"/> </label>
                                            <div class="col-sm-6">
                                                <input class="form-control" id="img" type="text"
                                                       name="imgName"
                                                       value="${room.imgName}">
                                            </div>
                                        </div>
                                    </li>
                                    <li class="list-group-item">
                                        <div class="form-group row">
                                            <label for="price" class="col-sm-6 col-form-label"><@spring.message "room.price"/>: </label>
                                            <div class="col-sm-6">
                                                <input class="form-control" id="price" type="number"
                                                       name="price"
                                                       value="${room.price}">
                                            </div>
                                        </div>
                                    </li>
                                    <li class="list-group-item">
                                        <div class="form-group row">
                                            <label for="capacity" class="col-sm-6 col-form-label"><@spring.message "application.capacity"/>: </label>
                                            <div class="col-sm-6">
                                                <input class="form-control" id="capacity" type="number" min="0" max="4"
                                                       name="capacity"
                                                       value="${room.capacity}">
                                            </div>
                                        </div>
                                    </li>
                                    <select class="form-select form-select mb-3" name="roomtype">
                                        <option selected value="${room.roomtype}">${room.roomtype}</option>
                                        <#list room.roomtype.values() as roomtype>
                                            <#if room.roomtype != roomtype>
                                                <option value="${roomtype}">${roomtype}</option>
                                            </#if>
                                        </#list>
                                    </select>
                                </ul>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type=hidden name="id" value="${room.id}">
                                <div class="col text-center">
                                    <button type="submit" class="btn btn-success mt-3 px-4"><@spring.message "edit"/></button>
                                </div>
                            </form>
                            <form action="/admin/edit/rooms/delete" method="POST" class = "my-1">
                                <div class="col text-center">
                                    <button type="submit" class="btn btn-danger"><@spring.message "remove"/></button>
                                </div>
                                <input type=hidden name="roomId" value="${room.id}">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </@p.pagination>
</@c.page>