package kafka;

import domaine.price.model.Price;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@ComponentScan(basePackages = "kafka")
public class KafkaConsumerConfiguration {


    @Bean
    public ConsumerFactory<String, Price> priceConsumerFactory(@Value("${spring.kafka.bootstrap-servers}") String bootstrapAddress,
                                                               @Value("${spring.kafka.consumer.group-id}") String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        JsonDeserializer<Price> payloadJsonDeserializer = new JsonDeserializer<>();
        payloadJsonDeserializer.trustedPackages("*");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), payloadJsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Price> kafkaListenerContainerFactory(@Value("${spring.kafka.bootstrap-servers}") String bootstrapAddress,
                                                                                                @Value("${spring.kafka.consumer.group-id}") String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, Price> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(priceConsumerFactory(bootstrapAddress, groupId));
        return factory;
    }

}
