<?php
session_start();
require_once "../db.php";

if(isset($_POST['action']) && $_POST['action'] != null){
    if($_POST['action'] == "login"){
        $username=mysqli_real_escape_string($db,$_POST['username']);
        $password=mysqli_real_escape_string($db,$_POST['password']);
        $query=mysqli_query($db,"SELECT * FROM `admin`");
        $flag=0;
        if(mysqli_num_rows($query) >0){
            while($data=mysqli_fetch_assoc($query)){
                if($data['username'] == $username && $data['pasword'] == $password){
                    $flag++;
                }
            }
        }
        if($flag == 0){
            $result=array(
                "error"=>1,
                "msg"=>"Invalid credentials !!"
            );
            echo json_encode($result);
        }else{
            $_SESSION['admin_login']=true;
            $_SESSION['adminid']=base64_decode($username);
            $result=array(
                "error"=>0,
                "msg"=>"Login successfully !!"
            );
            echo json_encode($result);
        }

    }else if($_POST['action'] == "add_driver"){
        $name=$_POST['name'];
        $age=$_POST['age'];
        $username=$_POST['username'];
        $password=$_POST['password'];
        if(mysqli_query($db,"INSERT INTO `driver` (name,age,username,password) VALUES ('$name','$age','$username','$password')")){
            $result=array(
                "error"=>0,
                "msg"=>"Driver added !!"
            );
            echo json_encode($result);
        }else{
            $result=array(
                "error"=>1,
                "msg"=>"Failed to add the driver !!"
            );
            echo json_encode($result);
        }
    }else if($_POST['action'] == "delete_driver"){
        $id=$_POST['id'];
        if(mysqli_query($db,"DELETE FROM `driver` WHERE id='$id'")){
            $result=array(
                "error"=>0,
                "msg"=>"Driver deleted successfully !!"
            );
            echo json_encode($result);
        }else{
            $result=array(
                "error"=>1,
                "msg"=>"Failed to delete the driver !!"
            );
            echo json_encode($result);
        }
    }
    else if($_POST['action'] == "add_bus_route"){
        $bus_number=$_POST['bus_number'];
        $source=$_POST['source'];
        $destination=$_POST['destination'];
        
        if(mysqli_query($db,"INSERT INTO `routes` (bus_number,source,destination) VALUES ('$bus_number','$source','$destination') ")){
            $result=array(
                "error"=>0,
                "msg"=>"Route added !!"
            );
            echo json_encode($result);
        }else{
            
            $result=array(
                "error"=>1,
                "msg"=>"Failed to add the route !!"
            );
            echo json_encode($result);
        }
    }
    else if($_POST['action'] == "delete_route"){
        $id=$_POST['id'];
        if(mysqli_query($db,"DELETE FROM `routes` WHERE id='$id'")){
            $result=array(
                "error"=>0,
                "msg"=>"Route deleted successfully !!"
            );
            echo json_encode($result);
        }else{
            $result=array(
                "error"=>1,
                "msg"=>"Failed to delete the route !!"
            );
            echo json_encode($result);
        }
    }
}

?>