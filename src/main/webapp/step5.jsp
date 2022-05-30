<%--
  Created by IntelliJ IDEA.
  User: samusev
  Date: 31.10.2019
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head></head>
<body>
<fieldset>
    <legend>Обработка задач:</legend>
    <table>
        <tr>
            <td rowspan="2">
                <button id="button1" style="font-weight:bold" onclick=activateTask(1)>Задание 1<span id="circle0" style="color:orange">&#x25cf</span></button><br><br>
                <button id="button2" style="${d2}" onclick=activateTask(2)>Задание 2<span id="circle1" style="color:orange">&#x25cf</span></button><br><br>
                <button id="button3" style="${d3}" onclick=activateTask(3)>Задание 3<span id="circle2" style="color:orange">&#x25cf</span></button><br><br>
                <button id="button4" style="${d4}" onclick=activateTask(4)>Задание 4<span id="circle3" style="color:orange">&#x25cf</span></button><br><br>
                <button id="button5" style="${d5}" onclick=activateTask(5)>Задание 5<span id="circle4" style="color:orange">&#x25cf</span></button>
            </td>

            <td>
                <textarea class="form1" rows="4" cols="49" readonly>${param["sourceDbQuery1"]}</textarea>
                <textarea class="form2" style="display:none" rows="4" cols="49" readonly>${param["sourceDbQuery2"]}</textarea>
                <textarea class="form3" style="display:none" rows="4" cols="49" readonly>${param["sourceDbQuery3"]}</textarea>
                <textarea class="form4" style="display:none" rows="4" cols="49" readonly>${param["sourceDbQuery4"]}</textarea>
                <textarea class="form5" style="display:none" rows="4" cols="49" readonly>${param["sourceDbQuery5"]}</textarea>
            </td>
        <td rowspan="2">
            <textarea id="error" style="display:none" rows="10" cols="49" readonly></textarea>
        </td>
        </tr>
        <tr>
            <td>
                <textarea class="form1" rows="4" cols="49" readonly>${param["targetDbQuery1"]}</textarea>
                <textarea class="form2" style="display:none" rows="4" cols="49" readonly>${param["targetDbQuery2"]}</textarea>
                <textarea class="form3" style="display:none" rows="4" cols="49" readonly>${param["targetDbQuery3"]}</textarea>
                <textarea class="form4" style="display:none" rows="4" cols="49" readonly>${param["targetDbQuery4"]}</textarea>
                <textarea class="form5" style="display:none" rows="4" cols="49" readonly>${param["targetDbQuery5"]}</textarea>
            </td>
        </tr>
    </table>
    <br>
    <form action="ok.html" method="POST">
        <input id="submit" type="submit" value="Finish" disabled>
    </form>

</fieldset>
<script src="step5.js"></script>
</body>
</html>