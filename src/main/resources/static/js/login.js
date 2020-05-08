var close = document.getElementsByClassName("closebtn");


for (let i = 0; i < close.length; i++) {
  close[i].onclick = function(){
    const div = this.parentElement;
    div.style.opacity = "0";
  }
}

function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};

document.addEventListener("DOMContentLoaded", function() {

    const para = getUrlParameter("message");
    console.log(para);
    if(para != null) {
        if(para === "logout") {
          const a = document.getElementById("alert");
          a.classList.add("success");
        }
    }
});

var username = document.getElementById("username");
username.oninvalid = function(e) {
    e.target.setCustomValidity("");
    if (!e.target.validity.valid) {
        e.target.setCustomValidity("Username cannot be blank");
    }
};
username.oninput = function(e) {
    e.target.setCustomValidity("");
};
var password = document.getElementById("password");
password.oninvalid = function(e) {
    e.target.setCustomValidity("");
    if (!e.target.validity.valid) {
          e.target.setCustomValidity("Password cannot be blank");
    }
};
password.oninput = function(e) {
    e.target.setCustomValidity("");
};


