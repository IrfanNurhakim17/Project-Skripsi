<?php 
require ("../Database/User.php");

	$user = new User();

if ($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	$id_user = $_POST['id_user'];
	$username = $_POST['username'];
	$password = $_POST['password'];
	$nama = $_POST['nama'];
	$alamat = $_POST['alamat'];
	$no_telp = $_POST['no_telp'];
	$role_id = $_POST['role_id'];

$dsn = "mysql:host=localhost;dbname=pesan_gas_online";
$pdo = new PDO($dsn, 'root', '');

$sql = "SELECT * FROM user WHERE username = ?";

$stmt = $pdo->prepare($sql);
$stmt->execute([$username]);

if ($row = $stmt->fetch()) {
		
			echo json_encode(array('kode' =>3, 'pesan' => 'Username Sudah Ada'
			));

}
else{
	$user->insert_user($id_user, $username, $password, $nama, $alamat, $no_telp, $role_id);
}

	


}


 ?>