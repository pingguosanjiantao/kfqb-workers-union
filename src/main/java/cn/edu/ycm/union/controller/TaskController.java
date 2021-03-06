package cn.edu.ycm.union.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.ycm.union.dto.ReturnMsg;
import cn.edu.ycm.union.dto.TaskObject;
import cn.edu.ycm.union.dto.TaskTemplate;
import cn.edu.ycm.union.service.TaskService;
import cn.edu.ycm.union.utils.JsonTool;
import cn.edu.ycm.union.utils.ReturnTool;
import cn.edu.ycm.union.utils.UserTool;

@Path("/action")
public class TaskController {

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	private TaskService taskService = new TaskService();
	
	@GET
	@Path("tasks")
	public String getTasks(@Context HttpServletRequest request){
		logger.info("查询所有任务");
		List<TaskTemplate> result = taskService.getTasks();
		ReturnMsg ret = ReturnTool.getSuccReturn();
		ret.setTasks(result);
		return JsonTool.Json2String(ret);
	}
	
	@POST
	@Path("task")
	public String createTask(@FormParam("newtask") String newTaskJson,
		      			     @Context HttpServletRequest request){
		//反序列化为对象
		TaskObject taskObject = (TaskObject) JsonTool.String2Json(newTaskJson, TaskObject.class);
		logger.info("新增任务:"+JsonTool.Json2String(taskObject));
		if (!UserTool.isAdmin(request)){
			return ReturnTool.getFailedStringReturn("没有权限");
		}
		taskService.createTask(taskObject,UserTool.getUserNameBySession(request));
		return ReturnTool.getSuccStringReturn();
	}
	
	@DELETE
	@Path("task/{taskID}")
	public String delateTask(@PathParam("taskID") String taskID,
							 @Context HttpServletRequest request){
		logger.info("删除任务:"+taskID);
		if (!UserTool.isAdmin(request)){
			return ReturnTool.getFailedStringReturn("没有权限");
		}
		taskService.delTask(taskID);
		return ReturnTool.getSuccStringReturn();
	}
	
	/**
	 * 任务只支持修改有效标识
	 * @param validFlag
	 * @return
	 */
	@PUT
	@Path("task/{taskID}")
	public String updateTask(@PathParam("taskID") String taskID,
							 @FormParam("validFlag") String validFlag,
			     			 @Context HttpServletRequest request){
		TaskTemplate taskTemplate = taskService.getTaskTemplateByTaskID(taskID);
		
		logger.info("修改任务:"+JsonTool.Json2String(taskTemplate)+"的validFlag为"+validFlag);
		if (!UserTool.isAdmin(request)){
			return ReturnTool.getFailedStringReturn("没有权限");
		}
		taskTemplate.setValidFlag(validFlag);
		taskService.updateTask(taskID, taskTemplate);
		return ReturnTool.getSuccStringReturn();
	}
	
	@GET
	@Path("task/{taskID}")
	public String retrieveTask(@PathParam("taskID") String taskID){
		TaskTemplate taskTemplate = taskService.getTaskTemplateByTaskID(taskID);
		ReturnMsg ret = ReturnTool.getSuccReturn();
		ret.setTaskTemplate(taskTemplate);
		return JsonTool.Json2String(ret);
	}
	
	/**
	 * 根据taskID创建excel
	 * @param taskID
	 * @return
	 * @throws IOException 
	 */
	@GET
	@Path("excelTask/{taskID}")
	public String getTaskEcxel(@PathParam("taskID") String taskID,
							   @Context HttpServletRequest request,
							   @Context HttpServletResponse response) throws IOException{
		logger.info("用户请求生成"+taskID+"EXCEL数据");
		if (!UserTool.isAdmin(request)){
			return ReturnTool.getFailedStringReturn("没有权限");
		}
		
		//1.获取要下载的文件的绝对路径
		String realPath = request.getSession().getServletContext().getRealPath("/download/tmp.xlsx");
		
		String result = taskService.getExcelFileUserTaskByTaskID(taskID,realPath);
		if (result == null){
			return ReturnTool.getFailedStringReturn("生成EXCEL错误，请联系管理员");
		}
		
        //2.获取要下载的文件名
		TaskTemplate taskTemplate = taskService.getTaskTemplateByTaskID(taskID);
        String fileName = taskTemplate.getTaskObject().getTaskTitle()+".xlsx";
        //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
        response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
        //4.获取要下载的文件输入流
        InputStream in = new FileInputStream(realPath);
        int len = 0;
        //5.创建数据缓冲区
        byte[] buffer = new byte[1024];
        //6.通过response对象获取OutputStream流
        OutputStream out = response.getOutputStream();
        //7.将FileInputStream流写入到buffer缓冲区
        while ((len = in.read(buffer)) > 0) {
        //8.使用OutputStream将缓冲区的数据输出到客户端浏览器
            out.write(buffer,0,len);
        }
        in.close();
		
		ReturnMsg ret = ReturnTool.getSuccReturn();
		ret.setJumpurl(result);
		return ReturnTool.getSuccStringReturn();
	}
	
	/**
	 * 用户读取自己的任务信息，用于填写
	 * @param taskID
	 * @param request
	 * @return
	 */
	@GET
	@Path("usertask/{taskID}")
	public String getUserTaskByTaskID(@PathParam("taskID") String taskID,
									  @Context HttpServletRequest request){
		String userID = UserTool.getUserIDBySession(request);
		TaskObject taskObject = taskService.getUserTaskByTaskIDAndUserID(taskID,userID);
		ReturnMsg ret = ReturnTool.getSuccReturn();
		ret.setTaskObject(taskObject);
		logger.info(JsonTool.Json2String(ret));
		return JsonTool.Json2String(ret);
	}
	
	/**
	 * 更新用户填写表格
	 * @param validFlag
	 * @return
	 */
	@PUT
	@Path("usertask/{taskID}")
	public String updateUserTask(@PathParam("taskID") String taskID,
							 @FormParam("taskObject") String taskObjectJson,
			     			 @Context HttpServletRequest request){
		TaskTemplate taskTemplate = taskService.getTaskTemplateByTaskID(taskID);
		if (taskTemplate.getValidFlag().equals("false")){
			return ReturnTool.getFailedStringReturn("该统计不可填写！");
		}
		TaskObject taskObject = (TaskObject) JsonTool.String2Json(taskObjectJson, TaskObject.class);
		String userID = UserTool.getUserIDBySession(request);
		taskService.updateTaskByTaskIDAndUserID(taskID, userID, taskObject);
		return ReturnTool.getSuccStringReturn();
	}
	
}
