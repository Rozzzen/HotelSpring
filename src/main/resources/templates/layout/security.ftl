<#assign isAuthorized = Session.SPRING_SECURITY_CONTEXT??>

<#if isAuthorized>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    role = user.getRole()
    >
<#else>
    <#assign
    name = "unknown"
    role = "GUEST"
    >
</#if>