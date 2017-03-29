<%--
    Copyright (C) 2011  JTalks.org Team
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.
    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.
    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="jtalks" uri="http://www.jtalks.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<c:set value="false" var="editing"/>

<c:if test="${topicId != null}">
    <c:set value="true" var="editing"/>
</c:if>

<head>
    <meta name="description" content="<c:out value="${topicDto.topic.branch.name}"/>">
    <title>
        <c:out value="${cmpTitlePrefix}"/>
        <c:if test="${topicDto.topic.branch.name != null}"><c:out value="${topicDto.topic.branch.name}"/> -
        </c:if><spring:message code="h.new_topic"/>
    </title>
</head>
<body>
<div class="container">
    <jtalks:breadcrumb breadcrumbList="${breadcrumbList}"/>

    <div id="previewPoll" class="well">
    </div>
    <form:form action="${pageContext.request.contextPath}${submitUrl}"
               method="POST" modelAttribute="topicDto" class="well anti-multipost submit-form"
               enctype="multipart/form-data">
        <input id="topicDraftLastSavedMillis" type="hidden" value="${topicDraft.lastSaved.millis}"/>
        <input id="branchId" type="hidden" value="${branchId}"/>
        <input id="topicType" type="hidden" value="${topicDto.topic.type}"/>
        <div class='control-group hide-on-preview'>
            <div class='controls'>
                <spring:message code='label.topic.topic_title' var='topicTitlePlaceholder'/>
                <form:input path="topic.title" id="subject" type="text" size="45"
                            maxlength="255" tabindex="100"
                            class="full-width script-confirm-unsaved" placeholder="${topicTitlePlaceholder}"/>
                <form:errors path="topic.title" type="text" size="45" maxlength="255"
                             class="post" cssClass="help-inline focusToError"/>
            </div>
        </div>
        <jtalks:hasPermission targetId='${branchId}' targetType='BRANCH'
                              permission='BranchPermission.CREATE_STICKED_TOPICS'>
            <div class='control-group hide-on-preview'>
                <form:checkbox id="sticked" path="topic.sticked" value="true" tabindex="101"
                               class="confirm-unsaved form-check-radio-box"/>
                <label for='sticked' class='string optional'>
                    <spring:message code="label.sticked"/>
                </label>

                <form:errors path="topic.sticked"/>
            </div>
        </jtalks:hasPermission>

        <jtalks:hasPermission targetId='${branchId}' targetType='BRANCH'
                              permission='BranchPermission.CREATE_ANNOUNCEMENTS'>
            <div class='control-group hide-on-preview'>
                <form:checkbox id="announcement" path="topic.announcement" value="true" tabindex="102"
                               class="script-confirm-unsaved form-check-radio-box"/>
                <label for='announcement' class='string optional'>
                    <spring:message code="label.announcement"/>
                </label>
                <form:errors path="topic.announcement"/>
            </div>
        </jtalks:hasPermission>
        <jtalks:bbeditor labelForAction="${editing ? 'label.save' : 'label.send'}"
                         postText="${topicDto.bodyText}"
                         bodyParameterName="bodyText"
                         back="${pageContext.request.contextPath}/branches/${branchId}"/>
        <br/>
    </form:form>


</div>
</body>