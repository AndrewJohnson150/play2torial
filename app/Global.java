import org.springframework.context.APplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import play.Application;
import play.GlobalSettings;

import configs.AppConfig;

public class Global extends GlobalSettings {
    private ApplicationContext ctx;

    @Override
    public void onStart(Application app) {
        super.onStart(app);
        ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Override
    public <A> A getControllerInstance(Class<A> clazz) {
        return ctx.getBean(clazz);
    }
}