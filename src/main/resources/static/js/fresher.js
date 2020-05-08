        function validateFunc(id) {
            var form = confirm('Are you sure about deleting this user?')
            if(form) {
                 fetch('/admin/trainee/delete/' + id).then(res => document.location.href = '')
            }
        }

        function validateFunc2(id) {
            var form = confirm('Are you sure about deleting this user?')
            if(form) {
                 fetch('/admin/delete-trainer/' + id).then(res => document.location.href = '/admin/getAllTrainer')
            }
        }

        $('#EditCategory').on('show.bs.modal', function (event) {
          const button = $(event.relatedTarget) // Button that triggered the modal
          const id = button.data('object-id');
          const data = button.data('object-name');// Extract info from data-* attributes
          // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
          // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
//          var modal = $(this)
//          modal.find('.modal-body input').val(data);
//          console.log(id);
//          console.log(data);
          $('#categoryName-2').val(data);
          $('#categoryId2').val(id);

        });

        function searchByUsername() {
            var input = document.getElementById('searchInput').value;
            if(input.length>0 && !input.empty) {
                document.location.href = '/admin/trainee/get?page=1'+ '&name=' + input
            }
            else {
                document.location.href = '/admin/trainee/get?page=1'+ '&name='
            }
        }

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
                /*check if the item starts with the same letters as the text field value:*/
                /*if (arr[i].userName.substr(0, val.length).toUpperCase() == val.toUpperCase()) {*/
                if (arr[i].username.toUpperCase().match(val.toUpperCase())) {
                  /*create a DIV element for each matching element:*/
                  b = document.createElement("DIV");
                  b.innerHTML = arr[i].username;
                  /*insert a input field that will hold the current array item's value:*/
                  b.innerHTML += "<input type='hidden' value='" + arr[i].username + "'>";
                  b.addEventListener("click", function(e) {
                      /*insert the value for the autocomplete text field:*/
                      inp.value = this.getElementsByTagName("input")[0].value;
                      searchByUsername();
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
                 searchByUsername();
             }
        });
axios.get('/admin/trainee/all').then(res => autocomplete(document.getElementById("searchInput"),res.data));