<?php
 require_once "header.php";
 require_once "nav.php";
 if(!isset($_SESSION['admin_login']) && !(isset($_SESSION['adminid']))){
    if(!$_SESSION['admin_login']){
        echo "Session over !!" ;
    }else if($_SESSION['adminid'] == null){
        echo "Session over !!";
    }
    header("Location:index.php");
}
    $get_data=mysqli_query($db,"SELECT d.name,d.age,d.username,l.time FROM driver d,logs l WHERE d.id=l.driverid");
    
?>


<div class="container mt-10">
    <div class="row d-flex justify-content-center">
        <div class="col-lg-12">
            <div class="card ">
                <div class="card-header">
                    <h5>Drivers Logs</h5>
                </div>
                <div class="card-body" id="table_container">
                    
                    <table class="table table-light"  id="table">
                        <thead class="thead-light">
                            <tr>
                                <th>Driver Name</th>
                                <th>Age</th>
                                <th>Username</th>
                                <th>Time of Login</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php
                            
                            if(mysqli_num_rows($get_data) > 0){
                                while($data=mysqli_fetch_assoc($get_data)){
                            ?>
                                <tr>
                                    <td> <?php echo $data['name'] ?> </td>
                                    <td> <?php echo $data['age'] ?> </td>
                                    <td> <?php echo $data['username'] ?> </td>
                                    <td> <?php echo $data['time'] ?> </td>
                                    
                                </tr>
                            <?php
                                    }
                                }
                            ?>
                        </tbody>
                    </table>
                    
                </div>
            </div>
        </div>
    </div>
</div>


<?php require_once "footer.php" ?>

