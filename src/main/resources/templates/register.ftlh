<#import "layout/common.ftl" as c>
<#import "/spring.ftl" as spring>
<@c.page>
    <#if alert?has_content><div class="alert alert-danger" role="alert">${alert}</div></#if>
    <div class="mx-auto w-25 my-5">
        <form method="post" action="register"
              oninput='password2.setCustomValidity(password2.value !== password.value ? "Passwords do not match." : "")'>
            <div class="row mb-3">
                <div class="form-group col-md-6">
                    <label for="name" class="form-label"><@spring.message "register.firstname"/></label>
                    <input pattern="/^[a-zA-Z]+$/" type="text" class="form-control" id="name" required name="name">
                </div>
                <div class="form-group col-md-6">
                    <label for="surname" class="form-label"><@spring.message "register.lastname"/></label>
                    <input pattern="/^[a-zA-Z]+$/" type="text" class="form-control" id="surname" required name="surname">
                </div>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label"><@spring.message "login.email"/></label>
                <input type="email" class="form-control" id="email" aria-describedby="emailHelp" required name="email">
                <div id="emailHelp" class="form-text"><@spring.message "register.emailinfo"/></div>
            </div>
            <div class="mb-3">
                <div class="mt-2">
                    <p><@spring.message "register.gender"/></p>
                </div>
                <#list ['Male', 'Female'] as gender>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" value="${gender}" type="radio" id="${gender?index}" required
                               name="gender">
                        <label class="form-check-label" for="${gender?index}">${gender}</label>
                    </div>
                </#list>
            </div>
            <div class="mb-3">
                <label for="phone" class="form-label"><@spring.message "register.phonenumber"/></label>
                <input type="tel" class="form-control" id="phone" aria-describedby="phoneHelp" required name="phone">
                <div id="phoneHelp" class="form-text"><@spring.message "register.phonenumberinfo"/></div>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label"><@spring.message "login.password"/></label>
                <input type="password" class="form-control" id="password" required name="password">
            </div>
            <div class="mb-3">
                <label for="repeatPassword" class="form-label"><@spring.message "register.repeatpassword"/></label>
                <input type="password" class="form-control" id="repeatPassword" required name="password2">
            </div>
            <div class="mb-3">
                <div class="mt-2">
                    <p><@spring.message "register.birthdate"/></p>
                </div>
                <div class="row">
                    <div class="form-group col-md-4">
                        <select class="form-control" id="day" required name="day">
                            <option value="" selected disabled><@spring.message "register.day"/></option>
                            <#list 1..31 as day>
                                <option>${day}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="form-group col-md-4">
                        <select class="form-control" id="month" required name="month">
                            <option value="" selected disabled><@spring.message "register.month"/></option>
                            <option value="1"><@spring.message "january"/></option>
                            <option value="2"><@spring.message "February"/></option>
                            <option value="3"><@spring.message "March"/></option>
                            <option value="4"><@spring.message "April"/></option>
                            <option value="5"><@spring.message "May"/></option>
                            <option value="6"><@spring.message "June"/></option>
                            <option value="7"><@spring.message "July"/></option>
                            <option value="8"><@spring.message "August"/></option>
                            <option value="9"><@spring.message "September"/></option>
                            <option value="10"><@spring.message "October"/></option>
                            <option value="11"><@spring.message "November"/></option>
                            <option value="12"><@spring.message "December"/></option>
                        </select>
                    </div>
                    <div class="form-group col-md-4">
                        <select class="form-control" id="year" required name="year">
                            <option value="" selected disabled><@spring.message "register.year"/></option>
                            <#list 1940..2005 as year>
                                <option>${year?c}</option>
                            </#list>
                        </select>
                    </div>
                </div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary mb-3"><@spring.message "login.registerhref"/></button>
        </form>
    </div>
</@c.page>