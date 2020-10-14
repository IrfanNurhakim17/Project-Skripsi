<?php 

class Pemesanan{

	private $pdo;
	function __construct(){ //underskor 2 KALI
		try{
			$this->pdo = new PDO('mysql:host=localhost;dbname=pesan_gas_online', 'root', '');
		}catch(PDOException $e){
			echo $e;
		}
	}

	public function insert_pemesanan($id_pemesanan, $user_id, $barang_id, $jumlah_pemesanan, $tanggal_pemesanan, $biaya_antar, $total_biaya, $metode_pembayaran, $bukti_transfer, $status_id){
		$sql = "INSERT INTO pemesanan (id_pemesanan, user_id, barang_id, jumlah_pemesanan, tanggal_pemesanan, biaya_antar, total_biaya, metode_pembayaran, bukti_transfer, status_id)
			VALUES(?,?,?,?,?,?,?,?,?,?)";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$id_pemesanan, $user_id, $barang_id, $jumlah_pemesanan, $tanggal_pemesanan, $biaya_antar, $total_biaya, $metode_pembayaran , $bukti_transfer, $status_id]);

		echo ($stmt) ? json_encode(array('kode' =>1, 'pesan' => 'Berhasil Menambahkan Data'
			)) : json_encode(array('kode' =>2, 'pesan' => 'Data Gagal Ditambahkan'));
	}

	public function update_pemesanan($id_pemesanan, $user_id, $barang_id, $jumlah_pemesanan, $tanggal_pemesanan, $biaya_antar, $total_biaya, $metode_pembayaran, $bukti_transfer, $status_id){
		$sql = "UPDATE pemesanan
			SET user_id =?, barang_id =?, jumlah_pemesanan =?, tanggal_pemesanan=?, biaya_antar=?, total_biaya=?, metode_pembayaran=?, bukti_transfer=?, status_id=? WHERE id_pemesanan =?";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$user_id, $barang_id, $jumlah_pemesanan, $tanggal_pemesanan, $biaya_antar, $total_biaya, $metode_pembayaran, $bukti_transfer, $status_id, $id_pemesanan]);
		echo ($stmt) ? json_encode(array('kode' =>1, 'pesan' => 'Berhasil Merubah Data'
			)) : json_encode(array('kode' =>2, 'pesan' => 'Data Gagal Ditambahkan'));
	}

	public function tampil_pemesanan($key,$ket_request){ //get pesanan,pengiriman,riwayat
		$status_id = "";
		if (!empty($key)) {
			$sql = "SELECT id_pemesanan, user_id, barang_id, nama, no_telp, nama_barang, alamat, jumlah_pemesanan, tanggal_pemesanan, biaya_antar, total_biaya, metode_pembayaran, bukti_transfer, ket_status, status_id
		FROM (((pemesanan INNER JOIN barang ON pemesanan.barang_id=barang.id_barang)
		INNER JOIN user ON pemesanan.user_id=user.id_user)
		INNER JOIN status ON pemesanan.status_id=status.id_status) WHERE nama LIKE ?";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$key]);
		$array = array();
		while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
			

			if ($ket_request == "req_pesanan") {

				if ($row['status_id'] == 1) {
				$array[] = $row;
				$status_id = $row['status_id'];
				}

			}

			if ($ket_request == "req_pengiriman") {
				
				if ($row['status_id'] != 1 && $row['status_id'] != 5 && $row['status_id'] != 6 && $row['status_id'] != 7) {
				$array[] = $row;
				$status_id = $row['status_id'];
				}
			}

			if ($ket_request == "req_riwayat") {

				if ($row['status_id'] == 5 || $row['status_id'] == 6 || $row['status_id'] == 7) {
				$array[] = $row;
				$status_id = $row['status_id'];
				}
			}
		}

		echo ($stmt) ? json_encode(array('kode' =>$status_id, 'result' => $array
			)) : json_encode(array('kode' =>0, 'pesan' => 'data tidak ditemukan'));
		}

		else
		{

			if ($ket_request == "req_riwayat") {

				$sql = "SELECT id_pemesanan, user_id, barang_id, nama, no_telp, nama_barang, alamat, jumlah_pemesanan, tanggal_pemesanan, biaya_antar, total_biaya, metode_pembayaran, bukti_transfer, ket_status, status_id
					FROM (((pemesanan INNER JOIN barang ON pemesanan.barang_id=barang.id_barang)
					INNER JOIN user ON pemesanan.user_id=user.id_user)
					INNER JOIN status ON pemesanan.status_id=status.id_status) ORDER BY id_pemesanan DESC";
					$stmt = $this->pdo->prepare($sql);
					$stmt->execute();
					$array = array();
					while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
						if ($ket_request == "req_pesanan") {

							if ($row['status_id'] == 1) {
							$array[] = $row;
							$status_id = $row['status_id'];
							}

						}

						if ($ket_request == "req_pengiriman") {
				
							if ($row['status_id'] != 1 && $row['status_id'] != 5 && $row['status_id'] != 6 && $row['status_id'] != 7) {
							$array[] = $row;
							$status_id = $row['status_id'];
							}
						}

						if ($ket_request == "req_riwayat") {
				
							if ($row['status_id'] == 5 || $row['status_id'] == 6 || $row['status_id'] == 7) {
							$array[] = $row;
							$status_id = $row['status_id'];
							}
						}
		
					}

				}

