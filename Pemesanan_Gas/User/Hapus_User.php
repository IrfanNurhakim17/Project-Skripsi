<?php 
require ("../Database/User.php");

	$user = new User();

if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	$id_user = $_POST['id_user'];

	$user->delete_user($id_user);

}


 ?>