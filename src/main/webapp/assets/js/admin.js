$(document).ready(function(){
	
	/*展示用户列表*/
	$.ajax({
        url: "/kfqb-workers-union/action/users",
        type: "GET",
        cache: false,
    		dataType:"json",
        success: function(data) {
	        	//校验通过，跳转页面
	    		if (data.status === "0"){
	    			var str="";  
	    			str += '<div class="panel panel-default">';
	    		    	str +=	    '<table class="table">';
	    		    	str += '<thead>';
	    		    	str += '<tr class="active">';
    		    		str +=	    '<td>工号</td>';
    		    		str +=	    '<td>姓名</td>';
    		    		str +=	    '<td>团队</td>';
    		    		str +=	    '<td>手机号</td>';
    		    		str +=	    '<td>管理员</td>';
    		    		str +=	    '<td>操作</td>';
    		    		str += '</tr>';
    		    		str += '</thead>';
	    		    	for(var i=0;i<data.users.length;i++){  
	    		    		str += '<tr>';
	    		    		str +=	    '<td>'+data.users[i].id+'</td>';
	    		    		str +=	    '<td>'+data.users[i].name+'</td>';
	    		    		str +=	    '<td>'+data.users[i].team+'</td>';
	    		    		str +=	    '<td>'+data.users[i].cellphone+'</td>';
	    		    		if (data.users[i].adminFlag === "true"){
	    		    			str +=     '<td><button type="button" class="btn btn-primary" id="myButton4" >取消管理员</button></td>';
	    		    		}else{
	    		    			str +=     '<td><button type="button" class="btn btn-success" id="myButton4" >设为管理员</button></td>';
	    		    		}
	    		    		str +=     '<td><button type="button" class="btn btn-danger" id="myButton4" >删除用户</button></td>';
	    		    		str += '</tr>';
	    		    	}
	    			str +=     '</table>';
	    			str +='</div>';
	    			$("#bodytext").empty();
	    			$("#bodytext").append(str);
	    		}else{//校验未通过，弹框提示
	    			$('#modaltext').text(data.msg);
	    			$('#myModal').modal();  
	    		}
        	
        },
        error: function() {
        		window.location.href='../';
        },
    });
	
	//新建一个用户
	$('#newuser').on('click', function() {
		var str=""; 
		str += '<div class="panel panel-default">';
	    	str +=	    '<table id="newusertable" class="table">';
	    	str += 			'<thead>';
	    	str += 				'<tr class="active">';
		str +=	    				'<td>工号</td>';
		str +=	  		  		'<td>姓名</td>';
		str +=	    				'<td>团队</td>';
		str +=	    				'<td>手机号</td>';
		str += 				'</tr>';
		str += 			'</thead>';
		str += 			'<tr>';
		str +=	    			'<td><input type="text" class="form-control" id="id" placeholder="工号"></td>';
		str +=	    			'<td><input type="text" class="form-control" id="name" placeholder="姓名"></td>';
		str +=	    			'<td><input type="text" class="form-control" id="team" placeholder="团队"></td>';
		str +=	    			'<td><input type="text" class="form-control" id="cellphone" placeholder="手机号"></td>';
		str += 			'</tr>';
		str +=     '</table>';
		str +='</div>';
		str += '<td><button type="button" class="btn btn-primary" id="newuserbtn" >新建用户</button></td>';
		
		$("#bodytext").empty();
		$("#bodytext").append(str);
		
		var selector = '#'+"newuserbtn";
	    	//重新绑定事件
	    	$('#newuserbtn').on('click', function() {
	    		/*请求任务页面*/
	    		$.ajax({
	    			url: "/kfqb-workers-union/action/user",
	    	        type: "POST",
	    	        cache: false,
	    	    		dataType:"json",
	    	    		data:{"newuser":GetNewUserTableData()},
	    	        success: function(data) {
	        	    		alert("submit");
	    	        },
	    	        error: function() {
	    	        },
	    	    });
	    		
	    	});
		
	});
	
	//遍历一个表格
	function GetNewUserTableData(){
        var str = '';
        str += '{';
        $("#newusertable tr td input").each(function(trindex,tritem){
	    		str += '"';
	    		str += tritem.id;
	    		str += '":"';
	    		str += tritem.value;
	    		str += '",';
        });
        str += '"adminFlag":"false"';
        str += '}';
        return str;
    }
});
