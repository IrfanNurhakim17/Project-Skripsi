<?php 
require ('../Database/Pemesanan.php');
	$pemesanan = new Pemesanan();

if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	$user_id = $_POST['user_id'];
	$ket_request = $_POST['ket_request'];

	$data_pemesanan = $pemesanan->tampil_pemesanan_pelanggan($user_id, $ket_request);
}
 ?>