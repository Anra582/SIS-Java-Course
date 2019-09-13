package io.github.bael.spring.data.service;

import io.github.bael.spring.data.entity.Book;
import io.github.bael.spring.data.entity.Customer;

import java.math.BigDecimal;

public interface BookStoreService {

    void save(Book book);

    void save(Customer customer);

    void makeDeal(Customer customer, Book book, BigDecimal price);

    BigDecimal calcSalesOfBook(Book book);

    BigDecimal calcPurchasesByCustomer(Customer customer);
}
