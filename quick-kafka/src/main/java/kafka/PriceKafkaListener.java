package kafka;

import domaine.price.model.Price;
import domaine.price.service.PriceService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "prices", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
public class PriceKafkaListener {

    private final PriceService priceService;

    public PriceKafkaListener(PriceService priceService) {
        this.priceService = priceService;
    }

    @KafkaHandler
    public void consumePriceEvent(Price priceEvent) {
        priceService.updatePrice(priceEvent);
    }

}
