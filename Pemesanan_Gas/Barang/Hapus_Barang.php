<?php 
require ("../Database/Barang.php");

	$barang = new Barang();

if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	$id_barang = $_POST['id_barang'];

	$barang->delete_barang($id_barang);

}


 ?>