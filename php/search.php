<?php
		require "init.php";
		
		if($con)
		{
			if(isset($_GET['string'])
			{
				$qstring = $_GET['string'];
				$query = "select * from distinguish where term1 like '$qstring' or term2 like '$qstring';";
				$res = mysqli_query($query);
				if($res)
				{
					$resarr = array();
					while($row = mysqli_fetch_assoc($res))
					{
						$resarr[] = $row;
					}
					echo json_encode($resarr);
				}else{
					$arr1 = array("success"=>false);
					echo json_encode($arr);
				}
			}
		}else{
			$arr = array("connection"=>"failure");
			echo json_encode($arr);
		}
			
?>
