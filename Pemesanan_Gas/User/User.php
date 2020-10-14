<?php 

class User{

	private $pdo;
	function __construct(){ //underskor 2 KALI
		try{
			$this->pdo = new PDO('mysql:host=localhost;dbname=skrw8128_pesan_gas_online', 'root', '');
		}catch(PDOException $e){
			echo $e;
		}
	}

	public function Insert_User($username, $password, $nama, $alamat, $no_telp, $role){
		$sql = "INSERT INTO user(id_user, password, nama, alamat, no_telp, role)
			VALUES(?,?,?,?,?,?)";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$username, $password, $nama, $alamat, $no_telp, $role]);
		echo ($stmt) ? json_encode(array('kode' =>1, 'pesan' => 'Berhasil Menambahkan Data'
			)) : json_encode(array('kode' =>2, 'pesan' => 'Data Gagal Ditambahkan'));
	}

	public function Update_User($username, $password, $nama, $alamat, $no_telp, $role){
		$sql = "UPDATE user
			SET password =?, nama =?, alamat =?, no_telp=?, role=? WHERE id_user =?";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$password, $nama, $alamat, $no_telp, $role, $username]);
	}
}

 ?>