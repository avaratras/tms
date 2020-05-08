const tbody = document.getElementById("tbody");

const pageSize = 5;

const subjectPageSize = 4;
document.addEventListener("DOMContentLoaded", function() {
    callAPI("/admin/courses/g?page=1&size=" + pageSize)
        .then(resp => {
        //    console.log(resp);
            $('#pagination').twbsPagination({
                totalPages: resp.totalPages,
                visiblePages: 5,
                onPageClick: function (event, page) {
                    callAPI('/admin/courses/g?page=' + page + '&size=' + pageSize)
                        .then(res => {
                            const tableData = res.content;
                            tbody.innerHTML = '';
                            tableData.forEach((e,i) => {
                                tbody.innerHTML += template(e);
                            });
                        })
                        .catch(err => console.log(err));
                }
            })
        })
        .catch(err => console.log(err));
});

function template(data) {
    const html = '<tr id="'+data.courseId+'">' +
        '<th scope="row">'+data.courseId+'</th>' +
        '<td>'+data.courseName+'</td>' +
        '<td>'+data.startDate+'</td>' +
        '<td>'+data.endDate+'</td>' +
        '<td>' +
        '<button type="button" class="btn btn-info btn-circle btn-sm mr-1" data-toggle="modal" data-target="#viewCourse" data-whatever="'+data.courseId+'"><span class="icon text-white-50"><i class="fas fa-list"></i></span></button>' +
        '<a href="/admin/courses/edit?id='+data.courseId+'" class="btn btn-warning btn-circle btn-sm mr-1"><span class="icon text-white-50"><i class="fas fa-edit"></i></span></a>' +
        '<a href="javascript:deleteCourse('+ data.courseId +')" class="btn btn-danger btn-circle btn-sm mr-1"><span class="icon text-white-50"><i class="fas fa-trash"></i></span></a>' +
        '</td>' +
        '</tr>';
    return html;
};

function callAPI(url) {
    return fetch(url).then(res => res.json());
}


const subjectTbody = document.getElementById('subject-tbody');
$('#viewCourse').on('show.bs.modal', function (e) {
    const button = $(e.relatedTarget);
    const courseId = button.data('whatever');
    callAPI('/admin/courses/api/get?id='+ courseId)
        .then(resp => {
        //    console.log(resp);
            $('#courseName').val(resp.courseName);
            $('#description').val(resp.description);
            $('#startDate').val(resp.startDate);
            $('#endDate').val(resp.endDate);
            $('#trainee').html('');
            for(let tr of resp.trainees) {
                $('#trainee').append($('<option>').text(tr.username));
            }
        })
        .catch(err => console.log(err));
    callAPI('/admin/subjectcourse/api/get?courseid='+courseId+'&page=1&size='+subjectPageSize)
        .then(resp => {
             console.log(resp);
             if(resp.content.length > 0) {
             $('#totalSubject').text(' total: ' + resp.totalElements);
             $('#subjectPagination').twbsPagination({
                    totalPages: resp.totalPages,
                    visiblePages: 5,
                    onPageClick: function (event, page) {
                    callAPI('/admin/subjectcourse/api/get?courseid='+courseId+'&page='+page+'&size='+subjectPageSize)
                        .then(res => {
                            const tableData = res.content;
                            subjectTbody.innerHTML = '';
                            tableData.forEach((e,i) => {
                                subjectTbody.innerHTML += template2(e);
                            });

                        })
                        .catch(err => console.log(err));
                        }
             })
            }

        })
        .catch(err => console.log(err));


});
$('#viewCourse').on('hide.bs.modal', function (e) {
    subjectTbody.innerHTML = '<tr style="text-align: center"><td colspan="7"> No Subject Available </td></tr>';
    $('#subjectPagination').twbsPagination('destroy');
    document.getElementById('totalSubject').innerHTML = ''
});

function template2(data) {
    const subject = data.subject;
    const html =
            '<td>'+subject.title+'</td>' +
            '<td>'+subject.duration+'</td>' +
            '<td>'+subject.category.categoryName+'</td>' +
            '<td>'+subject.trainer.map(e => e.username).toString()+'</td>' +
            '<td>'+data.startDate+'</td>';
    return html;
}
l
function deleteCourse(courseId) {
    const agree = confirm("Are you sure you want to delete this course?");
    console.log(agree);
    if(agree === true) {
        console.log('admin/courses/api/delete/' + courseId);
        fetch('/admin/courses/api/delete/' + courseId,{
            method: 'delete'
        })
        .then(() => window.location.href = '/admin/courses/lists')
        .catch(err => console.log(err));
    }
}