<?PHP




include('system/route/Route.php');
require 'system/db/BasicDB.php';

require 'system/class/api.class.php';







		
		Route::add('/login',function(){
	$api = new API();
	
	
	
	
	if(isset($_GET["email"]) && isset($_GET["password"])){
		
		$apistatus = $api->login($_GET["email"],$_GET["password"]);
		
		header( 'Content-Type: application/json; charset=utf-8' );
		

		echo $apistatus;
		 exit;
		
		
	}
},['get','post']);

Route::add('/register',function(){
	$api = new API();
	
	
	if(isset($_GET["email"]) && isset($_GET["password"])){
		
	$apistatus = $api->register($_GET["email"],$_GET["password"],$_GET["username"],$_GET["phone"]);
		
		header( 'Content-Type: application/json; charset=utf-8' );
		

		echo $apistatus;
		 exit;
		
		
	}
},['get','post']);


Route::add('/verify',function(){
		$api = new API();
	
	
	if(isset($_GET['user_id'])){
		
			
		
		$apistatus = $api->verify_account($_GET["user_id"],$_GET["verification_code"]);
		
		header( 'Content-Type: application/json; charset=utf-8' );
		

		echo $apistatus;
		 exit;
		
		
	}
},['get','post']);

Route::add('/api/app-about',function(){
	
		$api = new API();
		$apistatus = $api->get_about_app();
		
		header( 'Content-Type: application/json; charset=utf-8' );
		

		echo $apistatus;
		 exit;
		
		
	
},['get','post']);


Route::add('/forgotpassword',function(){
	$api = new API();
	
	
	
	
	if(isset($_GET["email"]) ){
		
		$apistatus = $api->forgot_pass($_GET["email"]);
		
		header( 'Content-Type: application/json; charset=utf-8' );
		

		echo $apistatus;
		 exit;
		
		
	}
},['get','post']);

Route::add('/api/status',function(){
	$api = new API();
	
	$apistatus = $api->checkApiStatus();
	
	header( 'Content-Type: application/json; charset=utf-8' );
		

	echo $apistatus;
	exit;
	
	
	
},['get','post']);


Route::run('');
		
	
	
	
	








?>
