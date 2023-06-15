package com.example.creationbookservice.kafka_config;

import com.example.creationbookservice.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEvent {

    private String bookEventStatus;
    private String message;
    private Book book;
}
