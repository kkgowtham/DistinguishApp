<?php
	require "init.php";
	
	if($con)
	{
		if(isset($_GET['email']))
		{
			$email = $_GET['email'];
			$query = "select postid from likes where email='$email';";
			$result = mysqli_query($con,$query);
			$arr=array();
			while($row = mysqli_fetch_assoc($result))
			{
				$arr[] = $row;
			}
			echo json_encode($arr);
		}
		else
		{
			echo json_encode(array("success"=>false));
		}
	}
	else
	{
		echo json_encode(array("connection"=>false));
	}
?>