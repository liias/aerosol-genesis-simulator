<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title></title>
</head>
<body>


<form:form method="post" action="order/add" modelAttribute="simulationOrder">


    <c:forEach items="${simulationOrder.parameters}" var="parameter" varStatus="loop">
        <p>
            <form:errors path="parameters[${loop.index}].value"/>
                <%--            <select id="${parameter.id}_value">
                    <option>0</option>
                    <option>1</option>
                </select>--%>
            Value: <form:input path="parameters[${loop.index}].value"/>
            Min: <form:input path="parameters[${loop.index}].min"/>
            Max: <form:input path="parameters[${loop.index}].max"/>
        </p>
    </c:forEach>


    <input type="submit" value="Go"/>
</form:form>


<pre>
TODO:

free air   /   forest
min max    /   min max


Input#number_of_runs
etc
</pre>

</body>
</html>