package cn.edu.ycm.union.container;

import javax.servlet.ServletContextEvent;

import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;

import cn.edu.ycm.union.database.DatabaseOper;
import jodd.petite.PetiteContainer;
import jodd.petite.config.AutomagicPetiteConfigurator;

public class WorkersUnionContainer extends ResteasyBootstrap {
	
	//static final Logger logger = LoggerFactory.getLogger(WorkersUnionContainer.class);
	
	private static PetiteContainer workerUnionIOC;
	private static AutomagicPetiteConfigurator workerUnionConfig;
	
	public void contextInitialized(ServletContextEvent event) {
		
		//resteasy初始化
		//logger.info("ResteasyBootstrap开始初始化");
		super.contextInitialized(event);
		
		//数据库初始化
		//logger.info("WorkersUinonMongoDB开始初始化");
		DatabaseOper.initializedDB();
		
		//IOC容器初始化
		//logger.info("WorkersUinon容器开始初始化");
		workerUnionIOC = new PetiteContainer();
		workerUnionConfig = new AutomagicPetiteConfigurator();
		workerUnionConfig.configure(workerUnionIOC);
	}

	public void contextDestroyed(ServletContextEvent event) {
		
		//数据库释放
		//logger.info("WorkersUinonMongoDB开始释放资源");
		DatabaseOper.dropDB();
		
		//容器释放
		//logger.info("WorkersUinon容器开始资源释放");
		workerUnionIOC.shutdown();
		
		//resteasy释放
		//logger.info("ResteasyBootstrap资源释放");
		super.contextDestroyed(event);
		
	}
}
