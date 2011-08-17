package com.dim.mail.sitebricks;

import com.dim.mail.sitebricks.controllers.Home;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.sitebricks.SitebricksModule;

public class AppContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new SitebricksModule() {
			protected void configureSitebricks() {
	            scan(Home.class.getPackage());
	        }
		});
	}

}
