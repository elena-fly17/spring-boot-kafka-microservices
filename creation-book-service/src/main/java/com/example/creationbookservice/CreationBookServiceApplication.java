package com.example.creationbookservice;

import com.example.creationbookservice.kafka_config.BookEvent;
import com.example.creationbookservice.model.Book;
import com.example.creationbookservice.model.BookStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class CreationBookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreationBookServiceApplication.class, args);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CreationBookServiceApplication.class);

	@Bean
	CommandLineRunner commandLineRunner(KafkaTemplate<String, BookEvent> kafkaTemplate) {
		return args -> {
			for (int i = 1; i < 4; i++) {
				StringBuilder builderName = new StringBuilder();
				StringBuilder builderDescription = new StringBuilder();
				builderName.append("Book ").append(i);
				builderDescription.append("About Book ").append(i);

				Book book = new Book(i, builderName.toString(), builderDescription.toString(),
						BookStatus.UNCHECKED, i * 100);

				BookEvent bookEvent = new BookEvent();
				bookEvent.setBookEventStatus("WAITING FOR VERIFICATION");
				bookEvent.setMessage("The book must be checked");
				bookEvent.setBook(book);

				kafkaTemplate.send("book_topics", bookEvent);

				LOGGER.info(String.format("Book event => %s", bookEvent.toString()));
			}
		};
	}
}
