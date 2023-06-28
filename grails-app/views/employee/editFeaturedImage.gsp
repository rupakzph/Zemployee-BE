<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'employee.label', default: 'Employee')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#create-employee" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="create-employee" class="col-12 content scaffold-create" role="main">
                    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
                    <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.employee}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.employee}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                    </g:hasErrors>
<g:uploadForm name="uploadFeaturedImage" action="uploadFeaturedImage">
    <g:hiddenField name="id" value="${this.employee?.id}" />
    <g:hiddenField name="version" value="${this.employee?.version}" />
    <input type="file" name="featuredImageFile" />
    <fieldset class="buttons">
        <input class="save" type="submit" value="${message(code: 'employee.featuredImage.upload.label', default: 'Upload')}" />
    </fieldset>
</g:uploadForm>
                </div>
            </section>
        </div>
    </div>
    </body>
</html>
