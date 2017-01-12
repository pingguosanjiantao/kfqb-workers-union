package cn.edu.ycm.union.utils;

import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;

public class WokersUnionBootstrap extends ResteasyBootstrap {
	public WokersUnionBootstrap(){
		super();
		System.out.println("容器初始化");
	}

}
