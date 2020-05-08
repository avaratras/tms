const courseForm = document.getElementById('courseForm');
const selects = document.getElementsByTagName('select');
const description = document.getElementById('description');
const courseName = document.getElementById('courseName');
const startDate = document.getElementById('startDate');
const endDate = document.getElementById('endDate');
const trainee = document.getElementById('trainee');

let courseId = '';

let dateValidation = true;
let courseNameValidation = true;
let dateSubjectValdation = true;

let objectSubjectCourse = [];

const subjectPageSize = 4;

function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};

function callAPI(url) {
    return fetch(url).then(res => res.json());
}

document.addEventListener("DOMContentLoaded", function() {
    // render
    callAPI('/admin/courses/api/get?id='+getUrlParameter('id'))
        .then(resp => {
            //      console.log(resp);
            courseId = resp.courseId;
            courseName.value = resp.courseName;
            description.value = resp.description;
            startDate.value = resp.startDate;
            endDate.value = resp.endDate;

            const traineeArr = resp.trainees
            callAPI('/admin/trainee/api/get')
                .then(res => {
                //   console.log(res)
                    trainee.innerHtml = '';
                    for(u of res) {
                        let html ='';

                        if(traineeArr.filter(e => e.userId === u.userId).length > 0) {
                            html = '<option value="'+u.userId+'" selected>' +u.username + '</option>';
                        } else {
                            html = '<option value="'+u.userId+'">' +u.username + '</option>';
                        }
                        trainee.innerHTML += html;
                    }
                    $('.selectpicker').selectpicker('refresh');
                })
                .catch(err => console.log(err));

            callAPI('/admin/subjectcourse/api/get?courseid='+courseId)
                .then(resp => {
                    console.log(resp);
                    document.getElementById('totalSubject').innerHTML = 'total: ' + resp.length;
                    for(let data of resp) {
                        const subject = data.subject;
                        const ob = {
                            subjectId: data.subject.subjectId,
                            startDate: data.startDate
                        }
                        objectSubjectCourse.push(ob);
                    }
                    const tbody = document.getElementById('tbody');

                    callAPI('/admin/subject/api/get?page=1&size=' + subjectPageSize)
                        .then(res => {
                            $('#subjectPagination').twbsPagination({
                                totalPages: res.totalPages,
                                visiblePages: 5,
                                onPageClick: function (event, page) {
                                callAPI('/admin/subject/api/get?page=' + page + '&size=' + subjectPageSize)
                                    .then(res => {
                                        const tableData = res.content;
                                        tbody.innerHTML = '';
                                        tableData.forEach((e,i) => {
                                            tbody.innerHTML += template(e);
                                        });
                                        // render if subject is checked
                                        for(let subject of objectSubjectCourse) {
                                            const cbox = document.getElementById('checkbox'+subject.subjectId);
                                            if(cbox !== null) {
                                                cbox.checked = true;
                                            }
                                            const startDateSubject = document.getElementById('subjectDate'+subject.subjectId);
                                            if(startDateSubject !== null) {
                                                startDateSubject.value = subject.startDate;
                                                startDateSubject.disabled = false;
                                            }
                                        }
                                        const checkboxes = courseForm.getElementsByTagName('input');
                                        for (let i = 0; i < checkboxes.length; i++) {
                                            if (checkboxes[i].type === 'checkbox') {
                                                const startDateSubject = document.getElementById('subjectDate'+checkboxes[i].value);
                                                checkboxes[i].addEventListener('change',function (e) {
                                                    if(e.target.checked) {
                                                        startDateSubject.disabled = false;
                                                        const ob = {
                                                            subjectId : Number(e.target.value),
                                                            startDate : startDateSubject.value
                                                        }
                                                        objectSubjectCourse.push(ob);
                                                        dateSubjectValdation = false;
                                                    } else {
                                                        startDateSubject.disabled = true;
                                                        console.log(e.target.value);
                                                        objectSubjectCourse = objectSubjectCourse.filter(o => (o.subjectId !== Number(e.target.value)));
                                                        console.log(objectSubjectCourse);
                                                        if(objectSubjectCourse.length === 0) {
                                                          dateSubjectValdation = true;
                                                        }
                                                        }
                                                        document.getElementById('totalSubject').innerHTML = 'total: ' + objectSubjectCourse.length;
                                                })
                                                                                                    startDateSubject.addEventListener('change', function (e) {
                                                                                    objectSubjectCourse = objectSubjectCourse.filter(o => !(o.subjectId === Number(checkboxes[i].value)));
                                                                                    const obj = {
                                                                                        subjectId: Number(checkboxes[i].value),
                                                                                        startDate: e.target.value
                                                                                    }
                                                                                    objectSubjectCourse.push(obj);
                                                                                    //   console.log(objectSubjectCourse);
                                                                                    const startDateSubjectMessage = document.getElementById('startDateSubject-message' + checkboxes[i].value);
                                                                                    if(startDate.value === '' && endDate.value === '') {
                                                                                        startDateSubjectMessage.innerHTML = 'Start date and end date are blank'
                                                                                        startDateSubject.classList.add('is-invalid');
                                                                                    } else if(startDate.value === '' || startDate.value === '') {
                                                                                        startDateSubjectMessage.innerHTML = 'Start date or end date is blank'
                                                                                        startDateSubject.classList.add('is-invalid');
                                                                                    } else {
                                                                                        const startDateTime = (new Date(startDate.value)).getTime();
                                                                                        const endDateTime = (new Date(endDate.value)).getTime();
                                                                                        const startDateSubjectTime = (new Date(startDateSubject.value)).getTime();
                                                                                        if(startDateTime <= startDateSubjectTime && endDateTime >= startDateSubjectTime)
                                                                                        {
                                                                                            startDateSubject.classList.remove('is-invalid');
                                                                                            dateSubjectValdation = true;
                                                                                        } else {
                                                                                            startDateSubjectMessage.innerHTML = 'startDateSubject must between start date and endate';
                                                                                            startDateSubject.classList.add('is-invalid');
                                                                                            dateSubjectValdation = false;
                                                                                        }

                                                                                    }
                                                                                })



                                                                            }
                                                                        }
                                                                    })
                                                                    .catch(err => console.log(err));
                                                            }
                                                        })
                                                    })
                                                    .catch(err => console.log(err));









                })
                .catch(err => console.log(err));

        })
        .catch(err => console.log(err));


});

