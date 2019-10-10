<?php
require_once "db.php";
if(isset($_POST['action'])){
    if($_POST['action'] == "login"){
        $username=$_POST['username'];
        $password=$_POST['password'];
        $data=mysqli_query($db,"SELECT * FROM `driver`");
        $driver_data=array();
        $flag=0;
        if(mysqli_num_rows($data) >0){
            while($res=mysqli_fetch_assoc($data)){
                if($res['username'] == $username && $res['password'] == $password){
                    array_push($driver_data,$res['id']);
                    array_push($driver_data,$res['name']);
                    array_push($driver_data,$res['age']);
                    $flag++;
                }
            }
        }
        if($flag !=0){
            $result=array(
                "error"=>0,
                "msg"=>"Login successfully !!",
                "data"=>$driver_data
            );
            echo json_encode($result);
        }else{
            $result=array(
                "error"=>1,
                "msg"=>"Invalid credentials !!"
            );
            echo json_encode($result);
        }
    }else if($_POST['action'] == "get_routes"){
        
        $data=mysqli_query($db,"SELECT * FROM `routes`");
        $result=array();
        if(mysqli_num_rows($data) >0){
            while($res=mysqli_fetch_assoc($data)){
                array_push($result,$res);
            }
        }
        echo json_encode($result);
    }
    else if($_POST['action'] == "update_location"){
        $latitude=$_POST['latitude'];
        $longtitude=$_POST['longtitude'];
        $bus_number=$_POST['busID'];
        $query="UPDATE `routes` SET latitude='$latitude', langtitude='$longtitude' WHERE id=$bus_number";
        if(mysqli_query($db,$query)){
            $result=array(
                "error"=>0,
                "query"=>$query,
                "msg"=>"updated !!"
            );
            echo json_encode($result);
        }else{
            $result=array(
                "error"=>0,
                "msg"=>"Failed !!"
            );
            echo json_encode($result);
        }

    }else if($_POST['action'] == "get_route"){
        $id=$_POST['id'];
        $data=mysqli_query($db,"SELECT * FROM `routes` WHERE id='$id'");
        if(mysqli_num_rows($data) >0){
            while($row=mysqli_fetch_assoc($data)){
                $result=array(
                    'lat'=>$row['latitude'],
                    'lan'=>$row['langtitude'],
                    'error'=>0
                );
                echo json_encode($result);
            }
        }
    }

}

?>

