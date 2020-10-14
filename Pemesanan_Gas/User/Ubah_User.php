<?php 
require ("../Database/User.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	$id_user = $_POST['id_user'];
	$username = $_POST['username'];
	$password = $_POST['password'];
	$nama = $_POST['nama'];
	$alamat = $_POST['alamat'];
	$no_telp = $_POST['no_telp'];
	$role_id = $_POST['role_id'];

$plgn = new User();
$plgn->Update_User($id_user, $username, $password, $nama, $alamat, $no_telp, $role_id);

}

 ?>