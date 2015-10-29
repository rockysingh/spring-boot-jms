package spring.boot.jms.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.boot.jms.subscriber.Receiver;
import spring.boot.jms.subscriber.Sender;

import javax.jms.ConnectionFactory;

@Configuration
@ConfigurationProperties(prefix="queue")
public class ActiveMQConfig {
    
    private String host;

    @Bean()
    ConnectionFactory connectionFactory(){
        return new ActiveMQConnectionFactory(host);
    }

    @Bean
    Sender sender(){
        return new Sender();
    }

    @Bean
    Receiver receiver(){
        return new Receiver();
    }

    public void setHost(String host) { this.host = host; }
}
