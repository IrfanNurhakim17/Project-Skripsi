<?php 
require ("../Database/Pemesanan.php");

	$pemesanan = new Pemesanan();

if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	$id_pemesanan = $_POST['id_pemesanan'];
	$user_id = $_POST['user_id'];
	$barang_id = $_POST['barang_id'];
	$jumlah_pemesanan = $_POST['jumlah_barang'];
	$tanggal_pemesanan = $_POST['tanggal_pemesanan'];
	$biaya_antar = $_POST['biaya_antar'];
	$total_biaya = $_POST['total_biaya'];
	$metode_pembayaran = $_POST['metode_pembayaran'];
	$bukti_transfer = $_POST['bukti_transfer'];
	$status_id = $_POST['status_id'];


	$pemesanan->update_pemesanan($id_pemesanan, $user_id, $barang_id, $jumlah_pemesanan,$tanggal_pemesanan, $biaya_antar,$total_biaya,$metode_pembayaran, $bukti_transfer, $status_id);

}


 ?>