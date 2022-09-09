// $(window).on("load resize ", function() {
//     var scrollWidth = $('.tbl-content').width() - $('.tbl-content table').width();
//     $('.tbl-header').css({'padding-right':scrollWidth});
// }).resize();

getProvinces();
let idProvince;
function getProvinces() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/Provinces",
        success: function (data) {
            console.log(data)
            displayTable(data)
            // displayBrands()
        }
    })

}

function changePrice(n){
const formatter = new Intl.NumberFormat('it-IT', {
    style: 'currency',
    currency: 'VND',
    // minimumFractionDigits: 2
})
return  formatter.format(n);
}

function displayTable(data) {
    let result = ""
    result += "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">"
    result += "<tbody>"
    for (let i = 0; i < data.length; i++) {
        result += "<tr>"
        result += "<td>" + (i + 1) + "</td>"
        result += "<td>" + data[i].name + "</td>"
        result += "<td>" + data[i].area + "</td>"
        result += "<td>" + data[i].population + "</td>"
        result += "<td>" + data[i].gdp + "</td>"
        result += "<td>" + data[i].description + "</td>"
        result += "<td>" + data[i].country.name + "</td>"
        result += "<td><button type=\"button\" class=\"btn btn-danger\" onclick='datailForm(" + data[i].id + ")'>Xem</button>"
        result += "<button style=\"margin-left: 15px\" type=\"button\" class=\"btn btn-danger\" onclick='updateFrom(" + data[i].id + ")'>Sửa</button>"
        result += "<button style=\"margin-left: 15px\" type=\"button\" class=\"btn btn-danger\" onclick='deleteForm(" + data[i].id + ")'>Xóa</button></td>"
        result += "</tr>"
    }
    result += "</tbody>"
    result += "</table>"
    document.getElementById("list-provinces").innerHTML = result;
}
function datailForm(id){
    idProvince = id
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/Provinces/" + idProvince,
        success: function (data) {
            document.getElementById("nameDetail").innerHTML = "Tên thành phố: " + data.name
            document.getElementById("areaDetail").innerHTML = "Diện tích thành phố: " + data.area
            document.getElementById("populationDetail").innerHTML = "Dân số thành phố: " + data.population
            document.getElementById("gdpDetail").innerHTML = "Gdp thành phố: " + data.gdp
            document.getElementById("descriptionDetail").innerHTML = "Miêu tả: " + data.description
            document.getElementById("countryDetail").innerHTML = "Quốc gia: " + data.country.name
        }
    })

    $('#detailModal').modal("show")
}

function displayCreateForm() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/Provinces/Countries",
        success: function (data) {
            let result = ""
            for (let i = 0; i < data.length; i++) {
                result += "<option value='" + data[i].id + "'>" + data[i].name
                result += "</option>"
            }
            document.getElementById("countries").innerHTML = result;
        }
    })
    resetFormCreate()
    document.getElementById("titleFrom").innerHTML = "Tạo mới thành phố"
    document.getElementById("button").innerHTML = "Tạo mới"
    document.getElementById("button").setAttribute("onclick", "createProvince()")
    $('#myModal').modal('show');
}

function resetFormCreate(){
    document.getElementById("name").value = ""
    document.getElementById("area").value = ""
    document.getElementById("population").value = ""
    document.getElementById("gdp").value = ""
    document.getElementById("description").value = ""
    document.getElementById("countries").value = ""
    document.getElementById("messageCreate").innerHTML = ""
}

function setFormUpdate(data){
    document.getElementById("name").value = data.name
    document.getElementById("area").value = data.area
    document.getElementById("population").value = data.population
    document.getElementById("gdp").value = data.gdp
    document.getElementById("description").value = data.description
    document.getElementById("countries").value = data.country.id
}

function createProvince() {
    let name = $('#name').val()
    let area = $('#area').val()
    let population = $('#population').val()
    let gdp = $('#gdp').val()
    let description =  $('#description').val()
    let countries = $('#countries').val()

    let province = {
        name: name,
        area: area,
        population: population,
        gdp: gdp,
        description: description,
        country: {
            id: countries
        }
    }
    if (area == null || area == isNaN() || area == 0 || population == null || population == isNaN() || population == 0 ||gdp == null || gdp == isNaN() || gdp == 0 ){
        resetFormCreate()
        createFail()
        $("#myModal").modal("hide")

    }else {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            data: JSON.stringify(province),
            url: "http://localhost:8080/Provinces",
            success: function () {
                $('#myModal').modal('hide');
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Tạo mới thành công',
                    showConfirmButton: false,
                    timer: 1000

                })
                setTimeout(getProvinces, 100)
                resetFormCreate()
            },
            error: function (data) {
                if (data.status === 404 || data.status === 415) {
                    createFail()
                } else {
                    createFail()
                }
                resetFormCreate()
                $("#myModal").modal("hide")
            }
        })
    }

    event.preventDefault()
}

