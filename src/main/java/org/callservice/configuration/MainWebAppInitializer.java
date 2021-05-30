package org.callservice.configuration;

import org.callservice.service.UserInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.util.EnumSet;

public class MainWebAppInitializer implements WebApplicationInitializer{
//public class MainWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return null;
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class[] {WebConfig.class};
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[] {"/"};
//    }
    ////////////////        //super.onStartup(aServletContext);

    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(PersistenceConfig.class, WebConfig.class, WebSecurityConfig.class, LoggerConfig.class);

        aServletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(WebConfig.class);
        //create dispatcher configure and register
        ServletRegistration.Dynamic registration = aServletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

        //add filter to open hidden http method in html PATCH DELETE
        registerHiddenFieldFilter(aServletContext);
        //add filter for Security (authorization and authentification)
        configureSpringSecurityFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter())
                .addMappingForUrlPatterns(null ,true, "/*");
    }

    private void configureSpringSecurityFilter(ServletContext aContext) {
        aContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy())
            .addMappingForUrlPatterns(null,true,"/*");
    }




}
