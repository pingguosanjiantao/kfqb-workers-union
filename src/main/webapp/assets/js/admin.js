
jQuery(document).ready(function() {
	
	//设置背景
    $.backstretch("../assets/img/backgrounds/1.jpg");
    
    $('#loginbtn').on('click', function() {
    		//校验工号
	    	if( $('#username').val() == "" ) {
	    		$('#modaltext').text("请先输入工号");
    			$('#myModal').modal();  
    		//校验密码
    		}else if($('#password').val() == ""){
    			$('#modaltext').text("请先输入密码");
    			$('#myModal').modal();  
    		}else{
    			$.ajax({
        	        url: "./login",
        	        type: "POST",
        	        cache: false,
        	    		dataType:"json",
        	    		data:{"username":$('#username').val(),"password":$('#password').val()},
        	        success: function(data) {
        	        		//校验通过，跳转页面
        	        		if (data.status === "0"){
        	        			window.location.href=data.jumpurl;
        	        		}else{//校验未通过，弹框提示
        	        			$('#modaltext').text(data.msg);
        	        			$('#myModal').modal();  
        	        		}
        	        },
        	        error: function() {
        	        		$('#modaltext').text("访问出错");
            			$('#myModal').modal();  
        	        },
        	    });
    			
    		}
			
    });
    
});