			else{
					$sql = "SELECT id_pemesanan, user_id, barang_id, nama, no_telp, nama_barang, alamat, jumlah_pemesanan, tanggal_pemesanan, biaya_antar, total_biaya, metode_pembayaran, bukti_transfer, ket_status, status_id
					FROM (((pemesanan INNER JOIN barang ON pemesanan.barang_id=barang.id_barang)
					INNER JOIN user ON pemesanan.user_id=user.id_user)
					INNER JOIN status ON pemesanan.status_id=status.id_status)ORDER BY id_pemesanan ASC";
					$stmt = $this->pdo->prepare($sql);
					$stmt->execute();
					$array = array();
					while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {

						if ($ket_request == "req_pesanan") {

							if ($row['status_id'] == 1) {
							$array[] = $row;
							$status_id = $row['status_id'];
							}

						}

						if ($ket_request == "req_pengiriman") {
				
							if ($row['status_id'] != 1 && $row['status_id'] != 5 && $row['status_id'] != 6 && $row['status_id'] != 7) {
						
							$array[] = $row;
							$status_id = $row['status_id'];

							}
						}

						if ($ket_request == "req_riwayat") {
				
							if ($row['status_id'] == 5 || $row['status_id'] == 6|| $row['status_id'] == 7) {
							$array[] = $row;
							$status_id = $row['status_id'];
							}
						}
		
					}
				}

		
		echo ($stmt) ? json_encode(array('kode' =>$status_id, 'result' => $array
			)) : json_encode(array('kode' =>0, 'pesan' => 'data tidak ditemukan'));
		}
		
	}

	public function tampil_pemesanan_pelanggan($user_id, $ket_request){
		$sql = "SELECT id_pemesanan, user_id, barang_id, nama, no_telp, nama_barang, alamat, jumlah_pemesanan, tanggal_pemesanan, biaya_antar, total_biaya, metode_pembayaran, ket_status, bukti_transfer, status_id
		FROM (((pemesanan INNER JOIN barang ON pemesanan.barang_id=barang.id_barang)
		INNER JOIN user ON pemesanan.user_id=user.id_user)
		INNER JOIN status ON pemesanan.status_id=status.id_status) WHERE user_id = ? ORDER BY id_pemesanan DESC";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$user_id]);
		$array = array();
		while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
			if ($ket_request == "detail_pemesanan") {
				if ($row['status_id'] >= 1 && $row['status_id'] <= 4) {
					echo ($stmt) ? json_encode(array('id_pemesanan' =>$row['id_pemesanan'], 
						'user_id' => $row['user_id'],
						'barang_id' =>$row['barang_id'],
						'nama' => $row['nama'],
						'no_telp' =>$row['no_telp'],
						'nama_barang' => $row['nama_barang'],
						'alamat' => $row['alamat'],
						'jumlah_pemesanan' => $row['jumlah_pemesanan'],
						'tanggal_pemesanan' =>$row['tanggal_pemesanan'],
						'biaya_antar' => $row['biaya_antar'],
						'total_biaya' =>$row['total_biaya'],
						'metode_pembayaran' =>$row['metode_pembayaran'],
						'bukti_transfer' =>$row['bukti_transfer'],
						'ket_status' => $row['ket_status'],
						'status_id' => $row['status_id'])) 
					: json_encode(array('kode' =>0, 'pesan' => 'data tidak ditemukan'));		
				}
			}
			if ($ket_request == "riwayat_transaksi") {
				if ($row['status_id'] == 5 || $row['status_id'] == 6|| $row['status_id'] == 7) {
					$array[] = $row;		
				}
			}
		}
		echo ($stmt) ? json_encode(array('kode' =>1, 'result' => $array
			)) : json_encode(array('kode' =>0, 'pesan' => 'data tidak ditemukan'));
	}

	public function delete_pemesanan($id_pemesanan){
		$sql = "DELETE FROM pemesanan WHERE id_pemesanan=?";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$id_pemesanan]);

		echo ($stmt) ? json_encode(array('kode' =>1, 'pesan' => 'Berhasil Menghapus Data'
			)) : json_encode(array('kode' =>2, 'pesan' => 'Data Gagal Dihapus'));
	}
}

function upload_desk(){
		$namaFile = $_FILES['gambar']['name'];
		$ukuranFile= $_FILES['gambar']['size'];
		$error = $_FILES['gambar']['error'];
		$tmpName= $_FILES['gambar']['tmp_name'];

		//cek apakah gambar yang diupload
		if( $error === 4){
			echo "
				<script>
					alert('Pilih gambar dahulu!');
				</script>
			";
			return false;
		}

		$ekstensiGambarValid = ['jpg','jpeg','png'];
		$ekstensiGambar = explode('.',$namaFile);
		$ekstensiGambar = strtolower(end($ekstensiGambar));
		if(!in_array($ekstensiGambar, $ekstensiGambarValid)){
			echo"
				<script>
					alert('Anda bukan upload gambar!');
				</script>
			";
			return false;
		}

		//cek jika ukuran terlalu Besar
		if($ukuranFile > 1000000){
			echo"
				<script>
					alert('Ukuran gambar terlalu besar!');
				</script>
			";
		}

		$namaFileBaru = uniqid();
     	$namaFileBaru .= '.';
     	$namaFileBaru .= $ekstensiGambar;

		// setelah lolos cek gambar di upload
		move_uploaded_file($tmpName,'../gambar/'. $namaFileBaru);
		return $namaFileBaru;
	}



 ?>