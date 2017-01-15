
jQuery(document).ready(function() {
	
	/*设置背景*/
    $.backstretch("assets/img/backgrounds/1.jpg");
    
    $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });
	
    $('.login-form').on('submit', function(e) {
    	
    	$(this).find('input[type="text"], input[type="password"], textarea').each(function(){
    		if( $(this).val() == "" ) {
    			e.preventDefault();
    			$(this).addClass('input-error');
    		}
    		else {
    			$(this).removeClass('input-error');
    			$.ajax({
        	        url: "./login",
        	        type: "POST",
        	        cache: false,
        	    	dataType:"json",
        	    	data:{"username":$('#username').val(),"password":$('#password').val()},
        	        success: function(data) {
        	        	$('#myModal').modal()  
        	        },
        	        error: function() {
        	        },
        	    });
    		}
    	});
    	
    });
    
    
});
