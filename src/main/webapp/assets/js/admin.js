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
	
	
});
