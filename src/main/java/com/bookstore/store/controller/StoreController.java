package com.bookstore.store.controller;

import com.bookstore.store.model.Transaction;
import com.bookstore.store.model.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StoreController {

    @Autowired
    private TransactionRepository repository;

    @Value("${spring.bookmicroservice.path}")
    private String bookServicePath;


    // Find
    @GetMapping("/store/books")
    List<Book>  listBooks() {
        RestTemplate rt = new RestTemplate();
        List<Book> books = rt.getForObject(bookServicePath, new ArrayList<Book>().getClass());
        return books;

    }

    // Find
    @GetMapping("/store/books/{id}")
    Book findOne(@PathVariable Long id) {
        RestTemplate rt = new RestTemplate();
        Book book = rt.getForObject(bookServicePath + "/" + id, Book.class);
        return book;
    }

    // Save
    @PostMapping("/store/buy")
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    Transaction buyBook(@RequestParam Long id, @RequestParam int quantity) {
        Transaction t = new Transaction(id, quantity);
        return repository.save(t);
    }
}
