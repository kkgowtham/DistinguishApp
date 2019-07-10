<?php

	require "init.php";
	
	$postid = $_GET['postid'];
	
	if($con)
	{
		$readquery = "select * from comments where postid ='$postid';";
		$result = mysqli_query($con,$readquery);
		$arr= array();
		while($row = mysqli_fetch_assoc($result))
		{
			$arr[] = $row;
		}
		echo json_encode($arr);
	}
	else{
		echo "Connection Failure";
	}
?>