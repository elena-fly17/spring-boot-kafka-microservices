package com.example.checkbookservice.kafka_config;

import com.example.checkbookservice.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckBookEvent {

    private String bookEventStatus;
    private String message;
    private Book book;
}
