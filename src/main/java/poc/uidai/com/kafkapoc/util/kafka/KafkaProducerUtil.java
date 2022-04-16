package poc.uidai.com.kafkapoc.util.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import poc.uidai.com.kafkapoc.controller.KafkaPocController;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import poc.uidai.com.kafkapoc.data.entity.Greeting;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class KafkaProducerUtil {

    private static final Logger LOGGER = Logger.getLogger(KafkaPocController.class.getName());

    @Autowired
    private KafkaTemplate<String, Greeting> kafkaTemplate;


    @Value(value = "${message.topic.name}")
    private String topicName;


    /**
     * Send message to Kafka topic
     *
     * @param greeting
     */
    public void sendMessage(Greeting greeting) throws Exception {

        try {
            ListenableFuture<SendResult<String, Greeting>> future = kafkaTemplate.send(topicName, greeting);


            future.addCallback(new ListenableFutureCallback<SendResult<String, Greeting>>() {

                @Override
                public void onSuccess(SendResult<String, Greeting> result) {
                    LOGGER.info("Sent message=[" + greeting.getMessage() + "] with offset=[" + result.getRecordMetadata()
                            .offset() + "]");
                }

                @Override
                public void onFailure(Throwable ex) {
                    LOGGER.log(Level.SEVERE, "Unable to send message=[" + greeting.getMessage() + "] due to : " + ex.getMessage(), ex);
                }
            });
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Unable to send message due to : " + ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage());
        }
    }
}
