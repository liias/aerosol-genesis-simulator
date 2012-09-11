<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title></title>
</head>
<body>

<c:forEach items="${parameters}" var="parameter">
    <p>
        <label for="${parameter.id}_value">${parameter.label}:</label>

        <select id="${parameter.id}_value">
            <option>0</option>
            <option>1</option>
        </select>
    </p>
</c:forEach>

<form:form method="post" action="order/add" modelAttribute="order">
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