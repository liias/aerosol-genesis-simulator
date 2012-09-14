package ee.ut.simulator;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;

public class Launcher {
    public static void main(final String[] arguments) throws Exception {
        /* Server jettyServer = new Server(8080);
       DispatcherServlet springServlet = new DispatcherServlet();
       springServlet.setContextConfigLocation("classpath:META-INF/spring/applicationContext.xml");
       final ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
       contextHandler.setContextPath("/");
       contextHandler.addServlet(new ServletHolder(springServlet), "/*");
       jettyServer.setHandler(contextHandler);

       contextHandler.getInitParams().put("contextConfigLocation", "classpath:META-INF/spring/applicationContext.xml");
       ContextLoaderListener listener = new ContextLoaderListener();
       contextHandler.addEventListener(listener);

       jettyServer.start();
       jettyServer.join();
        */


        /*  Server server = new Server(8082);
        ServletContextHandler root = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setContextConfigLocation("classpath:META-INF/spring/applicationContext.xml");

        root.addServlet(new ServletHolder(dispatcherServlet), "/*");
        server.start();       */



       // dispatcherServlet
        //        .setContextConfigLocation("classpath:com/earldouglas/selfcontained/mvc-config.xml");





        Server jettyServer = new Server(8080);
        DispatcherServlet springServlet = new DispatcherServlet();
        springServlet.setContextConfigLocation("classpath:META-INF/spring/webmvc-config.xml, classpath:META-INF/spring/applicationContext.xml");
        ServletContextHandler contextHandler = new ServletContextHandler(jettyServer, "/", ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        ServletHolder servletHolder = new ServletHolder(springServlet);
        servletHolder.setName("myServlet");
        contextHandler.addServlet(servletHolder, "/*");
        jettyServer.setHandler(contextHandler);

        //springServlet.getNamespace()

        contextHandler.getInitParams().put("contextConfigLocation", "classpath:META-INF/spring/webmvc-config.xml, classpath:META-INF/spring/applicationContext.xml");
        ContextLoaderListener listener = new ContextLoaderListener();
        contextHandler.addEventListener(listener);

        jettyServer.start();
        jettyServer.join();



        //context.getServletHandler().setInitializeAtStart(false);
        //server.start();
        //context.getServletHandler().initialize();
        //server.join();

    }
}