package tech.lmru.integration.adeo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DeliveryRepositoryQueueListener {
    //@RabbitListener(queues = "tms.deliveryModified.all")
    public void processMessage(String content) {
        System.out.println(content);
    }

}
