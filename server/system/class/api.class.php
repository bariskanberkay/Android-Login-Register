<?php 


class API {




	public function checkApiStatus(){
		$db = new Db();
		$query = $db->from('tbl_api_settings')
			->where('ID', "1")
			->first();
		
		if($query['API_STATUS'] == "1"){
			
			$set['APP'][]=array('msg' =>'Api Active','success'=>'1');
			
		}else{
			
			$set['APP'][]=array('msg' =>'Api Deactive !','success'=>'0');
		}
		$json = json_encode($set);

			return $json;
		
		
	}
	
	
	public function checkApiKey($api_key){
		
		$db = new Db();
		$query = $db->from('tbl_api_settings')
			->where('ID', "1")
			->first();
			
			if($query['API_KEY'] == $api_key){
			
			return true;
			
		}else{
			
			return false;
		}
		
		
		
		
		
	}
	
	
	public function get_about_app(){
		
		
	$db = new Db();
	
	
		
			$query = $db->from('tbl_about_app')
			->where('ID',"1")
			->first();
   
   if($query){
	   
	 
	   
			
			
		$set['APP'][]=array(
		'app_name' =>$query['NAME'],
		'app_logo'=>$query['LOGO'],
		'app_version'=>$query['VERSION'],
		'app_author'=>$query['AUTHOR'],
		'app_contact'=>$query['CONTACT'],
		'app_email'=>$query['EMAIL'],
		'app_website'=>$query['WEBSITE'],
		'app_description'=>stripslashes($query['DESCRIPTION']),
		'app_privacy_policy'=>stripslashes($query['PRIVACY_POLICY'])
		);
			
		
		
	}else{
		
		
		$set['APP'][]=array('msg' =>'Some Error :( !','success'=>'0');
	}
		
		$json = json_encode($set);

			return $json;
		
		
		
	}
	
	public function login($email,$password){
		$db = new Db();
		
		if(isset($_GET["email"]) && isset($_GET["password"])){
		
			$query = $db->from('tbl_users')
			->where('EMAIL', $_GET["email"])
			->where('PASSWORD', $_GET["password"])
			->first();
   
		   if($query){
			   
			   if($query['STATUS'] == "1"){
				
				$set['APP'][]=array(
				'user_id' => $query['ID'],
				'name'=>$query['NAME'],
				'user_name'=>$query['USERNAME'],
				'website'=>$query['WEBSITE'],
				'bio_text'=>$query['BIO'],
				'email'=>$query['EMAIL'],
				'phone'=>$query['PHONE'],
				'profile_image'=>$query['PROFILE_IMAGE'],
				'level'=>$query['LEVEL'],
				'success'=>'1');
				
			}else if($query['STATUS'] == "2"){
				
				$set['APP'][]=array(
				'user_id' => $query['ID'],
				'name'=>$query['NAME'],
				'user_name'=>$query['USERNAME'],
				'website'=>$query['WEBSITE'],
				'bio_text'=>$query['BIO'],
				'email'=>$query['EMAIL'],
				'phone'=>$query['PHONE'],
				'profile_image'=>$query['PROFILE_IMAGE'],
				'level'=>$query['LEVEL'],
				'success'=>'2',
				'msg'=>'Confirmation Needed');
				
			}else{
				$set['APP'][]=array('msg' =>'Login failed','success'=>'0');
				
			}
			   
		   
					
				
				
			}else{
				
				
				$set['APP'][]=array('msg' =>'Email or Password Incorrect !','success'=>'0');
			}
				
				//header( 'Content-Type: application/json; charset=utf-8' );
			$json = json_encode($set);

			return $json;
			
				
				
			}
		
		
		
	}


	public function register($email,$password,$username,$phone){
		$db = new Db();
		
				$query = $db->from('tbl_users')
				->where('EMAIL', $email)
				->first();
   
   if($query){
	   
	  $set['APP'][]=array('msg' => "Email address already used!",'success'=>'0');
			
		
		
	}else{
		if (!filter_var($email, FILTER_VALIDATE_EMAIL)) 
		{
			$set['APP'][]=array('msg' => "Invalid email format!",'success'=>'0');
		}else
		{ 
 			
			$query = $db->insert('tbl_users')
            ->set(array(
                 "NAME" => '',
                 "USERNAME" =>  $username,
                 "WEBSITE" =>"",
				 "BIO" => "",
				 "EMAIL" => $email,
				 "PHONE" => $phone,
				 "PASSWORD" => $password,
				 "HASH_KEY" => $this->random_hash_key_generator(20),
				 "PROFILE_IMAGE" => '1.jpeg',
				 "STATUS" => '2',
				 "LEVEL" => '2',
				 "VER_CODE" => $this->random_ver_code_generator(),
            ));
			
			
 			 
 				if ( $query ){
				 $set['APP'][]=array('msg' => "Register successflly...!",'success'=>'1');
				}								 
					 
				
			
					
		}
		
		
	}
		$json = json_encode($set);

			return $json;
		
	}
	
	
	
	
	public function verify_account($user_id,$ver_code){
		$db = new Db();
		
		
		$pre_query = $db->from('tbl_users')
			->where('ID', $user_id)
			->first();
   
		   if($pre_query){
			   
			   if($ver_code == $pre_query["VER_CODE"]){
				   
				   
				   $query = $db->update('tbl_users')
					->where('ID', $pre_query["ID"])
					->set([
						 'status' => '1'
					]);
		   
					if ( $query ){
					 $set['APP'][]=array('msg'=>'Account Activated','success'=>'1');	
					}
				   
				
				
				
				}else{
					$set['APP'][]=array('msg'=>'Verification Code Not ','success'=>'0');	
					
				}
			   
		   
					
				
				
			}else{
				
				
				$set['APP'][]=array('msg' =>'Some Error :( !','success'=>'0');
			}
		
		
		
		$json = json_encode($set);

			return $json;
		
	}
	
	public function forgot_pass($email){
		$db = new Db();
		
		
		$query = $db->from('tbl_users')
			->where('EMAIL', $email)
			->first();
   
		   if($query){
			   
			   /*SEND EMAIL FUNCTIONS HERE*/
			   
			   
			 $set['APP'][]=array('msg'=>'Pass Recovery Email Sended','success'=>'1');	
					
			   
		   
					
				
				
			}else{
				
				
				$set['APP'][]=array('msg' =>'User Not Found Check Email !','success'=>'0');
			}
		
		
		
		$json = json_encode($set);

			return $json;
		
	}
	
	
	
	public function random_ver_code_generator(){
		
		$ver_code = mt_rand(100000, 999999);
		return $ver_code;
	}
	
	
	function random_hash_key_generator($length) {
    return substr(md5(openssl_random_pseudo_bytes(20)),-$length);
}

}

?>