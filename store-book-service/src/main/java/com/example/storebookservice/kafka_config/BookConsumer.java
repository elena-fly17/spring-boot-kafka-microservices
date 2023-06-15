package com.example.storebookservice.kafka_config;

import com.example.storebookservice.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookConsumer.class);

    List<Book> storeBooks = new ArrayList<>();

    @KafkaListener(topics = "verify_book_topic", groupId = "repositoryBooks")
    void bookConsume(StoreBookEvent storeBookEvent) {

        // StoreBookEvent localStoreBookEvent = storeBookEvent;
        // storeBooks.add(localStoreBookEvent.getBook());
        // storeBooks.add(storeBookEvent.getBook());
        ////////////
        storeBooks.add(storeBookEvent.getBook());

        LOGGER.info(String.format("The book saved to the list => %s",
                storeBookEvent.getBook().toString()));

        System.out.println(storeBooks.toString());
    }
}
