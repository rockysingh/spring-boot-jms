package spring.boot.jms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.boot.jms.model.QueueItem;
import spring.boot.jms.subscriber.Sender;

@Service
public class QueueService {
    
    @Value("${queue.name}")
    private String name;
    
    @Autowired
    private Sender sender;
    
    public void send(QueueItem queueItem){
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(queueItem);
            sender.send(json, name);
            System.out.println(json);
        } catch (JsonProcessingException jpe){
            jpe.printStackTrace();
        }
    }

}
