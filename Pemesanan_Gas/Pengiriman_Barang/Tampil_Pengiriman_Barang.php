<?php 
require ('../Database/Pemesanan.php');//sudah tidak dipakai
	$pemesanan = new Pemesanan();
	$data_pemesanan = $pemesanan->tampil_pengiriman();
	
 ?>