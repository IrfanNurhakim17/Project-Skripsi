<?php 
require ("../Database/Barang.php");

	$barang = new Barang();

if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	$id_barang = $_POST['id_barang'];
	$nama_barang = $_POST['nama_barang'];
	$jumlah_barang = $_POST['jumlah_barang'];
	$harga_barang = $_POST['harga_barang'];

	$barang->insert_barang($id_barang, $nama_barang, $jumlah_barang, $harga_barang);


}


 ?>