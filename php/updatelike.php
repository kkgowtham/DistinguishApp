<?php
	include "init.php";
	if($con)
	{
		$postid = $_GET['postid'];
		$number = $_GET['numlikes'];
		$email = $_GET['email'];
		$fun = $_GET['fun'];
		
		if($fun=="like")
		{
			
			$updatequery = "update distinguish set likes='$number' where id='$postid';";
			$q1 = "select * from liketable where postid='$postid' and email='$email';";
			$tempres = mysqli_query($con,$q1);
			$num = mysqli_num_rows($tempres);
			if($num==0){
			$insertquery = "insert into liketable values('','$email','$postid');";
			if(mysqli_query($con,$insertquery))
			{
				echo "Inserted";
			}
			else{
				echo "Not Inserted";
			}
			}
			if(mysqli_query($con,$updatequery))
			{
				echo "Updated";
			}
			else{
				echo "Not Updated";
			}
			
		}
		else{
			$updatequery1 = "update distinguish set likes='$number' where id='$postid';";
			$deletequery = "delete from liketable where email='$email' and postid='$postid';";
			if(mysqli_query($con,$updatequery1))
			{
				echo "Updated";
			}
			else{
				echo "Not Updated";
			}
			if(mysqli_query($con,$deletequery))
			{
				echo "Deleted";
			}
			else{
				echo "Not Deleted".mysqli_errno;
			}
		}
	}
	else{
		echo "Connection Failed";
	}
?>