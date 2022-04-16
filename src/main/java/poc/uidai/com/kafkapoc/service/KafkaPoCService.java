package poc.uidai.com.kafkapoc.service;

import org.springframework.stereotype.Service;
import poc.uidai.com.kafkapoc.data.entity.Greeting;

@Service
public interface KafkaPoCService {

    public void sendMessage(Greeting message) throws Exception;
}
