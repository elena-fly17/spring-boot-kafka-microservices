package com.example.creationbookservice;

import com.example.creationbookservice.kafka_config.BookEvent;
import com.example.creationbookservice.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@EnableScheduling
public class BookGenerator {

    private KafkaTemplate<String, BookEvent> kafkaTemplate;
    private Random random;

    public BookGenerator(KafkaTemplate<String, BookEvent> kafkaTemplate,
                         Random random) {
        this.kafkaTemplate = kafkaTemplate;
        this.random = random;
    }

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CreationBookServiceApplication.class);

    @Scheduled(fixedDelay = 4000)
    public void bookGenerate() {
        StringBuilder builderName = new StringBuilder();
        StringBuilder builderDescription = new StringBuilder();
        LocalDateTime localDateTime = LocalDateTime.now();

        builderName.append("Book ").append(localDateTime);
        builderDescription.append("About Book ").append(localDateTime);

        Book book = new Book(random.nextInt(400),
                builderName.toString(),
                builderDescription.toString(),
                "UNCHECKED",
                random.nextInt(400) * 10 + 1);

        BookEvent bookEvent = new BookEvent("WAITING FOR VERIFICATION",
                "The book must be checked",
                book);

        kafkaTemplate.send("new_book_topic", bookEvent);

        LOGGER.info(String.format("Book event => %s", bookEvent.toString()));
    }
}
