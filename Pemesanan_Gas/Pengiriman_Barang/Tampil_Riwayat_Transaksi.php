<?php 
require ('../Database/Pemesanan.php');//sudah tidak di pakai
	$pemesanan = new Pemesanan();
	$data_pemesanan = $pemesanan->tampil_riwayat_transaksi();
	
 ?>