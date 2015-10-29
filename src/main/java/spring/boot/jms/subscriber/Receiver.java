package spring.boot.jms.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.JmsListener;
import spring.boot.jms.model.QueueItem;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class Receiver implements MessageListener {

    @JmsListener(destination = "myqueue")
    public void onMessage(Message message) {
        try {
            if (message instanceof ActiveMQTextMessage) {
                String json = ((ActiveMQTextMessage) message).getText();
                ObjectMapper mapper = new ObjectMapper();
                QueueItem queueItem = mapper.readValue(json, QueueItem.class);
                System.out.println("QueuedItem: " + queueItem.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
