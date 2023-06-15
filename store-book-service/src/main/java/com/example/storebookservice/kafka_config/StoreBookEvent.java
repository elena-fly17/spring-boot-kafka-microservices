package com.example.storebookservice.kafka_config;

import com.example.storebookservice.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreBookEvent {

    private String bookEventStatus;
    private String message;
    private Book book;
}
