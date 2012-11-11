package ee.ut.physic.aerosol.simulator.util;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bootstraps Spring-managed beans into an application. How to use:
 * <ul>
 * <li>Create application context XML configuration files and put them where
 * they can be loaded as class path resources. The configuration must include
 * the {@code <context:annotation-config/>} element to enable annotation-based
 * configuration, or the {@code <context:component-scan base-package="..."/>}
 * element to also detect bean definitions from annotated classes.
 * <li>Create a "main" class that will receive references to Spring-managed
 * beans. Add the {@code @Autowired} annotation to any properties you want to be
 * injected with beans from the application context.
 * <li>In your application {@code main} method, create an
 * {@link ApplicationContextLoader} instance, and call the {@link #load} method
 * with the "main" object and the configuration file locations as parameters.
 * </ul>
 */
public class ApplicationContextLoader {

    protected ConfigurableApplicationContext applicationContext;

    private static ApplicationContextLoader instance = new ApplicationContextLoader();

    public static ApplicationContextLoader getInstance() {
        return instance;
    }

    private ApplicationContextLoader() {
    }

    public ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Loads application context. Override this method to change how the
     * application context is loaded.
     */
    public void loadApplicationContext(Class<?>... annotatedClasses) {
        applicationContext = new AnnotationConfigApplicationContext(annotatedClasses);
        applicationContext.registerShutdownHook();
    }

    /**
     * Injects dependencies into the object. Override this method if you need
     * full control over how dependencies are injected.
     *
     * @param main object to inject dependencies into
     */
    public void injectDependencies(Object main) {
        getApplicationContext().getBeanFactory().autowireBeanProperties(
                main, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
    }

    /**
     * Loads application context, then injects dependencies into the object.
     *
     * @param main            object to inject dependencies into
     */
    public void load(Object main, Class<?>... annotatedClasses) {
        loadApplicationContext(annotatedClasses);
        injectDependencies(main);
    }
}