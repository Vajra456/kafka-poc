package poc.uidai.com.kafkapoc.util.kafka.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import poc.uidai.com.kafkapoc.controller.KafkaPocController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;


@Component
public class KafkaHealthIndicator implements HealthIndicator {

    private static final Logger LOGGER = Logger.getLogger(KafkaPocController.class.getName());

    private KafkaTemplate<String, String> kafka;

    public KafkaHealthIndicator(KafkaTemplate<String, String> kafka) {
        this.kafka = kafka;
    }

    /**
     * Return an indication of health.
     *
     * @return the health for
     */
    @Override
    public Health health() {
        try {
            kafka.send("kafka-health-indicator", "❥").get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return Health.down(e).build();
        }
        return Health.up().build();
    }
}
