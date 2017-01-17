/*初始化页面*/
$(document).ready(function(){
	/*初始化任务列表*/
	$.ajax({
        url: "/kfqb-workers-union/action/tasks",
        type: "GET",
        cache: false,
    		dataType:"json",
        success: function(data) {
	    		var str="";  
	        for(var i=0;i<data.length;i++){  
	            str += '<div class="col-md-4">';
	            	str += '<span class="fa-stack fa-4x">';
	            	str += '   <i class="fa fa-circle fa-stack-2x text-primary"></i>';
	            	str += '   <a class="fa fa-laptop fa-stack-1x fa-inverse" taskId="'+data[i].id+'" id="'+data[i].id+'"></a>';
	            str += '</span>';
	            	str += '<h4 class="service-heading">'+data[i].name+'</h4>';
	            	str += '</div>';
	            	var selector = '#'+data[i].id;
	            	//重新绑定事件
	            	$('#taskTable').on('click', selector, function() {
	            		$("#testID").append("<p>thest</p>");  
	            		alert($(selector).attr("taskId"));
	            		/*请求任务页面*/
	            		$.ajax({
	            	        url: "/kfqb-workers-union/action/tasks",
	            	        type: "GET",
	            	        cache: false,
	            	    		dataType:"json",
	            	        success: function(data) {
		            	    		var str="";  
		            	        for(var i=0;i<data.length;i++){
		            	        	
		            	        }  
		            	        $("#taskTable").append(str);  
	            	        },
	            	        error: function() {
	            	        },
	            	    });
	            		//弹出模态框供用户填写及提交
	            		$('#portfolioModal').modal({
	            	    })
	            		
	            		
	            	});
	        }  
	        $("#taskTable").append(str);  
        },
        error: function() {
        		window.location.href='../';
        },
    });
	
	//Jump to admin page
	$('#adminbtn').on('click', function() {
		//请求跳转
		$.ajax({
	        url: "../admin",
	        type: "POST",
	        cache: false,
	    		dataType:"json",
	        success: function(data) {
	        		//是管理员，跳转页面
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
	});
	
});

