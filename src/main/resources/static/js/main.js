//check firstname
$('#firstName').on('keyup', function () {

    if ($('#firstName').val()!="") {
        $('#firstname-message').css('display', 'none');
        $('#submit').prop('disabled', false);

    } else {
    $('#firstname-message').css('display', 'inline');
        $('#firstname-message').html('<i class="fa fa-times-circle" style="font-size: 18px" >  Firstname is not valid</i>').css('color', 'red');
        $('#submit').prop('disabled',true);
    }
});
//check lastname
$('#lastName').on('keyup', function () {

    if ($('#lastName').val()!="") {
        $('#lastname-message').css('display', 'none');
        $('#submit').prop('disabled', false);

    } else {
    $('#lastname-message').css('display', 'inline');
        $('#lastname-message').html('<i class="fa fa-times-circle" style="font-size: 18px" >  Lastname is not valid</i>').css('color', 'red');
        $('#submit').prop('disabled',true);
    }
});
//check confirm password
$('#password,#re-password').on('keyup', function () {
    if ($('#password').val() == $('#re-password').val() && $('#password').val().length>8) {
        $('#password-message').html('<i class="fa fa-check-circle" style="font-size: 18px" >  Password matching</i>').css('color', 'green');
        $('#submit').prop('disabled', false);
    }else {
    $('#password-message').html('<i class="fa fa-times-circle" style="font-size: 18px" >  Password  is not valid </i>').css('color', 'red');
            $('#submit').prop('disabled',true);
    }
});

//check duplicate username
var userRegex=/^(?!.*__.*)(?!.*\.\..*)[a-z0-9_]+$/;
$('#username').on('keyup',function(){
if(!userRegex.test(String($('#username').val()).toLowerCase())){
$("#username-message").css("display", "inline");
    $("#username-message").html("<i class=\"fa fa-times-circle\" style=\"font-size: 18px\" > Username is existed or not valid </i>").css("color", "red");
    $('#submit').prop('disabled',true);
}else{
$.ajax({
            type : "GET",
            url : "/admin/check-exist-user",
            data : {
                userId: $('#userId').val(),
                username : $('#username').val()
            },
            dataType : 'text',
            timeout : 100000,
            success : function(data) {
                if (data=="false") {
                    $("#username-message").css("display", "none");
                    $('#submit').prop('disabled',false);
                } else {
                $("#username-message").css("display", "inline");
                    $("#username-message").html("<i class=\"fa fa-times-circle\" style=\"font-size: 18px\" > Username is existed or not valid </i>").css("color", "red");
                    $('#submit').prop('disabled',true);
                }
            },
            error : function(e) {
                console.log("ERROR: ", e);
            }
        });
}
});

//check duplicate email
var emailRegex=/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
$('#email').on('keyup',function(){
if(!emailRegex.test(String($('#email').val()).toLowerCase())){
$("#email-message").css("display", "inline");
 $("#email-message").html("<i class=\"fa fa-times-circle\" style=\"font-size: 18px\" > Email is existed or not valid</i>").css("color", "red");
                    $('#submit').prop('disabled',true);
}else{
$.ajax({
            type : "GET",
            url : "/admin/check-exist-email",
            data : {
             userId: $('#userId').val(),
                email : $('#email').val()
            },
            dataType : 'text',
            timeout : 100000,
            success : function(data) {
                if (data=="false") {
                    $("#email-message").css("display", "none");
                    $('#submit').prop('disabled',false);

                } else {
                $("#email-message").css("display", "inline");
                    $("#email-message").html("<i class=\"fa fa-times-circle\" style=\"font-size: 18px\" > Email is existed or not valid</i>").css("color", "red");
                    $('#submit').prop('disabled',true);

                }
            },
            error : function(e) {
                console.log("ERROR: ", e);
            }
        });
}
});

//check duplicate telephone
var telephoneRegex=/^0[0-9]{9,10}$/;
$('#phonenumber').on('keyup',function(){
if(!telephoneRegex.test(String ($('#phonenumber').val()))){
$("#phonenumber-message").css("display", "inline");
        $("#phonenumber-message").html("<i class=\"fa fa-times-circle\" style=\"font-size: 18px\" > Telephone is existed or not valid</i>").css("color", "red");
        $('#submit').prop('disabled',true);
}else{
    $.ajax({
            type : "GET",
            url : "/admin/check-exist-phonenumber",
            data : {
             userId: $('#userId').val(),
                phonenumber : $('#phonenumber').val()
            },
            dataType : 'text',
            timeout : 100000,
            success : function(data) {
                if (data=="false") {
                    $("#phonenumber-message").css("display", "none");
                    $('#submit').prop('disabled',false);

                } else {
                $("#phonenumber-message").css("display", "inline");
                    $("#phonenumber-message").html("<i class=\"fa fa-times-circle\" style=\"font-size: 18px\" > Telephone is existed or not valid</i>").css("color", "red");
                    $('#submit').prop('disabled',true);

                }
            },
            error : function(e) {
                console.log("ERROR: ", e);
            }
        });
}

});