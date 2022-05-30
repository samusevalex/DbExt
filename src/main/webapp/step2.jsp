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
<form action="Step2Pro" method="POST">
    <fieldset>
        <legend>Параметры приёмника:</legend>
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
                    <td><input type="text" name="targetDbUrl" value="~/test"></td>
                </tr>
                <tr>
                    <td>User</td>
                    <td><input type="text" name="targetDbUser" value="sa"></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="targetDbPassword"></td>
                </tr>
                <tr>
                    <td>Schema</td>
                    <td><input type="text" name="targetDbScheme" value="schema2"></td>
                </tr>
            </table>
        <br>
        <input type="reset" value="Reset">
        <input type="submit" value="Submit">
    </fieldset>
</form>
<p style="color:red">${messageStep2}<p>
</body>
</html>
