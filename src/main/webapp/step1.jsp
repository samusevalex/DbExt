<%--
  Created by IntelliJ IDEA.
  User: samusev
  Date: 24.10.2019
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head></head>
<body>
<form action="Step1Pro" method="POST">
    <fieldset>
        <legend>Параметры источника:</legend>
            <table>
                <tr>
                    <td>DB</td>
                    <td>
                        <select name="db">
                            <c:forEach items="${listDb}" var="category">
                                <option value="${category}">${category}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>URL</td>
                    <td><input type="text" name="sourceDbUrl" value="~/test"></td>
                </tr>
                <tr>
                    <td>User</td>
                    <td><input type="text" name="sourceDbUser" value="sa"></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="sourceDbPassword"></td>
                </tr>
                <tr>
                    <td>Scheme</td>
                    <td><input type="text" name="sourceDbScheme" value="schema1"></td>
                </tr>
            </table>
        <br>
        <input type="reset" value="Reset">
        <input type="submit" value="Submit">
    </fieldset>
</form>
<p style="color:red">${messageStep1}<p>
</body>
</html>