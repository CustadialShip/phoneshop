<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
    <script>
        <%@ include file="js/addToCartList.js" %>
    </script>
    <title>
        <spring:message code="titlePage.quickOrder"/>
    </title>
</head>
<body>
<div class="container">
    <tags:headerLogin cart="${cart}" isCartAvailable="true"/>
    <%--Search--%>
    <div class="row">
        <div class="col col-md-9">
            <h1>
                <spring:message code="titlePage.quickOrder"/>
            </h1>
        </div>
    </div>
    <br>
    <%--Info table--%>
    <div class="row">
        <div class="col border col-md-5 bg-light">
            <spring:message code="titleTable.productCode"/>
        </div>
        <div class="col border col-md-5">
            <spring:message code="titleTable.quantity"/>
        </div>
    </div>
    <form:form action="${pageContext.servletContext.contextPath}/quickCart" method="post"
               modelAttribute="quickQuantityForm" id="quickQuantityForm">
        <div class="row">
            <div class="col col-md-5">
                <form:input path="model" form="quickQuantityForm" value="${quickQuantityForm.model}"/>
                <br>
                <form:errors path="model" style="color: red; font-size: small"/>
            </div>
            <div class="col col-md-5">
                <form:input path="quantity" form="quickQuantityForm" value="${quickQuantityForm.quantity}"/>
                <br>
                <form:errors path="quantity" style="color: red; font-size: small"/>
            </div>
            <div class="col col-md-1">
                <input type="submit" value="Add to cart" class="btn btn-outline-secondary" form="quickQuantityForm"/>
            </div>
        </div>
    </form:form>


<%--    <form:form action="${pageContext.servletContext.contextPath}/quickCart/mult" method="post"--%>
<%--               modelAttribute="quickUpdateCartRequest" id="formMap">--%>
<%--            <c:forEach var="map" items="item" varStatus="status">--%>
<%--                <div class="row">--%>
<%--                    <div class="col border col-md-5 bg-light">--%>
<%--                        <input type="hidden" name="model">--%>
<%--                        <form:input path="quickUpdateCartRequest[${status.index}].model" form="formMap"/>--%>
<%--                        <form:errors path="quickUpdateCartRequest[${status.index}].model"/>--%>
<%--                    </div>--%>
<%--                    <div class="col border col-md-5">--%>
<%--                        <form:input path="quickUpdateCartRequest[${status.index}].quantity" form="formMap"/>--%>
<%--                        <form:errors path="quickUpdateCartRequest[${status.index}].quantity"/>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <button>Add to cart</button>--%>
<%--            </c:forEach>--%>
<%--    </form:form>--%>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>