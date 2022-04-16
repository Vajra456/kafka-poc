package poc.uidai.com.kafkapoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import poc.uidai.com.kafkapoc.controller.KafkaPocController;
import poc.uidai.com.kafkapoc.data.repository.GreetingRepo;
import poc.uidai.com.kafkapoc.data.entity.Greeting;
import poc.uidai.com.kafkapoc.service.KafkaPoCService;
import poc.uidai.com.kafkapoc.util.kafka.KafkaProducerUtil;

//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import java.util.logging.Logger;

@Service
public class KafkaPoCServiceImpl implements KafkaPoCService {
    private static final Logger LOGGER = Logger.getLogger(KafkaPocController.class.getName());

    @Autowired
    private KafkaProducerUtil kafkaProducerUtil = new KafkaProducerUtil();

    @Autowired
    private GreetingRepo greetingRepo;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendMessage(Greeting greeting) throws Exception {
        try {
            LOGGER.info("Saving message to database");
            greetingRepo.save(greeting);
            LOGGER.info("Sending message to Kafka Topic");
            kafkaProducerUtil.sendMessage(greeting);
        } catch (Exception e) {
            LOGGER.severe("Exception occurred while sending message to Kafka Topic");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}
