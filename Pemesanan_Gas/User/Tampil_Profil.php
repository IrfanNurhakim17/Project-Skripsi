<?php 
require ("../Database/User.php");

	$user = new User();

if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	$username = $_POST['username'];

	$user->tampil_profil($username);

}


 ?>