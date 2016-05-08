package be.ugent.tiwi.listeners;

import be.ugent.tiwi.controller.ScheduleController;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Eigenaar on 17/04/2016.
 */
public class JsonControllerContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        context.setAttribute("scraperScheduler", new ScheduleController());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        ScheduleController controller = (ScheduleController) context.getContext("scraperScheduler");
        if(controller != null)
            controller.stop();
    }
}
