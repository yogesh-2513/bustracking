<?php require_once "header.php" ?>

<div class="container mt-10">
    <div class="row d-flex justify-content-center">
        <div class="col-lg-6">
            <div class="card ">
                <div class="card-header">
                    <h5>Admin Login</h5>
                </div>
                <div class="card-body ">
                    <form id="form_login" method="post">
                        <div class="form-group">
                            <label >Username</label>
                            <input type="email" name='username'  class="form-control">
                        </div>
                        <div class="form-group">
                            <label >Password</label>
                            <input type="text" name='password' class="form-control">
                        </div>
                        <div class="form-group">
                            <button type="submit" class='btn btn-primary btn-sm'>Submit</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<?php require_once "footer.php" ?>

