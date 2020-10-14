<?php 

class Barang{

	private $pdo;
	function __construct(){ //underskor 2 KALI
		try{
			$this->pdo = new PDO('mysql:host=localhost;dbname=pesan_gas_online', 'root', '');
		}catch(PDOException $e){
			echo $e;
		}
	}

	public function insert_barang($id_barang, $nama_barang, $jumlah_barang, $harga_barang){
		$sql = "INSERT INTO barang(id_barang, nama_barang, jumlah_barang, harga_barang)
			VALUES(?,?,?,?)";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$id_barang, $nama_barang, $jumlah_barang, $harga_barang]);
		echo ($stmt) ? json_encode(array('kode' =>1, 'pesan' => 'Berhasil Menambahkan Data'
			)) : json_encode(array('kode' =>2, 'pesan' => 'Data Gagal Ditambahkan'));
	}

	public function update_barang($id_barang, $nama_barang, $jumlah_barang, $harga_barang){
		$sql = "UPDATE barang
			SET nama_barang =?, jumlah_barang =?, harga_barang =? WHERE id_barang =?";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$nama_barang, $jumlah_barang, $harga_barang, $id_barang]);

		echo ($stmt) ? json_encode(array('kode' =>1, 'pesan' => 'Berhasil Merubah Data'
			)) : json_encode(array('kode' =>2, 'pesan' => 'Data Gagal Dirubah'));
	}

	public function tampil_barang(){
		$sql = "SELECT * FROM barang";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute();
		$array = array();
		while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
			$array[] = $row;
		}
		if (empty($array)) {
			echo json_encode(array('kode' =>0, 'result' => $array));	 
		}
		else{
			echo ($stmt) ? json_encode(array('kode' =>1, 'result' => $array
			)) : json_encode(array('kode' =>0, 'pesan' => 'data tidak ditemukan'));	
		}
		
	}

	public function tampil_barang_pelanggan(){
		$sql = "SELECT * FROM barang";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute();
		$array = array();
		while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
			echo ($stmt) ? json_encode(array('id_barang' =>$row['id_barang'], 'nama_barang' => $row['nama_barang'], 'jumlah_barang' =>$row['jumlah_barang'], 'harga_barang' => $row['harga_barang']
			)) : json_encode(array('kode' =>0, 'pesan' => 'data tidak ditemukan'));	
		}
		
	}

	public function delete_barang($id_barang){
		$sql = "DELETE FROM barang WHERE id_barang=?";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$id_barang]);

		echo ($stmt) ? json_encode(array('kode' =>1, 'pesan' => 'Berhasil Menghapus Data'
			)) : json_encode(array('kode' =>2, 'pesan' => 'Data Gagal Dihapus'));
	}
}

 ?>