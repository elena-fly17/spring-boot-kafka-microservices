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

    @KafkaListener(topics = "new_book_topic", groupId = "checkBooks")
    void bookConsumeAndProduce(CheckBookEvent checkBookEvent) {

        Book checkedBook = Book.builder()
                .id(checkBookEvent.getBook().getId())
                .name(checkBookEvent.getBook().getName())
                .description(checkBookEvent.getBook().getDescription())
                .status("CHECKED")
                .price(checkBookEvent.getBook().getPrice())
                .build();

        CheckBookEvent checkEvent = CheckBookEvent.builder()
                .bookEventStatus("CHECKED")
                .message("Book already checked")
                .book(checkedBook).build();

        kafkaTemplate.send("verify_book_topic", checkEvent);

        LOGGER.info(String.format("Checked book send to kafka => %s",
                checkEvent.toString()));
    }
}
