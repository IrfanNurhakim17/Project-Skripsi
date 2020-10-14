<?php 

if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	$username = $_POST['username'];
	$password = $_POST['password'];

$dsn = "mysql:host=localhost;dbname=pesan_gas_online";
$pdo = new PDO($dsn, 'root', '');

$sql = "SELECT * FROM user WHERE username = ?";

$stmt = $pdo->prepare($sql);
$stmt->execute([$username]);

if ($row = $stmt->fetch()) {

	if ($password == $row['password']) {
		
			echo ($row['role_id'] == 1) ? json_encode(array('kode' =>1, 'pesan' => 'Login Sebagai Admin'
			)) : json_encode(array('kode' =>2, 'pesan' => 'Login Sebagai Pelanggan'));
	}
	else{
		echo json_encode(array('kode' =>3, 'pesan' => 'Password Salah'));
	}
}
else{
	echo json_encode(array('kode' =>101, 'pesan' => 'Username Tidak Ada'
			));
}

}


 ?>