<?php 
require ("../Database/Pemesanan.php");

	$pemesanan = new Pemesanan();

if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	$id_pemesanan = $_POST['id_pemesanan'];

	$pemesanan->delete_pemesanan($id_pemesanan);

}


 ?>