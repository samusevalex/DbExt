let buttons=["", "button1", "button2", "button3", "button4", "button5"];
let forms=["", "form1", "form2", "form3", "form4", "form5"];
let total = 1;
let active = 1;
let i;

function addTask() {
    if (total < 5) {
        switch (total) {
            case 1:
                document.getElementById("buttonDeleteTask").disabled = false;
                break;
            case 4:
                document.getElementById("buttonAddTask").disabled = true;
                break;
        }
        total++;
        document.getElementById(buttons[total]).style.display = "";
        activateTask(total);
    }
};

function deleteTask() {
    if (total > 1) {
        switch (total) {
            case 2:
                document.getElementById("buttonDeleteTask").disabled = true;
            case 5:
                document.getElementById("buttonAddTask").disabled = false;
        }
        document.getElementById(buttons[total]).style.display = "none";

        resetButton(total);
        total--;

        if (active == total + 1)
            activateTask(total);
    }
};

function activateTask(i) {
    document.getElementById(buttons[active]).style.fontWeight = "normal";
    document.getElementsByClassName(forms[active])[0].style.display = "none";
    document.getElementsByClassName(forms[active])[1].style.display = "none";
    active = i;
    document.getElementById(buttons[active]).style.fontWeight = "bold";
    document.getElementsByClassName(forms[active])[0].style.display = "";
    document.getElementsByClassName(forms[active])[1].style.display = "";
};

function resetButton(i) {
    document.getElementsByClassName(forms[i])[0].firstChild.value = "";
    document.getElementsByClassName(forms[i])[1].firstChild.value = "";
};

function okButton() {
    for (i = 1; i <= 5; i++) {
        document.getElementsByClassName(forms[i])[0].firstChild.readOnly = true;
        document.getElementsByClassName(forms[i])[1].firstChild.readOnly = true;
    }
    document.getElementById("buttonReset").style.display="none";
    document.getElementById("buttonDeleteTask").style.display="none";
    document.getElementById("buttonAddTask").style.display="none";
    document.getElementById("buttonOk").style.display="none";
    document.getElementById("buttonBack").style.display="";
    document.getElementById("buttonSubmit").style.display="";
    document.getElementById("hiddenTotal").value=total;
};
function backButton() {
    for (i = 1; i < 6; i++) {
        document.getElementsByClassName(forms[i])[0].firstChild.readOnly = false;
        document.getElementsByClassName(forms[i])[1].firstChild.readOnly = false;
    }
    document.getElementById("buttonReset").style.display="";
    document.getElementById("buttonDeleteTask").style.display="";
    document.getElementById("buttonAddTask").style.display="";
    document.getElementById("buttonOk").style.display="";
    document.getElementById("buttonBack").style.display="none";
    document.getElementById("buttonSubmit").style.display="none";
};