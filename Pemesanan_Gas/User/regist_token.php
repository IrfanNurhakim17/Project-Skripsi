<?php 

require ("../Database/FCM.php");
	$fcm = new FCM();



	if (isset($_POST['Token'])) {


		$token = $_POST['Token'];
		$user_id = $_POST['user_id'];

		$fcm->insert_token($token,$user_id);

	}


 ?>