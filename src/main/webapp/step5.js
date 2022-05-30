let buttons=["", "button1", "button2", "button3", "button4", "button5"];
let forms=["", "form1", "form2", "form3", "form4", "form5"];
let circles=["circle0", "circle1", "circle2", "circle3", "circle4", ];
let active = 1;

function activateTask(i) {
    document.getElementById(buttons[active]).style.fontWeight = "normal";
    document.getElementsByClassName(forms[active])[0].style.display = "none";
    document.getElementsByClassName(forms[active])[1].style.display = "none";
    active = i;
    document.getElementById(buttons[active]).style.fontWeight = "bold";
    document.getElementsByClassName(forms[active])[0].style.display = "";
    document.getElementsByClassName(forms[active])[1].style.display = "";
};

let ajaxRequest;
let jobStatus;
window.setInterval(
    function(){
                ajaxRequest = new XMLHttpRequest();
                ajaxRequest.onreadystatechange =
                    function(){
                        if (ajaxRequest.readyState != 4) {
                            let sum = 0;
                            jobStatus = Array.from(ajaxRequest.responseText);
                            jobStatus.forEach(
                                function (item, index) {
                                    switch (item) {
                                        case "2":
                                            document.getElementById(circles[index]).style = "color:green";
                                            sum = sum + + item;
                                            break
                                        case "1":
                                            document.getElementById(circles[index]).style = "color:red";
                                            sum = sum + + item;
                                            break
                                    }
                                }
                            )
                            if(sum == jobStatus.length)
                                document.getElementById("submit").disabled = false;
                        }
                    }
                ajaxRequest.open("POST", "/DbExt/JobStatus", true);
                ajaxRequest.send(null);
    }
, 2000);

window.setInterval(
    function(){
        ajaxRequest = new XMLHttpRequest();
        ajaxRequest.onreadystatechange =
            function(){
                let errorMessage = ajaxRequest.responseText;
                console.log(errorMessage);
                if(errorMessage){
                    document.getElementById("error").innerText=errorMessage;
                    document.getElementById("error").style.display="";
                }
            }
        ajaxRequest.open("GET", "/DbExt/ErrorStatus", true);
        ajaxRequest.send(null);
    }
    , 9000);