courseForm.addEventListener('submit', function (e) {
    e.preventDefault();
    if(courseForm.checkValidity() === false) {
        e.stopPropagation();
        courseForm.classList.add('was-validated');
    } else {
        if(dateValidation && courseNameValidation && dateSubjectValdation) {
            let data = {};

            data['' + description.name + ''] = description.value;
            data['' + courseName.name + ''] = courseName.value;
            data['' + startDate.name + ''] = startDate.value;
            data['' + endDate.name + ''] = endDate.value;

            for(let select of selects) {
                let oData= [];
                for(let option of select) {
                    if(option.selected === true) {
                        oData = [...oData,option.value];
                    }
                }
                data['' + select.name + ''] = oData;
            }

            console.log(JSON.stringify(data));

            fetch('/admin/courses/api/put/' + courseId, {
                method: 'put',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
                })
                .then(res => res.json())
                .then((resp) => {
                    console.log(resp);
                    for(let ob of objectSubjectCourse) {
                        ob.courseId = resp.courseId;
                    }
                    fetch('/admin/subjectcourse/api/put/' + courseId, {
                        method: 'put',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(objectSubjectCourse)
                    })
                        .then(() => {
                            window.location.href = '/admin/courses/lists';
                        })
                        .catch(err => console.log(err));
                  //  window.location.href = '/admin/courses/lists'
                })
                .catch(err => console.log(err));

        }

    }
});


endDate.addEventListener('change', checkDate);
startDate.addEventListener('change', checkDate);

function checkDate(e) {
    const startDateTime = (new Date(document.getElementById('startDate').value)).getTime();
    const endDateTime = (new Date(document.getElementById('endDate').value)).getTime();
    if(endDateTime - startDateTime <= 0) {
        document.getElementById('endDate-message').innerHTML = 'End date cannot be less than start date';
        endDate.classList.add('is-invalid');
        dateValidation = false;
    } else {
        dateValidation = true;
        endDate.classList.remove('is-invalid');
    }
    for(let ob of objectSubjectCourse) {
        const startDateSubject = document.getElementById('subjectDate'+ob.subjectId);
        const startDateSubjectMessage = document.getElementById('startDateSubject-message' + ob.subjectId);
        const startDateSubjectTime = (new Date(startDateSubject.value)).getTime();
        if(startDateTime <= startDateSubjectTime && endDateTime >= startDateSubjectTime) {
            startDateSubject.classList.remove('is-invalid');
            dateSubjectValdation = true;
        } else {
            startDateSubjectMessage.innerHTML = 'startDateSubject must between start date and endate';
            startDateSubject.classList.add('is-invalid');
            dateSubjectValdation = false;
        }
    }
}

courseName.addEventListener('change', (e) => {
    callAPI("/admin/courses/api/get?name=" + e.target.value.trim())
    .then(resp => {
        if(resp === null) {
            courseNameValidation = true;
            courseName.classList.remove('is-invalid');
        } else {
            if(resp.courseId !== courseId) {
                courseNameValidation = false;
                document.getElementById('courseName-message').innerHTML = 'CourseName duplicated!';
                courseName.classList.add('is-invalid');
            } else {
                courseNameValidation = true;
                courseName.classList.remove('is-invalid');
            }

        }
    })
    .catch(err => console.log(err));
});






function template(data) {
    const html = '<tr id="'+data.subjectId+'">' +
        '<th scope="row"><input type="checkbox" class="form-control-input" id="checkbox'+data.subjectId+'" value="'+data.subjectId+'" ></th>' +
        '<td>'+data.title+'</td>' +
        '<td>'+data.duration+'</td>' +
        '<td>'+data.category.categoryName+'</td>' +
        '<td>'+
        '<input id="'+'subjectDate'+data.subjectId+'" type="date" name="startDateSubject" disabled class="form-control" required>'+
        '<div class="invalid-feedback"><span id="startDateSubject-message'+data.subjectId+'">This input cannot be left blank</span></div>' +
        '</td>';
    return html;
};


