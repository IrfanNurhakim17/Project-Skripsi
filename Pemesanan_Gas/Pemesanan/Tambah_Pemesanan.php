<?php 
error_reporting(E_ALL);
require ("../Database/Pemesanan.php");
	$pemesanan = new Pemesanan();

	$date=date_create("now",timezone_open("Asia/Bangkok"));


if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	if (!empty($_POST['id_pemesanan'])) {
		$id_pemesanan = $_POST['id_pemesanan'];
		$user_id = $_POST['user_id'];
		$barang_id = $_POST['barang_id'];
		$jumlah_pemesanan = $_POST['jumlah_barang'];
		$tanggal_pemesanan = date_format($date,"d-m-Y H:i:sA");
		$biaya_antar = $_POST['biaya_antar'];
		$total_biaya = $_POST['total_biaya'];
		$metode_pembayaran = $_POST['metode_pembayaran'];
		$bukti_transfer = $_POST['bukti_transfer'];
		$status_id = $_POST['status_id'];
		

		$pemesanan->insert_pemesanan($id_pemesanan, $user_id, $barang_id, $jumlah_pemesanan,$tanggal_pemesanan, $biaya_antar, $total_biaya, $metode_pembayaran, $bukti_transfer, $status_id);
	}
	
	if (!empty($_FILES['gambar'])) {
		$gambar = upload();

		echo ($gambar) ? json_encode(array('kode' =>1, 'pesan' => $gambar
			)) : json_encode(array('kode' =>2, 'pesan' => 'Data Gagal Dihapus'));
	}
	
	
}

function upload(){
	$part = "../gambar/";
	$filename = "img".rand(9,9999).".jpg";

	$destinationfile = $part.$filename;
		$test = move_uploaded_file($_FILES['gambar']['tmp_name'],$destinationfile);
	return $filename;
}


 ?>