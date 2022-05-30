<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head></head>
<body>
<form action="LoginPro" method="POST">
    <table>
        <tr>
            <td>Login</td>
            <td><input type="text" name="login" value="qwe"></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" value="qwe"></td>
        </tr>
    </table>
    <br>
    <input type="reset" value="Reset">
    <input type="submit" value="Submit">
</form>
<p style="color:red">${messageLogin}</p>
</body>
</html>
