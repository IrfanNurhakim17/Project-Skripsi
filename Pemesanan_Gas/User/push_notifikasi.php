<?php 

function send_notification ($tokens, $message){

	$url = 'https://fcm.googleapis.com/fcm/send';
	$fields = array('registration_ids' => $tokens,
					'data' => $message);

	$headers = array('Authorization:key = AAAA3x7roDs:APA91bFdCg6FfeaJ-dW-XITFcIOQ1MKJ89bN8WgjGOAvZbZZKjDeUn22v8Q4dtMQJ22Mqrdf0gNS9jeJzjxivMiP9Ew1GWuONt77kcrCLGz1B0t-s0BbhWzF1JJSF353fNAOvoYVvr_k','Content-Type: application/json');

	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, $url);
	curl_setopt($ch, CURLOPT_POST, true);
	curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
	curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
	curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

	$result = curl_exec($ch);
	if ($result == FALSE) {
		die('Curl failed: '. curl_error($ch));
	}
	curl_close($ch);
	return $result;
}

$con = mysqli_connect("localhost","root","","pesan_gas_online");
$sql = "SELECT fcm_token, user_id, username, status_id, ket_status FROM ((fcm_info INNER JOIN user ON fcm_info.user_id=user.id_user)
													INNER JOIN status ON fcm_info.status_id=status.id_status)";

$result = mysqli_query($con,$sql);
$tokens = array();
$username = "";
$status = "";
$status_id = "";

if (mysqli_num_rows($result) > 0) {
	
	while ($row = mysqli_fetch_assoc($result)) {
		$tokens[] = $row['fcm_token'];
		$username = $row['username'];
		$status = $row['ket_status'];
		$status_id = $row['status_id'];
	}
}
mysqli_close($con);

$message = array("username" => $username,
	"status" => $status,
	"status_id" => $status_id);
$message_status = send_notification($tokens, $message);
echo $message_status;


 ?>