package com.example.checkbookservice.kafka_config;

import com.example.checkbookservice.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookConsumerAndProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookConsumerAndProducer.class);

    @Autowired
    @Qualifier("getKafkaTemplate")
    private KafkaTemplate<String, CheckBookEvent> kafkaTemplate;

    @KafkaListener(topics = "new_book_topic",
            groupId = "checkBooks")
    void bookConsumeAndProduce(CheckBookEvent bookEvent) {

        com.example.checkbookservice.model.Book checkedBook =
                new Book(bookEvent.getBook().getId(),
                bookEvent.getBook().getName(),
                bookEvent.getBook().getDescription(),
                "CHECKED",
                bookEvent.getBook().getPrice());
        CheckBookEvent checkEvent =
                new CheckBookEvent("CHECKED",
                "Book already checked",
                checkedBook);

        kafkaTemplate.send("new_book_checked_topic", checkEvent);

        LOGGER.info(String.format("Checked book send to kafka => %s",
                checkEvent.toString()));
    }
}
