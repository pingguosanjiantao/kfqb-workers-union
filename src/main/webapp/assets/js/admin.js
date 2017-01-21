$(document).ready(function(){
	
	//新建一个任务
	$('#newtask').on('click', function() {
		var str=""; 
		str += '<div id="newtaskbody">';
		str += '<div class="panel panel-default id="newtaskbody">';
		str += '<input type="text" class="form-control" id="taskTitle" placeholder="统计项目名称">';
		str += '<input type="text" class="form-control" id="instruction" placeholder="统计描述：如截止时间、适用人群">';
		str += '<div class="panel panel-default" id="taskpanel">';
	    	str +=	    '<table id="newtasktable" class="table">';
		str += 			'<tr>';
		str +=	    			'<td><input type="text" class="form-control" id="key" placeholder="显示为统计项名称"></td>';
		str +=	    			'<td><input type="text" class="form-control" id="tip" placeholder="显示为提示语，如是/否等"></td>';
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
		str += '</div>'
		$("#bodytext").unbind();
		$("#bodytext").empty();
		$("#bodytext").append(str);
		
	    	//新增一行
	    	$("#newline").on('click', function() {
	    		var str = '';
	    		str += '<tr>';
	    		str +=	  '<td><input type="text" class="form-control" id="key" placeholder="显示为统计项名称"></td>';
	    		str +=	  '<td><input type="text" class="form-control" id="tip" placeholder="显示为提示语，如填选项等"></td>';
	    		str += 	'</tr>';
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
	    			url: "/kfqb-workers-union/action/task",
	    	        type: "POST",
	    	        cache: false,
	    	    		dataType:"json",
	    	    		data:{"newtask":GetNewTaskTableData()},
	    	        success: function(data) {
	    	        		if (data.status === "0"){
	    	        			$('#modaltext').text("新建任务成功");
	    	        			$('#myModal').modal();
	    	        			$("#managetasks").click();
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
	
	//遍历新建任务表格
	function GetNewTaskTableData(){
        var str = '';
        str += '{';
        str += '"taskTitle":"'+$("#taskTitle").val()+'",';
        str += '"instruction":"'+$('#instruction').val()+'",';
        str += '"table":{';
        str += '"lines":[';
        $("#newtasktable tr").each(function(trindex,tritem){
        	str += '{';
        	$(tritem).find("td input").each(function(tdindex,tditem){
        		str += '"' + tditem.id + '":"' + tditem.value + '",';
        	});
        	str += '"value":"",';
    		str += '"valueType":"",';
    		str += '"valueTypeText":""';	
        	str += '},';
        });
        //去掉最后一个逗号
        str = str.substring(0,str.length-1)
        str += ']';
        str += '}';
        str += '}';
        return str;
    }
	
	//查询所有任务
	$('#managetasks').on('click', function() {
		/*展示用户列表*/
		$.ajax({
	        url: "/kfqb-workers-union/action/tasks",
	        type: "GET",
	        cache: false,
	    	dataType:"json",
	        success: function(data) {
		        	//校验通过，跳转页面
		    		if (data.status === "0"){
		    			$("#bodytext").empty();
		    			var str="";  
		    			str += '<div id="tasksbody">';
		    			str += '<div class="panel panel-default">';
	    		    	str +=	    '<table class="table" id="userinfo">';
	    		    	str += '<thead>';
	    		    	str += '<tr class="active">';
    		    		str +=	    '<td>任务编号</td>';
    		    		str +=	    '<td>任务名称</td>';
    		    		str +=	    '<td>创建者</td>';
    		    		str +=	    '<td>是否有效</td>';
    		    		str +=	    '<td>删除操作</td>';
    		    		str +=	    '<td>导出统计数据</td>';
    		    		str +=	    '<td>复制操作</td>';
    		    		str += '</tr>';
    		    		str += '</thead>';
	    		    	for(var i=0;i<data.tasks.length;i++){  
	    		    		str += '<tr>';
	    		    		str +=	    '<td>'+data.tasks[i].taskID+'</td>';
	    		    		str +=	    '<td>'+data.tasks[i].taskObject.taskTitle+'</td>';
	    		    		str +=	    '<td>'+data.tasks[i].createrName+'</td>';
	    		    		if (data.tasks[i].validFlag === "true"){
	    		    			str +=     '<td><button type="button" class="btn btn-primary"  id="taskValid'+data.tasks[i].taskID+'" >设为无效</button></td>';
	    		    		}else{
	    		    			str +=     '<td><button type="button" class="btn btn-success" id="taskValid'+data.tasks[i].taskID+'" >设为有效</button></td>';
	    		    		}
	    		    		str +=     '<td><button type="button" class="btn btn-danger"  id="del'+data.tasks[i].taskID+'" >删除任务</button></td>';
	    		    		str +=     '<td><button type="button" class="btn btn-warning" id="out'+data.tasks[i].taskID+'" >导出EXCEL</button></td>';
	    		    		str +=     '<td><button type="button" class="btn btn-primary" id="cpy'+data.tasks[i].taskID+'" >复制任务</button></td>';
	    		    		str += '</tr>';
	    		    	}
		    			str += '</table>';
		    			str += '</div>';
		    			str += '</div>';
		    			$("#bodytext").empty();
		    			$("#bodytext").append(str);

		    			//重新绑定事件
		    			for(var i=0;i<data.tasks.length;i++){  
		    		    		//绑定设置有效
		    		    		$("#taskValid"+data.tasks[i].taskID).bind('click',data.tasks[i],function(event) {
		    		    			var task = event.data;
		    		    			var validFlag = "true";
			    		    		if (task.validFlag === "true"){
			    		    			validFlag = "false";
			    		    		}
			    		    		var reqdata = ''+validFlag+'';
				    	    		/*请求任务页面*/
			    		    		var requrl = "/kfqb-workers-union/action/task/"+task.taskID;
				    	    		$.ajax({
				    	    			url: requrl,
				    	    	        type: "PUT",
				    	    	        cache: false,
				    	    	    	dataType:"json",
				    	    	    	data:{"validFlag":reqdata},
				    	    	        success: function(data) {
				    	    	        		if (data.status === "0"){
				    	    	        			if (validFlag==="true"){
				    	    	        				$('#modaltext').text("设置任务有效成功");
				    	    	        			}else{
				    	    	        				$('#modaltext').text("取消任务有效成功");
				    	    	        			}
				    	    	        			$('#myModal').modal();
				    	    	        			$("#managetasks").click();
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
		    		    		
				    	    	//绑定删除任务事件
				    	    	$("#del"+data.tasks[i].taskID).on('click',data.tasks[i], function(event) {
				    	    		var task = event.data;
				    	    		var requrl = "/kfqb-workers-union/action/task/"+task.taskID;
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
				    	    	        			$("#managetasks").click();
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
				    	    	
				    	    	//绑定复制任务事件
				    	    	$("#cpy"+data.tasks[i].taskID).on('click',data.tasks[i], function(event) {
				    	    		var task = event.data;
				    	    		$("#newtask").click();
				    	    		$("#taskTitle").val(task.taskObject.taskTitle);
				    	    		$("#instruction").val(task.taskObject.instruction);
				    	    		$("#newtasktable").empty();
				    	    		
				    	    		for(var i=0;i<task.taskObject.table.lines.length;i++){ 
				    	    			var str = '';
				    	    			str += '<tr>';
				    	    			str +=	  '<td><input type="text" class="form-control" id="key" placeholder="显示为统计项名称" value="'+task.taskObject.table.lines[i].key+'"></td>';
				    	    			str +=	  '<td><input type="text" class="form-control" id="tip" placeholder="显示为提示语，如填选项等" value="'+task.taskObject.table.lines[i].tip+'"></td>';
				    	    			str += 	'</tr>';
				    	    			$("#newtasktable").append(str);
				    	    		}
				    	    	});
				    	    	
				    	    	//绑定导出EXCEL事件
				    	    	
				    	    	
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
	
});
