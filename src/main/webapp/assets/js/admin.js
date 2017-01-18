$(document).ready(function(){
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
		$("#bodytext").unbind();
		$("#bodytext").empty();
		$("#bodytext").append(str);
		var selector = '#'+"newuserbtn";
	    	//重新绑定事件
	    	$(selector).on('click', function() {
	    		/*请求任务页面*/
	    		$.ajax({
	    			url: "/kfqb-workers-union/action/user",
	    	        type: "POST",
	    	        cache: false,
	    	    		dataType:"json",
	    	    		data:{"newuser":GetNewUserTableData()},
	    	        success: function(data) {
	    	        		if (data.status === "0"){
	    	        			$('#modaltext').text("添加成功");
	    	        			$('#myModal').modal();
	    	        			$("#mangeusers").click();
	    	        		}else{
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
	
	//查询所有用户
	$('#mangeusers').on('click', function() {
		/*展示用户列表*/
		$.ajax({
	        url: "/kfqb-workers-union/action/users",
	        type: "GET",
	        cache: false,
	    		dataType:"json",
	        success: function(data) {
		        	//校验通过，跳转页面
		    		if (data.status === "0"){
		    			$("#bodytext").empty();
		    			var str="";  
		    			str += '<div class="panel panel-default">';
		    		    	str +=	    '<table class="table" id="userinfo">';
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
		    		    			str +=     '<td><button type="button" class="btn btn-primary"  id="admin'+data.users[i].id+'" >取消管理员</button></td>';
		    		    		}else{
		    		    			str +=     '<td><button type="button" class="btn btn-success" id="admin'+data.users[i].id+'" >设为管理员</button></td>';
		    		    		}
		    		    		str +=     '<td><button type="button" class="btn btn-danger" id="del'+data.users[i].id+'" >删除用户</button></td>';
		    		    		str += '</tr>';
		    		    	}
		    			str +=     '</table>';
		    			str +='</div>';
		    			$("#bodytext").empty();
		    			$("#bodytext").append(str);

		    			//重新绑定事件
		    			for(var i=0;i<data.users.length;i++){  
		    		    		//绑定设置管理员事件
		    		    		$("#admin"+data.users[i].id).bind('click',data.users[i],function(event) {
		    		    			var user = event.data;
		    		    			var isAdmin = 'true';
			    		    		if (user.adminFlag === "true"){
			    		    			isAdmin = 'false';
			    		    		}
			    		    		var reqdata = '{"id":'+user.id+',"name":'+user.name+',"team":'+user.team+',"cellphone":'+user.cellphone+',"adminFlag":'+isAdmin+'}';
				    	    		/*请求任务页面*/
			    		    		var requrl = "/kfqb-workers-union/action/user/"+user.id;
				    	    		$.ajax({
				    	    			url: requrl,
				    	    	        type: "PUT",
				    	    	        cache: false,
				    	    	    		dataType:"json",
				    	    	    		data:{"newuser":reqdata},
				    	    	        success: function(data) {
				    	    	        		if (data.status === "0"){
				    	    	        			if (isAdmin==="true"){
				    	    	        				$('#modaltext').text("设置管理员成功");
				    	    	        			}else{
				    	    	        				$('#modaltext').text("取消管理员成功");
				    	    	        			}
				    	    	        			$('#myModal').modal();
				    	    	        			$("#mangeusers").click();
				    	    	        		}else{
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
		    		    		
				    	    	//绑定删除用户事件
				    	    	$("#del"+data.users[i].id).on('click',data.users[i], function(event) {
				    	    		var user = event.data;
				    	    		var requrl = "/kfqb-workers-union/action/user/"+user.id;
				    	    		/*请求任务页面*/
				    	    		$.ajax({
				    	    			url: requrl,
				    	    	        type: "DELETE",
				    	    	        cache: false,
				    	    	    		dataType:"json",
				    	    	        success: function(data) {
				    	    	        		if (data.status === "0"){
				    	    	        			$('#modaltext').text("删除成功");
				    	    	        			$('#myModal').modal();
				    	    	        			$("#mangeusers").click();
				    	    	        		}else{
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
	    		    	}
		    		}else{//校验未通过，弹框提示
		    			$('#modaltext').text(data.msg);
		    			$('#myModal').modal();  
		    		}
	        },
	        error: function() {
	        		window.location.href='../';
	        },
	    });
	});
	
	//新建一个任务
	$('#newtask').on('click', function() {
		var str=""; 
		str += '<div id="newtaskbody">';
		str += '<input type="text" class="form-control" id="titile" placeholder="统计项目名称">';
		str += '<input type="text" class="form-control" id="instruction" placeholder="统计描述：如截止时间">';
		str += '<div class="panel panel-default">';
	    	str +=	    '<table id="newtasktable" class="table">';
	    	str += 			'<thead>';
	    	str += 				'<tr class="active">';
		str +=	    				'<td>统计项名称</td>';
		str +=	  		  		'<td>提示语</td>';
		str += 				'</tr>';
		str += 			'</thead>';
		str += 			'<tr>';
		str +=	    			'<td><input type="text" class="form-control" id="key" placeholder="显示为统计项名称"></td>';
		str +=	    			'<td><input type="text" class="form-control" id="tip" placeholder="显示为提示语，如填选项等"></td>';
		str += 			'</tr>';
		str +=     '</table>';
		str += '</div>';
		str += '<td>';
		str += '<button type="button" class="btn btn-default" id="newline">';
	    str += '<span class="glyphicon glyphicon-plus"></span> 新增一行';
	    str += '</button>';
	    str += '<button type="button" class="btn btn-default" id="delline">';
	    str += '<span class="glyphicon glyphicon-minus"></span> 删除末行';
	    str += '</button>';
		str += '<button type="button" class="btn btn-primary" id="submitnewtaskbtn" >新建任务</button>';
		str += '</td>';
		str += '</div>';
		$("#bodytext").unbind();
		$("#bodytext").empty();
		$("#bodytext").append(str);
		
	    	//新增一行
	    	$("#newline").on('click', function() {
	    		var str = '';
	    		str += '';
	    		str += 			'<tr>';
	    		str +=	    			'<td><input type="text" class="form-control" id="key" placeholder="显示为统计项名称"></td>';
	    		str +=	    			'<td><input type="text" class="form-control" id="tip" placeholder="显示为提示语，如填选项等"></td>';
	    		str += 			'</tr>';
	    		$("#newtasktable").append(str);
	    	});

	    	//删除末行
	    	$("#delline").on('click', function() {
	    		$("#newtasktable tr:last").remove();
	    	});
		var selector = '#'+"submitnewtaskbtn";
	    	//重新绑定事件
	    	$(selector).on('click', function() {
	    		/*请求任务页面*/
	    		$.ajax({
	    			url: "/kfqb-workers-union/action/user",
	    	        type: "POST",
	    	        cache: false,
	    	    		dataType:"json",
	    	    		data:{"newuser":GetNewUserTableData()},
	    	        success: function(data) {
	    	        		if (data.status === "0"){
	    	        			$('#modaltext').text("添加成功");
	    	        			$('#myModal').modal();
	    	        			$("#mangeusers").click();
	    	        		}else{
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
