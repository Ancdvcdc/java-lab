<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:choose>
    <c:when test="${not empty sessionScope.username}">
        <c:redirect url="/welcome.jsp"/>
    </c:when>
    <c:otherwise>
        <c:redirect url="/login.jsp"/>
    </c:otherwise>
</c:choose>
