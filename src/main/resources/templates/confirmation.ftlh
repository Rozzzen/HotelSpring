<#import "layout/common.ftl" as c>
<#include "layout/security.ftl">
<#import "/spring.ftl" as spring>
<@c.page>
    <div class="row mt-5 mx-5">
        <div class="card col-md-4 order-md-2 px-0" style="border: none; border-left: 1px solid #e5e5e5;">
            <img class="card-img-top" src="/img/${room.imgName}" alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title my-2"><@spring.message "room.number"/> #${room.id}</h5>
                <p class="card-text my-1">
                    <@spring.message "confirmation.totalprice"/> : ${room.price * days} UAH
                </p>
                <p class="card-text my-1">
                    <@spring.message "sort.class"/>: ${room.roomtype}</p>
                <p class="card-text my-1">
                    <@spring.message "confirmation.bookedfrom"/> ${checkin}
                    <@spring.message "to"/> ${checkout}</p>
            </div>
        </div>
        <div class="col-md-8 order-md-1">
            <form method="post" action="${url}/confirm">
                <h4 class="mb-3">
                    <@spring.message "confirmation.creditcardinfo"/>
                </h4>
                <div class="mb-3">
                    <label for="address">
                        <@spring.message "confirmaion.adress"/>
                    </label>
                    <input type="text" class="form-control" id="address" placeholder="" required="">
                </div>
                <div class="mb-3">
                    <label for="address2">
                        <@spring.message "confirmation.address2"/>
                        <span class="text-muted">(<@spring.message "confirmation.optional"/>)</span></label>
                    <input type="text" class="form-control" id="address2" placeholder="">
                </div>
                <div class="d-block my-3">
                    <div class="custom-control custom-radio">
                        <input id="credit" name="paymentMethod" type="radio" class="custom-control-input" checked=""
                               required="">
                        <label class="custom-control-label" for="credit">
                            <@spring.message "confirmation.creditcard"/>
                        </label>
                    </div>
                    <div class="custom-control custom-radio">
                        <input id="debit" name="paymentMethod" type="radio" class="custom-control-input" required="">
                        <label class="custom-control-label" for="debit">
                            <@spring.message "confirmation.debitcard"/>
                        </label>
                    </div>
                    <div class="custom-control custom-radio">
                        <input id="paypal" name="paymentMethod" type="radio" class="custom-control-input" required="">
                        <label class="custom-control-label" for="paypal">
                            <@spring.message "confirmation.paypal"/>
                        </label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="cc-name">
                            <@spring.message "confirmation.nameoncard"/>
                        </label>
                        <input type="text" class="form-control" id="cc-name" placeholder="" required="">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="cc-number">
                            <@spring.message "confirmation.creditcardnumber"/>
                        </label>
                        <input minlength="13" maxlength="19" pattern="[0-9]*" type="text" class="form-control"
                               id="cc-number" placeholder="" required="">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 mb-3">
                        <label for="cc-expiration">
                            <@spring.message "confirmation.expiration"/>
                        </label>
                        <input pattern="^(0[1-9]|1[0-2])\/?([0-9]{4}|[0-9]{2})$" type="text" class="form-control"
                               id="cc-expiration" placeholder="" required="">
                    </div>
                    <div class="col-md-3 mb-3">
                        <label for="cc-cvv">
                            <@spring.message "confirmation.cvv"/>
                        </label>
                        <input pattern="^[0-9]{3,4}$" type="text" class="form-control" id="cc-cvv" placeholder=""
                               required="">
                    </div>
                </div>
                <hr class="mb-4">
                <#if url?contains("rooms")>
                    <input type="hidden" name="roomId" value="${room.id}">
                    <input type="hidden" name="checkin" value="${checkin}">
                    <input type="hidden" name="checkout" value="${checkout}">
                <#elseif url?contains("applications")>
                    <input type="hidden" name="applicationId" value="${applicationId}">
                </#if>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-primary btn-lg btn-block" type="submit">
                    <@spring.message "application.confirm"/>
                </button>
            </form>
        </div>
    </div>
</@c.page>