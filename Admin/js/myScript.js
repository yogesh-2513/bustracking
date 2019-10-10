"use strict";

function ajaxReq(formdata,callback){
    $.ajax({
        url:"handler/ajaxhandler.php",
        type:"POST",
        processData:false,
        contentType:false,
        data:formdata,
        success:function(response){
            console.log(response)
            var json=JSON.parse(response)
            alert(json['msg'])
            if(json['error'] == 0){
                callback();
            }
        }
    });
}


$("#form_login").on('submit',function(event){
    event.preventDefault();
    var formdata=new FormData(this);
    formdata.append("action","login");
    ajaxReq(formdata,login);
})

function login()
{
    location.replace("add_driver.php");
}

$("#form_add_driver").on('submit',function(event){
    event.preventDefault();
    var formdata=new FormData(this);
    formdata.append("action","add_driver");
    ajaxReq(formdata,add_driver);
})

function add_driver(){
    $("#table_container").load("add_driver.php #table");
}

function delete_driver(id){
    var formdata=new FormData();
    formdata.append('id',id);
    formdata.append("action","delete_driver");
    ajaxReq(formdata,add_driver);
}


$("#form_add_bus_route").on('submit',function(event){
    event.preventDefault();
    var formdata=new FormData(this);
    formdata.append("action","add_bus_route");
    ajaxReq(formdata,add_bus_route);
})

function delete_route(id){
    var formdata=new FormData();
    formdata.append('id',id);
    formdata.append("action","delete_route");
    ajaxReq(formdata,add_bus_route);
}

function add_bus_route(){
    $("#table_container").load("add_route.php #table");
}