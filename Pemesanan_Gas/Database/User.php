<?php 

class User{

	private $pdo;
	function __construct(){ //underskor 2 KALI
		try{
			$this->pdo = new PDO('mysql:host=localhost;dbname=pesan_gas_online', 'root', '');
		}catch(PDOException $e){
			echo $e;
		}
	}

	public function insert_user($id_user, $username, $password, $nama, $alamat, $no_telp, $role_id){
		$sql = "INSERT INTO user(id_user, username, password, nama, alamat, no_telp, role_id)
			VALUES(?,?,?,?,?,?,?)";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$id_user, $username, $password, $nama, $alamat, $no_telp, $role_id]);
		echo ($stmt) ? json_encode(array('kode' =>1, 'pesan' => 'Berhasil Menambahkan Data'
			)) : json_encode(array('kode' =>2, 'pesan' => 'Data Gagal Ditambahkan'));
	}

	public function update_user($id_user, $username, $password, $nama, $alamat, $no_telp, $role_id){
		$sql = "UPDATE user
			SET username =?, password =?, nama =?, alamat =?, no_telp=?, role_id=? WHERE id_user =?";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$username, $password, $nama, $alamat, $no_telp, $role_id, $id_user]);
		echo ($stmt) ? json_encode(array('kode' =>1, 'pesan' => 'Berhasil Menambahkan Data'
			)) : json_encode(array('kode' =>2, 'pesan' => 'Data Gagal Ditambahkan'));
	}

	public function tampil_user(){
		$sql = "SELECT * FROM user";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute();
		$array = array();
		while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
			if ($row['role_id'] == 2) {
				$array[] = $row;
			}
		}
		if (empty($array)) {
			echo json_encode(array('kode' =>0, 'result' => $array));	 
		}
		else{
			echo ($stmt) ? json_encode(array('kode' =>1, 'result' => $array
			)) : json_encode(array('kode' =>0, 'pesan' => 'data tidak ditemukan'));	
		}
		
	}

	public function tampil_profil($username){
		$sql = "SELECT * FROM user WHERE username = ?";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$username]);
		$array = array();
		while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
			echo ($stmt) ? json_encode(array('id_user' =>$row['id_user'], 'username' => $row['username'], 'password' =>$row['password'], 'nama' => $row['nama'], 'alamat' =>$row['alamat'], 'no_telp' => $row['no_telp'], 'role_id' => $row['role_id']
			)) : json_encode(array('kode' =>0, 'pesan' => 'data tidak ditemukan'));	
		}
		
	}

	public function delete_user($id_user){
		$sql = "DELETE FROM user WHERE id_user = ?";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$id_user]);

		echo ($stmt) ? json_encode(array('kode' =>1, 'pesan' => 'Berhasil Menghapus Data'
			)) : json_encode(array('kode' =>2, 'pesan' => 'Data Gagal Dihapus'));
	}
}

 ?>