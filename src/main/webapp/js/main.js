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
	        for(var i=0;i<data.tasks.length;i++){  
	            str += '<div class="col-md-4">';
	        	str += '<span class="fa-stack fa-4x">';
	        	str += '   <i class="fa fa-circle fa-stack-2x text-primary"></i>';
	        	str += '   <a class="fa fa-laptop fa-stack-1x fa-inverse" id="'+data.tasks[i].taskID+'"></a>';
	            str += '</span>';
	        	str += '<h4 class="service-heading">'+data.tasks[i].taskObject.taskTitle+'</h4>';
	        	str += '</div>';
	        }  
		    $("#taskTable").append(str);  
		    bindTaskClick(data);
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
	//绑定task的click事件
	function bindTaskClick(data){
        for(var i=0;i<data.tasks.length;i++){ 
        	//绑定弹出事件
        	$("#"+data.tasks[i].taskID).bind('click',data.tasks[i].taskID,function(event) {
        		var taskID= event.data;
        		$.ajax({
	    	        url: "/kfqb-workers-union/action/usertask/"+taskID,
	    	        type: "GET",
	    	        cache: false,
	    	    	dataType:"json",
	    	        success: function(data) {
	    	        	var task = data.taskObject;
	    	        	$('#portfolioModal').modal();
	        	    	var str='';
	        	    	str += '<h2>'+task.taskTitle+'</h2>';
	        	    	str += '<p class="item-intro text-muted">'+task.instruction+'</p>';
	        	    	str += '<table class="table table-hover">';
	        	        for(var i=0;i<task.table.lines.length;i++){
	        	        	str += '<tr>';
	        	        	str += '<td>'+task.table.lines[i].key+'</td>';
						    str += '<td><input type="text" id="'+task.table.lines[i].key+'" value="'+task.table.lines[i].value+'" placeholder="'+task.table.lines[i].tip+'"/></td>';
						    str += '</tr>';
	        	        }
	        	        str += '</table>';
	        	        str += '<button type="button" class="btn btn-primary" id="usertasksubmit">';
	        	        str += '<span class="glyphicon glyphicon-ok-sign"></span>';
	        	        str += '提 交';
						str += '</button>';
	        	        $("#modalbody").empty();
	        	        $("#modalbody").append(str);
	        	        //绑定用户更新事件 
	        	        BindUserUpdateTask(taskID);
	    	        },
	    	        error: function() {
	    	        },
	    	    });
        });
       }
    }
	
	function BindUserUpdateTask(taskID){
		$("#usertasksubmit").unbind();
		$("#usertasksubmit").bind('click',taskID,function(event) {
			var taskID = event.data;
			$.ajax({
    			url: "/kfqb-workers-union/action/usertask/"+taskID,
    	        type: "PUT",
    	        cache: false,
    	    	dataType:"json",
    	    	data:{"taskObject":GetUserTaskTableData()},
    	        success: function(data) {
	        		if (data.status === "0"){
	        			$('#modaltext').text("提交成功");
	        			$('#myModal').modal();
	        			//window.location.href="../pages/main.html";
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
	
	//遍历更新用户任务表格
	function GetUserTaskTableData(){
        var str = '';
        str += '{';
        str += '"taskTitle":"'+$("#modalbody h2").text()+'",';
        str += '"instruction":"'+$('#modalbody p').text()+'",';
        str += '"table":{';
        str += '"lines":[';
        $("#modalbody table tr").each(function(trindex,tritem){
        	str += '{';
        	$(tritem).find("td input").each(function(tdindex,tditem){
        		str += '"key":"' + tditem.id + '","value":"' + tditem.value + '",'+'"tip":"'+tditem.placeholder+'",';
        	});
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
	
	//logout
	$('#logout').on('click', function() {
		$.ajax({
	        url: "../login/logout",
	        type: "POST",
	        cache: false,
	    	dataType:"json",
	        success: function(data) {
	        	window.location.href="../";
	        },
	        error: function() {
	        	$('#modaltext').text("访问出错");
    			$('#myModal').modal();  
	        },
	    });
	});
	
	//user setting
	$('#profilebtn').on('click', function() {
		$.ajax({
	        url: "/kfqb-workers-union/action/user/self",
	        type: "GET",
	        cache: false,
	    	dataType:"json",
	        success: function(data) {
        		if (data.status === "0"){
        			var user = data.user;
        			var str='';
        	    	str += '<h2>'+user.name+'</h2>';
        	    	str += '<p class="item-intro text-muted"></p>';
        	    	str += '<table class="table table-hover">';
        	    	str += '<tr><td>工号</td><td>'+user.id+'</td></tr>';
        	    	str += '<tr><td>团队</td><td>'+user.team+'</td></tr>';
        	    	str += '<tr><td>手机号</td><td>'+user.cellphone+'</td></tr>';
        	    	if (user.adminFlag === "true"){
        	    		str += '<tr><td>管理员</td><td>是</td></tr>';
        	    	}else{
        	    		str += '<tr><td>管理员</td><td>否</td></tr>';
        	    	}
        	    	str += '</table>';
        	    	$('#portfolioModal').modal();
        	    	$("#modalbody").empty();
        	        $("#modalbody").append(str);
        		}else{//校验未通过，弹框提示
        			$('#modaltext').text(data.msg);
        			$('#myModal').modal();  
        		}
	        },
	        error: function() {
	        },
	    });
	});
	$('#tipmodalbtn').on('click', function() {
		$("#portfolioModal").modal("toggle");
	});
});

