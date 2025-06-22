package ru.project.iakov.homework2.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.project.iakov.homework2.dto.UserEvent;
import ru.project.iakov.homework2.service.EmailService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@AutoConfigureMockMvc
@SpringBootTest
public class KafkaConsumerTest {

    @Autowired
    private KafkaConsumerService kafkaConsumer;

    @MockitoBean
    private EmailService emailService;

    @Test
    public void testKafkaConsumer_sendsEmailOnCreatedEvent() {
        UserEvent event = UserEvent.builder()
                .email("lysenko_iakov@yahoo.com")
                .subject("Тест: создание")
                .eventType("CREATED")
                .build();

        kafkaConsumer.listen(event);

        verify(emailService, times(1)).sendEmail(eq("lysenko_iakov@yahoo.com"), anyString(), contains("создан"));
    }

    @Test
    public void testKafkaConsumer_sendsEmailOnDeletedEvent() {
        UserEvent event = UserEvent.builder()
                .email("lysenko_iakov@yahoo.com")
                .subject("Тест: удаление")
                .eventType("DELETED")
                .build();

        kafkaConsumer.listen(event);

        verify(emailService, times(1)).sendEmail(eq("lysenko_iakov@yahoo.com"), anyString(), contains("удалён"));
    }
}