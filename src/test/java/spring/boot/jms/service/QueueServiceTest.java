package spring.boot.jms.service;

import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import spring.boot.jms.Application;
import spring.boot.jms.model.QueueItem;

import javax.jms.ConnectionFactory;
import javax.jms.TextMessage;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
@WebAppConfiguration
public class QueueServiceTest {

    @Autowired
    private QueueService queueService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${queue.name}")
    private String name;

    @Test
    public void test_send_to_queue(){

        try {
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
            JMXConnector jmxc = JMXConnectorFactory.connect(url);
            MBeanServerConnection mBeanServerConnection = jmxc.getMBeanServerConnection();
            ObjectName queueName = new ObjectName("org.apache.activemq:BrokerName=localhost,Type=Queue,Destination=" + name);
            QueueViewMBean proxy=(QueueViewMBean) MBeanServerInvocationHandler.newProxyInstance(mBeanServerConnection, queueName, QueueViewMBean.class, true);
            proxy.purge();


            QueueItem queueItem = new QueueItem();
            queueItem.setMessage("message 1");
            queueService.send(queueItem);
            TextMessage message = (TextMessage) jmsTemplate.receive(name);
            String json = message.getText().trim();
            System.out.println("json" + json);
            String matched = "{\n  \"id\" : \"" + queueItem.getId() + "\"\n}";
            assertEquals(json, matched);
        } catch (Exception e){

        }

    }

}
