package cn.edu.ycm.union.container;

import javax.servlet.ServletContextEvent;

import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.ycm.union.database.DatabaseOper;

public class WorkersUnionContainer extends ResteasyBootstrap {
	
	static final Logger logger = LoggerFactory.getLogger(WorkersUnionContainer.class);
	
	public void contextInitialized(ServletContextEvent event) {
		
		//resteasy初始化
		logger.info("ResteasyBootstrap开始初始化");
		super.contextInitialized(event);
		
		//数据库初始化
		logger.info("WorkersUinonMongoDB开始初始化");
		DatabaseOper.initializedDB();
		
	}

	public void contextDestroyed(ServletContextEvent event) {
		
		//数据库释放
		logger.info("WorkersUinonMongoDB开始释放资源");
		DatabaseOper.dropDB();
		
		//resteasy释放
		logger.info("ResteasyBootstrap资源释放");
		super.contextDestroyed(event);
		
	}
}
