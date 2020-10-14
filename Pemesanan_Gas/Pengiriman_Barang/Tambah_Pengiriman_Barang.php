<?php 
require ("../Database/Pengiriman_Barang.php");

	$pengiriman = new Pengiriman_Barang();

if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	$id_kirim = $_POST['id_kirim'];
	$pemesanan_id = $_POST['pemesanan_id'];
	$user_id = $_POST['user_id'];
	$barang_id = $_POST['barang_id'];
	$status_pengiriman = $_POST['status_pengiriman'];

	$pengiriman->insert_pengiriman_barang($id_kirim, $pemesanan_id, $user_id, $barang_id, $status_pengiriman);


}


 ?>