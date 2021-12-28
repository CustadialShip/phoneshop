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
            <div class="text-danger small">
                <c:if test="${not empty errorMessage}">
                    ${errorMessage}
                </c:if>
            </div>
            <div class="text-success small">
                <c:if test="${not empty errorMessage}">
                    <c:forEach var="successMessage" items="${successMessageList}">
                        ${successMessage}
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
    <br>
    <%--Info table--%>
    <div class="row">
        <div class="col border col-md-5">
            <h4>
                <spring:message code="titleTable.quantity"/>
            </h4>
        </div>
        <div class="col border col-md-5 bg-light">
            <h4>
                <spring:message code="titleTable.productCode"/>
            </h4>
        </div>
    </div>
    <form:form action="${pageContext.servletContext.contextPath}/quickCart" method="post"
               modelAttribute="quickUpdateCartRequest">
        <c:forEach var="id" begin="0" end="10">
            <div class="row">
                <div class="col border col-md-5 bg-light">
                    <input type="hidden" name="model">
                    <form:input path="quantityFormList[${id}].model"
                                value="${quickUpdateCartRequest.quantityFormList[id].model}" cssClass="form-control"/>
                    <form:errors path="quantityFormList[${id}].model"
                                 value="${quickUpdateCartRequest.quantityFormList[id].model}"
                                 cssClass="text-danger small"/>
                </div>
                <div class="col border col-md-5">
                    <form:input path="quantityFormList[${id}].quantity"
                                value="${quickUpdateCartRequest.quantityFormList[id].quantity}"
                                cssClass="form-control"/>
                    <form:errors path="quantityFormList[${id}].quantity"
                                 value="${quickUpdateCartRequest.quantityFormList[id].quantity}"
                                 cssClass="text-danger small"/>
                </div>
            </div>
        </c:forEach>
        <br>
        <input type="submit" class="btn btn-secondary" value="Add to cart"/>
    </form:form>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>