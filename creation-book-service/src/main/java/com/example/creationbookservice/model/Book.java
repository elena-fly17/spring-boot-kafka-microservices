package com.example.creationbookservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private long id;
    private String name;
    private String description;
    private BookStatus status;
    private double price;
}
