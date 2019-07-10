<?php
		require "init.php";
		
		if($con)
		{
			if(
			(isset($_GET['term1']))    ||
			(isset($_GET['term2']))    ||
			(isset($_GET['explain1'])) ||
			(isset($_GET['explain2'])) ||
			(isset($_GET['thumbnail'])) ||
			(isset($_GET['imageurl1']))	||
			(isset($_GET['imageurl2'])) ||
			(isset($_GET['category'])) ||
			(isset($_GET['addedby'])) 
			)
			{
				$term1 = $_GET['term1'];
				$term2 = $_GET['term2'];
				$explain1 = $_GET['explain1'];
				$explain2 =$_GET['explain2'];
				$thumbnail = $_GET['thumbnail'];
				$imageurl1 = $_GET['imageurl1'];
				$imageurl2 = $_GET['imageurl2'];
				$category = $_GET['category'];
				$addedby = $_GET['addedby'];
				
				$insertquery = "insert into distinguish values('','$term1','$term2','$explain1','$explain2','$thumbnail'
				'$imageurl1','$imageurl2' , '$category' ,'$addedby',NULL,'','','');"; 
				$result = mysqli_query($con,$insertquery);
					
				if($result)
				{
					$arr1 = array("success"=>true);
					echo json_encode($arr);
					
					define('API_ACCESS_KEY','AAAAsGiA1Ag:APA91bGAnzO6p6BEm8YaN0Livd9z8v5mj_ZAQtvAYb-BV96nHDFTiQlljGSJ804pAtOdv3ewlnKBJIt3eKzhKJFnq33dXXLZTFYHHg7PFb1lw8AB8OFEEK7jiLkwVeEpUOA_06mYCMyn');
					$fields = array
					(
					'to'  => '/topics/all',
					'notification'=> $msg
							);
							$headers = array
							(
							'Authorization: key=' . API_ACCESS_KEY,
							'Content-Type: application/json'
							);
						$ch = curl_init();
						curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
						curl_setopt( $ch,CURLOPT_POST, true );
						curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
						curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
						curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
						curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
						$result = curl_exec($ch);

				}
				else{
					$arr1 = ("success"=>false);
					echo json_encode($arr);
				}
			}
			else{
				echo json_encode(array("data"=>invalid));
			}
			
		}
		else
		{
						$arr = array("connection"=>"failure");
			echo json_encode($arr);
		}
?>
