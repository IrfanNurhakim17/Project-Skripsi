<?php 

class FCM{

	private $pdo;
	function __construct(){ //underskor 2 KALI
		try{
			$this->pdo = new PDO('mysql:host=localhost;dbname=pesan_gas_online', 'root', '');
		}catch(PDOException $e){
			echo $e;
		}
	}

	public function insert_token($token,$user_id){
		$sql = "INSERT INTO fcm_info (fcm_token,user_id)
			VALUES(?,?)";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$token,$user_id]);

		echo ($stmt) ? json_encode(array('kode' =>1, 'pesan' => 'Berhasil Menambahkan Data'
			)) : json_encode(array('kode' =>2, 'pesan' => 'Data Gagal Ditambahkan'));
	}

	public function update_token($token,$user_id,$status_id){
		$sql = "UPDATE fcm_info
			SET user_id=?, status_id=? WHERE fcm_token =?";
		$stmt = $this->pdo->prepare($sql);
		$stmt->execute([$user_id, $fcm_token, $status_id]);
		echo ($stmt) ? json_encode(array('kode' =>1, 'pesan' => 'Berhasil Menambahkan Data'
			)) : json_encode(array('kode' =>2, 'pesan' => 'Data Gagal Ditambahkan'));
	}

	
}

 ?>