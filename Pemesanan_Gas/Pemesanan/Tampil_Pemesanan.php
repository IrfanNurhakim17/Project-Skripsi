<?php 
require ('../Database/Pemesanan.php');
	$pemesanan = new Pemesanan();

if ($_SERVER['REQUEST_METHOD'] == 'POST') //get pesanan,pengiriman,riwayat
{
	$key = $_POST['key'];
	$ket_request = $_POST['ket_request'];
	$data_pemesanan = $pemesanan->tampil_pemesanan($key,$ket_request);
}
	
 ?>