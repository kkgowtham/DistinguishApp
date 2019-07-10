<?php

	require "init.php";
	
	if($con)
	{
			
			if(isset($_GET['email']) || isset($_GET['comment']) || isset($_GET['name'] || isset($_GET['imageurl'])) 
			{
				$name = $_GET['name'];
				$imaegeurl = $_GET['imageurl'];
				$email = $_GET['email'];
				$comment = $_GET['comment'];
				
				$cquery = "insert into comments values('',$name,$imaegeurl,$email,$comment,NULL);";
				
				$res = mysqli_query($con,$cquery);
				if($res)
				{
					$id = mysqli_insert_id($res);
					$arr1 = ("success"=>true,"id"=>$id);
					echo json_encode(arr1);
				}
				else{
					$arr1 = ("success"=>false);
					echo json_encode($arr);
				}
				
			}
			else{
				
			}
	}else
	{

			$arr = array("connection"=>"failure");
			echo json_encode($arr);
	}
?>