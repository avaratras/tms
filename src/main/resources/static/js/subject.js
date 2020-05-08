//check duplicate and valid title
$('#title').on('keyup',function(){
    if(document.getElementById('titleErr'))
        $("#titleErr").css("display", "none");
    var str = $('#title').val().trim();
    if(str.length == 0){
        $("#title-message").css("display", "inline");
        $("#title-message").html("<i class=\"fa fa-times-circle\" style=\"font-size: 18px\" > title is not valid </i>").css("color", "red");
        /*$('#submit').prop('disabled',true);*/
    }else{
        $.get('/admin/subject/check/' + str, function(data, status){
            if (data=="false") {
                $("#title-message").css("display", "none");
                /*$('#submit').prop('disabled',false);*/
            } else {
                $("#title-message").css("display", "inline");
                $("#title-message").html("<i class=\"fa fa-times-circle\" style=\"font-size: 18px\" > title is existed </i>").css("color", "red");
                /*$('#submit').prop('disabled',true);*/
            }
        });
    }
});

// check duration is number
var duration=/^[0-9]+$/;
$('#duration').on('keyup', function () {
    if ($('#duration').val().match(duration)) {
        $('#duration-message').css('display', 'none');
        /*$('#submit').prop('disabled', false);*/
    } else {
        $('#duration-message').css('display', 'inline');
        $('#duration-message').html('<i class="fa fa-times-circle" style="font-size: 17px" >Duration is a value of hours </i>').css('color', 'red');
        /*$('#submit').prop('disabled',true);*/
    }
});

$('#trainerSelect').change(function() {
     var list = document.getElementById("listTrainer");
     var val = this.value;
     var inp, btn, div, a;
     var user;
     if (!val) { return false;}

     axios.get('/admin/check/' + val)
        .then(res => {
             if(res.data == true) {
                 /*list.setAttribute("style","overflow: scroll; width: 97%; height: 110px; background : #D8D8D8; margin-left:3%")*/
                 div = document.createElement("DIV");
                 inp = document.createElement("input");
                 a = document.createElement("a");
                 btn = document.createElement("button");

                 var index = list.childElementCount;
                 for(i=0;i<=index;i++) {
                     val1 = document.getElementById('trainer'+i+'.userId');
                     val2 = document.getElementById('trainer['+i+']');
                     if( val1 && val == val1.value || val2 && val == val2.value) {
                         $('#trainer').val('...');
                         return;
                     } else if(!val1 && !val2) {
                        div.setAttribute("id", $("#trainerSelect option:selected").text());

                        inp.setAttribute("id", "trainer["+i+"]");
                        inp.setAttribute("name", "trainer["+i+"]");
                        inp.setAttribute("value", val);
                        inp.setAttribute("type", "hidden");

                        a.setAttribute("id", "removeTrainer");
                        a.addEventListener("click", function(){
                            div.remove();
                            $("#trainerSelect option[value='"+val+"']").show();
                        });

                        a.setAttribute("href", "#");
                        a.innerHTML += "<span class=\"fa fa-times\"></span>"

                        btn.setAttribute("type", "button");
                        btn.setAttribute("class", "btn btn-outline-primary");
                        btn.setAttribute("style", "width:60%");
                        btn.setAttribute("onclick","javascript:showInfo("+"'" + val + "'"+")");
                        btn.innerHTML += "<span class=\"fa fa-envelope\">"+$("#trainerSelect option:selected").text()+"</span>";

                        $("#trainerSelect option:selected").hide();

                        div.appendChild(inp);
                        div.appendChild(btn);
                        div.appendChild(a);
                        list.appendChild(div)
                     }
                 }
             }
             $('#trainerSelect').val('...');
        });
});

$("#listTrainer a[id^='a_']").on('click', function(){
    this.parentNode.remove();
});

function showInfo(trainerId) {
    axios.get('/admin/trainer/get/' + trainerId)
        .then(res => {
            if(res.data != null) {
                var div = document.getElementById('info');
                div.innerHTML='';
                var name, phone, city;
                name=document.createElement("h5");
                phone=document.createElement("label");
                city=document.createElement("label");

                name.innerHTML += "<b>Name: </b>" +res.data.lastname + " " + res.data.firstname;
                phone.innerHTML += "<b>Tel: </b>" +res.data.telephone+ ",";
                city.innerHTML += "<b> City: </b>"+res.data.city.cityName;

                div.appendChild(name);
                div.appendChild(phone);
                div.appendChild(city);
            }
        });

}