function createFail(){
    Swal.fire({
        icon: 'error',
        title: 'Lỗi...',
        text: 'Tạo mới thất bại',
    })
}

function updateFail(){
    Swal.fire({
        icon: 'error',
        title: 'Lỗi...',
        text: 'Cập nhập thất bại',
    })
}

function updateFrom(id){
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/Provinces/Countries",
        success: function (data1) {
            let result = ""
            for (let i = 0; i < data1.length; i++) {
                result += "<option value='" + data1[i].id + "'>" + data1[i].name
                result += "</option>"
            }
            document.getElementById("countries").innerHTML = result;
        }
    })

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/Provinces/" + id,
        success: function (data){
            idProvince = data.id
           setFormUpdate(data)
            // document.getElementById("messageCreate").innerHTML = "";
        }
    })

    document.getElementById("titleFrom").innerHTML = "Chỉnh sửa sản phẩm"
    document.getElementById("button").innerHTML = "Chỉnh sửa"
    document.getElementById("button").setAttribute("onclick", "updatePhone()")
    $('#myModal').modal("show")

}

function updatePhone(){
    let name = $('#name').val()
    let area = $('#area').val()
    let population = $('#population').val()
    let gdp = $('#gdp').val()
    let description =  $('#description').val()
    let countries = $('#countries').val()
    let province = {
        id: idProvince,
        name: name,
        area: area,
        population: population,
        gdp: gdp,
        description: description,
        country: {
            id: countries
        }
    }
    Swal.fire({
        title: 'Bạn có chắc chắn muốn chỉnh sửa',
        showDenyButton: true,
        // showCancelButton: true,
        confirmButtonText: 'Đồng ý',
        denyButtonText: `Hủy bỏ`,
    }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            if (area == null || area == isNaN() || area == 0 || population == null || population == isNaN() || population == 0 ||gdp == null || gdp == isNaN() || gdp == 0 ){
                updateFail()
            }else {
                $.ajax({
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    type: "PUT",
                    data: JSON.stringify(province),
                    url: "http://localhost:8080/Provinces",
                    success: function (data) {
                        setFormUpdate(data)
                        Swal.fire('Chỉnh sửa thành công!', '', 'success')
                        setTimeout(getProvinces, 100)
                    },
                    error: function (data) {
                        if (data.status === 404 || data.status === 415) {
                            setTimeout(updateFail, 500)
                        } else {
                            setTimeout(updateFail, 500)
                        }
                    }
                })
            }

        } else if (result.isDenied) {
            Swal.fire('Hủy bỏ thành công', '', 'info')
        }
    })
    event.preventDefault()
}

function deleteForm(id){
    idProvince = id
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-success',
            cancelButton: 'btn btn-danger'
        },
        buttonsStyling: false
    })

    swalWithBootstrapButtons.fire({
        title: 'Bạn có chắc muốn xóa sản phẩm?',
        text: "Dữ liệu sẽ mất khi đồng ý",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Đồng ý!',
        cancelButtonText: 'Hủy bỏ!',
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            swalWithBootstrapButtons.fire(
                'Xóa thành công!',
                'Sản phẩm đã bị xóa bỏ',
                'success'
            )
            deletePhone(idProvince);
           setTimeout(getProvinces,100)
        } else if (
            /* Read more about handling dismissals below */
            result.dismiss === Swal.DismissReason.cancel
        ) {
            swalWithBootstrapButtons.fire(
                'Hủy bỏ',
                'Sản phẩm hoàn tác',
                'error'
            )
        }
    })
}
function deletePhone(){
    $.ajax({
        type: "DELETE",
        url: "http://localhost:8080/Provinces/" + idProvince,
        success: function() {

        }
    })
    event.preventDefault()
}
function searchListPhone(){
    let search = $('#search').val()
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/Provinces/search/" + search,
        success: function (data) {
            console.log(data)
            displayTable(data)

        }
    })
    event.preventDefault()
}

$('#search').keypress(function(event) {
    if (event.keyCode == 13 || event.which == 13) {
        searchListPhone()
    }
});

function displayBrands(){
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/phones/brands",
        success: function (data) {
            let result = ""
            for (let i = 0; i < data.length; i++) {
                result += "<button style='margin: 10px 10px 0 0' class='btn btn-primary' onClick='findAllByBrands(" + data[i].id + ")'>" + data[i].name + "</button>"
            }
            document.getElementById("get_by_brands").innerHTML = result;
        }
    })

}

function findAllByBrands(id){
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/phones/id_brands/" + id,
        success: function (data) {
            console.log(data)
            displayTable(data)

        }
    })
    event.preventDefault()
}


