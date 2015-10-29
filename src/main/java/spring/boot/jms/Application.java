package spring.boot.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import spring.boot.jms.model.QueueItem;
import spring.boot.jms.service.QueueService;

@SpringBootApplication
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer
{

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        /**
         * Just an example of how to use this demo*
         */
        QueueService queueService = context.getBean(QueueService.class);
        QueueItem queueItem1 = new QueueItem();
        queueItem1.setMessage("message 1");
        QueueItem queueItem2 = new QueueItem();
        queueItem2.setMessage("message 2");
        queueService.send(queueItem1);
        queueService.send(queueItem2);

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
}
