<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head></head>
<body>
<fieldset>
    <legend>Параметры задания:</legend>

    <button id="button1" style="font-weight:bold" onclick=activateTask(1)>Задание 1</button>
    <button id="button2" style="display:none" onclick=activateTask(2)>Задание 2</button>
    <button id="button3" style="display:none" onclick=activateTask(3)>Задание 3</button>
    <button id="button4" style="display:none" onclick=activateTask(4)>Задание 4</button>
    <button id="button5" style="display:none" onclick=activateTask(5)>Задание 5</button>

    <form action="Step4Pro" method="POST">
        <table>
            <tr>
                <td>Запрос на извлечение данных</td>
                <td class="form1"><textarea name="sourceDbQuery1" rows="4" cols="49">SELECT * FROM TEST1 WHERE NAME = 'MIKE'</textarea></td>
                <td class="form2" style="display:none"><textarea name="sourceDbQuery2" rows="4" cols="49">SELECT * FROM TEST1 WHERE NAME = 'SANDRA'</textarea></td>
                <td class="form3" style="display:none"><textarea name="sourceDbQuery3" rows="4" cols="49"></textarea></td>
                <td class="form4" style="display:none"><textarea name="sourceDbQuery4" rows="4" cols="49"></textarea></td>
                <td class="form5" style="display:none"><textarea name="sourceDbQuery5" rows="4" cols="49"></textarea></td>
            </tr>
            <tr>
                <td>Запрос на сохранение данных</td>
                <td class="form1"><textarea name="targetDbQuery1" rows="4" cols="49">INSERT INTO TEST2 VALUES (?, ?)</textarea></td>
                <td class="form2" style="display:none"><textarea name="targetDbQuery2" rows="4" cols="49">INSERT INTO TEST2 VALUES (?, ?)</textarea></td>
                <td class="form3" style="display:none"><textarea name="targetDbQuery3" rows="4" cols="49"></textarea></td>
                <td class="form4" style="display:none"><textarea name="targetDbQuery4" rows="4" cols="49"></textarea></td>
                <td class="form5" style="display:none"><textarea name="targetDbQuery5" rows="4" cols="49"></textarea></td>
            </tr>
        </table>

        <input id="buttonBack" style="display:none" type="button" value="Back" onclick=backButton()>
        <input id="buttonSubmit" style="display:none" type="submit" value="Submit">

        <input id="buttonReset" type="button" value="Reset" onclick=resetButton(active)>
        <input id="buttonDeleteTask" type="button" value="Delete Task" onclick=deleteTask() disabled>
        <input id="buttonAddTask" type="button" value="Add Task" onclick=addTask()>
        <input id="buttonOk" type="button" value="Ok" onclick=okButton()>

        <input id="hiddenTotal" type="hidden" name="total">

    </form>
</fieldset>
<script src="step4.js"></script>
</body>
</html>