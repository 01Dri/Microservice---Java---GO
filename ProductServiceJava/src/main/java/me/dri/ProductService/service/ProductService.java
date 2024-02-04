package me.dri.ProductService.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.dri.ProductService.config.KafkaProducerConfig;
import me.dri.ProductService.entities.Product;
import me.dri.ProductService.exception.FailedToSentProductToTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void creatProduct(String nameProduct, Double valueProduct) {
        Product product = new Product(nameProduct, valueProduct);
        try {
            String productJson = objectMapper.writeValueAsString(product);
            this.kafkaTemplate.send(KafkaProducerConfig.TOPIC_PRODUCT_PRODUCER, productJson);
            System.out.println("PRODUCT: " + productJson + " SENT TO TOPIC");
        } catch (KafkaProducerException | JsonProcessingException e) {
            throw new FailedToSentProductToTopic(e.getMessage());
        }
    }
}
