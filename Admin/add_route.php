<?php require_once "header.php";
require_once "nav.php";
if(!isset($_SESSION['admin_login']) && !(isset($_SESSION['adminid']))){
    if(!$_SESSION['admin_login']){
        echo "Session over !!" ;
    }else if($_SESSION['adminid'] == null){
        echo "Session over !!";
    }
    header("Location:index.php");
} ?>

<div class="container mt-10">
    <div class="row d-flex justify-content-center">
        <div class="col-lg-12">
            <div class="card ">
                <div class="card-header">
                    <h5>Add Bus Routes</h5>
                </div>
                <div class="card-body ">
                    <form id="form_add_bus_route" method="post">
                        <div class="form-group">
                            <label >Bus Number</label>
                            <input type="text" name='bus_number' pattern="[0-9]+" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label >Source</label>
                            <input type="text" name='source' pattern="/^[a-zA-Z]+$/"  class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label >Destination</label>
                            <input type="text" name='destination'  class="form-control" required>
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
                    <h5>Bus Routes List</h5>
                </div>
                <div class="card-body" id="table_container">
                    <table class="table table-light" id="table">
                        <thead class="thead-light">
                            <tr>
                                <th>Bus Number</th>
                                <th>Source</th>
                                <th>From</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php
                            $get_drivers=mysqli_query($db,"SELECT * FROM `routes`");
                            if(mysqli_num_rows($get_drivers) > 0){
                                while($data=mysqli_fetch_assoc($get_drivers)){
                            ?>
                                <tr>
                                    <td> <?php echo $data['bus_number'] ?> </td>
                                    <td> <?php echo $data['source'] ?> </td>
                                    <td> <?php echo $data['destination'] ?> </td>
                                    
                                    <td> 
                                    <button class='btn btn-sm btn-danger' onclick='delete_route("<?php echo $data['id'] ?>")'>Delete</button>
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