function validateFunc(id) {
   var form = confirm('Are you sure about deleting this subject?')
   if(form) {
       fetch('/admin/subject/delete/' + id).then( res => document.location.href = '/admin/subject/get/all?page=&title=')
   }
};

        function searchByTitle() {
            var input = document.getElementById('searchInput').value;
            if(input.length>0 && !input.empty) {
                document.location.href = '/admin/subject/get/all?page=1'+ '&title=' + input
            }
            else {
                document.location.href = '/admin/subject/get/all?page=1'+ '&title='
            }
        };

        function autocomplete(inp, arr) {
          /*the autocomplete function takes two arguments,
          the text field element and an array of possible autocompleted values:*/
          var currentFocus;
          inp.addEventListener("input", function(e) {
              var a, b, i, val = this.value;
              closeAllLists();

              if (!val) { return false;}
              currentFocus = -1;
              /*create a DIV element that will contain the items (values):*/
              a = document.createElement("DIV");
              a.setAttribute("id", this.id + "autocomplete-list");
              a.setAttribute("class", "autocomplete-items");
              /*append the DIV element as a child of the autocomplete container:*/
              this.parentNode.appendChild(a);
              /*for each item in the array...*/
              for (i = 0; i < arr.length; i++) {
                if (arr[i].title.toUpperCase().match(val.toUpperCase())) {
                  /*create a DIV element for each matching element:*/
                  b = document.createElement("DIV");
                  b.innerHTML = arr[i].title;
                  /*insert a input field that will hold the current array item's value:*/
                  b.innerHTML += "<input type='hidden' value='" + arr[i].title + "'>";
                  b.addEventListener("click", function(e) {
                      /*insert the value for the autocomplete text field:*/
                      inp.value = this.getElementsByTagName("input")[0].value;
                      searchByTitle();
                      closeAllLists();
                  });
                  a.appendChild(b);
                }
              }
          });
          /*execute a function presses a key on the keyboard:*/
          inp.addEventListener("keydown", function(e) {
              var x = document.getElementById(this.id + "autocomplete-list");
              if (x) x = x.getElementsByTagName("div");
              if (e.keyCode == 40) {
                /*If the arrow DOWN key is pressed,
                increase the currentFocus variable:*/
                currentFocus++;
                /*and and make the current item more visible:*/
                addActive(x);
              } else if (e.keyCode == 38) { //up
                /*If the arrow UP key is pressed,
                decrease the currentFocus variable:*/
                currentFocus--;
                /*and and make the current item more visible:*/
                addActive(x);
              } else if (e.keyCode == 13) {
                /*If the ENTER key is pressed, prevent the form from being submitted,*/
                e.preventDefault();
                if (currentFocus > -1) {
                  /*and simulate a click on the "active" item:*/
                  if (x) x[currentFocus].click();
                }
              }
          });
          function addActive(x) {
            /*a function to classify an item as "active":*/
            if (!x) return false;
            /*start by removing the "active" class on all items:*/
            removeActive(x);
            if (currentFocus >= x.length) currentFocus = 0;
            if (currentFocus < 0) currentFocus = (x.length - 1);
            /*add class "autocomplete-active":*/
            x[currentFocus].classList.add("autocomplete-active");
          }
          function removeActive(x) {
            /*a function to remove the "active" class from all autocomplete items:*/
            for (var i = 0; i < x.length; i++) {
              x[i].classList.remove("autocomplete-active");
            }
          }
          function closeAllLists(elmnt) {
            /*close all autocomplete lists in the document,
            except the one passed as an argument:*/
            var x = document.getElementsByClassName("autocomplete-items");
            for (var i = 0; i < x.length; i++) {
              if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
              }
            }
          }
          /*execute a function when someone clicks in the document:*/
          document.addEventListener("click", function (e) {
              closeAllLists(e.target);
          });
        }

        var input = document.getElementById('searchInput');
        input.addEventListener("keydown", function(e) {
             if(e.keyCode == 13) {
                 searchByTitle();
             }
        });
axios.get('/admin/subject/get/subjects').then(res => autocomplete(document.getElementById("searchInput"),res.data));