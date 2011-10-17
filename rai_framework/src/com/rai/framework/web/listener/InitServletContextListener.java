package com.rai.framework.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.rai.framework.exception.ErrorMessage;

public class InitServletContextListener implements ServletContextListener {
	public static ServletContext servletContext;

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent arg0) {
		servletContext = arg0.getServletContext();
		ErrorMessage em = ErrorMessage.getInstance();
		servletContext.setAttribute("app_ErrorMessage", em.loadErrorMessage());
	}
}
