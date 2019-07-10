<?php
	require "init.php";
	
	if($con)
	{
		if((isset($_GET['name']))&&(isset($_GET['imageurl']))&&(isset($_GET['email'])))
		{
			$name= $_GET['name'];
			$imageurl = $_GET['imageurl'];
			$email = $_GET['email'];
			
			$query ="select ^ from users where email='$email';";
			$rows = mysqli_query($con,$query);
			if(mysqli_num_rows($rows)>0)
			{
				echo json_encode(array("exists"=>true));
			}
			else{
				$query1 = "insert into users values('','$name','$email','$imageurl');";
				$result=mysqli_query($con,$query1);
				if($result)
				{
					$arr1 = ("success"=>true);
					echo json_encode($arr);
				}
				else{
					$arr1 = ("success"=>false);
					echo json_encode($arr);
				}
			}
		}
	}
	else{
		echo  json_encode(array("connection"=>false));
	}
?>