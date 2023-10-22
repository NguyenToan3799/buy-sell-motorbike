package buysellmoto.core.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

public class KafkaConfig {

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(kafkaTemplate().getProducerFactory());
    }

//    @KafkaListener(topics = "test")
//    public void listen(String message) {
//        System.out.println("Received Messasge: " + message);
//    }
//

//    @KafkaListener(topics = "test-topic")
//    public void listen2(String message) {
//        System.out.println("Received Messasge: " + message);
//    }

}
