
var flag = 0;
var val;
var id;

function checkFirst(val, mess, id) {

    axios.get('/admin/category/checkExist/' + val+ "?id=" + id)
        .then(res =>{
            console.log(res.data)
            if(res.data == true) {
                document.getElementById('errMess'+mess).style.display="inline";
                flag = 0;
            }
            else {
                document.getElementById('errMess'+mess).style.display="none";
                 flag = 1;
            }
        });
};

var crt = document.getElementById('categoryName');
crt.addEventListener("keyup", function() {
    val = document.getElementById('categoryName').value;
    checkFirst(val,1, -1);
});

var edit = document.getElementById('categoryName-2');
edit.addEventListener("keyup", function() {
    val = document.getElementById('categoryName-2').value;
    id = document.getElementById('categoryId2').value;
    checkFirst(val,2, id);
});

function save_crt(val, id) {
console.log(val);
    const data = {
        categoryId : id,
        categoryName: val
    }

    fetch('/admin/category/createCategory', {
                    method: 'post',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                    })
                    .then(res => {document.location.href = '/admin/category/getAllCategory';})
                    .catch(err => console.log(err));
}

function save_edit(val, id) {
console.log(val);
    const data = {
        categoryId : id,
        categoryName: val
    }

    fetch('/admin/category/editCategory', {
                    method: 'post',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                    })
                    .then(res => {document.location.href = '/admin/category/getAllCategory';})
                    .catch(err => console.log(err));

}

var btn = document.getElementById('btn_crt');
var btn2 = document.getElementById('btn_edit');

btn.addEventListener("click", function() {
                    if(flag == 1)
                        save_crt(val, id)
                });

btn2.addEventListener("click", function() {
                    if(flag == 1)
                        save_edit(val, id)
                });