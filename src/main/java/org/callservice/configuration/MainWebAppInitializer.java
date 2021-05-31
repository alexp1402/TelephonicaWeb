package org.callservice.configuration;


import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.*;


public class MainWebAppInitializer implements WebApplicationInitializer{

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

    //filter to add patch in http (base get/post)
    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter())
                .addMappingForUrlPatterns(null ,true, "/*");
    }


    private void configureSpringSecurityFilter(ServletContext aContext) {
        aContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy())
            .addMappingForUrlPatterns(null,true,"/*");
    }
}
