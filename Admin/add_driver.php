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
?>

<div class="container mt-10">
    <div class="row d-flex justify-content-center">
        <div class="col-lg-12">
            <div class="card ">
                <div class="card-header">
                    <h5>Add Driver</h5>
                </div>
                <div class="card-body ">
                    <form id="form_add_driver" method="post">
                        <div class="form-group">
                            <label >Driver Name</label>
                            <input type="text" name='name'  class="form-control" pattern="/^[a-zA-Z]+$/" required>
                        </div>
                        <div class="form-group">
                            <label >Age</label>
                            <input type="text" name='age' class="form-control" pattern="[0-9]+" maxlength="3" required>
                        </div>
                        <div class="form-group">
                            <label >Driver Username (For Login)</label>
                            <input type="email" name='username'  class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label >Password</label>
                            <input type="text" name='password' class="form-control" required>
                        </div>
                        <div class="form-group">
                            <button type="submit" class='btn btn-primary btn-sm'>Add</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>



<div class="container mt-5">
    <div class="row d-flex justify-content-center">
        <div class="col-lg-12">
            <div class="card ">
                <div class="card-header">
                    <h5>Drivers List</h5>
                </div>
                <div class="card-body" id="table_container">
                    <table class="table table-light" id="table">
                        <thead class="thead-light">
                            <tr>
                                <th>Name</th>
                                <th>Age</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php
                            $get_drivers=mysqli_query($db,"SELECT * FROM `driver`");
                            if(mysqli_num_rows($get_drivers) > 0){
                                while($data=mysqli_fetch_assoc($get_drivers)){
                            ?>
                                <tr>
                                    <td> <?php echo $data['name'] ?> </td>
                                    <td> <?php echo $data['age'] ?> </td>
                                    <td> <?php echo $data['username'] ?> </td>
                                    <td> <?php echo $data['password'] ?> </td>
                                    <td> 
                                    <button class='btn btn-sm btn-danger' onclick='delete_driver("<?php echo $data['id'] ?>")'>Delete</button>
                                    </td>
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

