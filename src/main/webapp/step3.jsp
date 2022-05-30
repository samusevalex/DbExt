<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!--
    <script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script>
        $(function(){
            $("#no, #reset").click(function(){
                $("#hidenElement").hide();
            });
            $("#yes").click(function(){
                $("#hidenElement").show();
            });
            $("#reset").click(function(){
                $("#trVal").text(1);
            });
            $("#range").change(function(){
                $("#trVal").text(this.value);
            });
        });
    </script>
    -->
</head>
<body>
<form action="Step3Pro" method="post">
    <fieldset>

        <legend>Параметры многопоточности и транзакций на commit:</legend>

            Использовать многопоточность
            <input id="no" type="radio" name="isMultithread" value="false" onclick=document.getElementById("hidenElement").style.display="none" checked> Нет
            <input id="yes" type="radio" name="isMultithread" value="true" onclick=document.getElementById("hidenElement").style.display=""> Да

        <p id="hidenElement" style="display:none">
            Количество потоков:
            <input type="number" name="threadCount" min="2" max="10" value="2">
        </p>

        <p>
            Количество записей в одной транзакции:
            <input id="range" type="range" name="rowsPerTran" min="1" max="10" step="1" value="1" onchange=document.getElementById("rowsPerTranValue").innerHTML=this.value >
            <i id="rowsPerTranValue">1</i>
        </p>

            <input id="reset" type="reset" value="Reset" onclick=document.getElementById("rowsPerTranValue").innerHTML=1;document.getElementById("hidenElement").style.display="none">
            <input id="submit" type="submit" value="Submit">

    </fieldset>
</form>
<p style="color:red">${messageStep3}<p>
</body>
</html>