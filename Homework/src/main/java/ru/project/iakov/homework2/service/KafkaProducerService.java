package ru.project.iakov.homework2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.project.iakov.homework2.dto.UserEvent;

@Service
public class KafkaProducerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String TOPIC = "user-events";

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendUserEvent(UserEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            LOGGER.info(String.format("Сообщение: %s", message));
            kafkaTemplate.send(TOPIC, message);
        } catch (JsonProcessingException e) {
            LOGGER.error("Ошибка сериализации UserEvent: " + e.getMessage());
        }




    }
}