<?php

	require "init.php";
	$limit1 = $_GET['limit1'];
	$limit2 = $_GET['limit2'];
	/*if($con)
	{
		
		$query = "select * from distinguish";
		$result = mysqli_query($con,$query);
		$json_array = array();
		while($row = mysqli_fetch_assoc($result))
		{
			$json_array[] = $row;
		}
		echo json_encode($json_array);
	}
	else{
		echo "Connection Failure";
	}
*/
	if($con)
	{
		$query = "SELECT * FROM DISTINGUISH ORDER BY ID DESC LIMIT $limit1 ,$limit2";
		$result = mysqli_query($con,$query);
		$json_array = array();
		while($row = mysqli_fetch_assoc($result))
		{
			$json_array[] = $row;
		}
		echo json_encode($json_array);
	}
	else{
		echo "Connection Failure";
	}
?>