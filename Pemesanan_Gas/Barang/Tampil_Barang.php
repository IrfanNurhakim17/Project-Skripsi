<?php 
require ('../Database/Barang.php');
	$barang = new Barang();
	$data_barang = $barang->tampil_barang();
 ?>