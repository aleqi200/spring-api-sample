package de.groupon.sample;

import org.springframework.util.StringUtils;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {

    private static final String PROFILE_PROPERTY = "env";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();

        String profile = System.getProperty(PROFILE_PROPERTY);
        if (!StringUtils.hasText(profile)) {
            throw new IllegalArgumentException("profile not specified! If you run application via maven please set -D" + PROFILE_PROPERTY + "=<your_profile>");
        }

        applicationContext.getEnvironment().setActiveProfiles(StringUtils.commaDelimitedListToStringArray(profile));

        applicationContext.setServletContext(servletContext);
        applicationContext.scan("de.groupon.sample.config.profile");

        servletContext.addListener(new ContextLoaderListener(applicationContext));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("sample-api", new DispatcherServlet(applicationContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
    }
}
