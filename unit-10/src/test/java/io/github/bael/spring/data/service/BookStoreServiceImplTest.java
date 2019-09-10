package io.github.bael.spring.data.service;

import io.github.bael.spring.data.SpringDataApplication;
import io.github.bael.spring.data.entity.Book;
import io.github.bael.spring.data.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataApplication.class)
public class BookStoreServiceImplTest {

    @Autowired
    private BookStoreService bookStoreService;

    @Test
    public void calcSalesOfBook() {
        Book book = new Book();
        book.setDescription("Description book");
        book.setTitle("Title book");
        book.setYear(2007);
        bookStoreService.save(book);

        Customer customer = new Customer();
        customer.setName("Rick");
        customer.setAddress("Universe");
        bookStoreService.save(customer);

        Customer customer2 = new Customer();
        customer.setName("Morty");
        customer.setAddress("Universe");
        bookStoreService.save(customer2);

        BigDecimal priceOld = BigDecimal.valueOf(500);
        BigDecimal priceNew = BigDecimal.valueOf(399.99);
        BigDecimal expectedTotal = priceOld.add(priceNew);

        bookStoreService.makeDeal(customer, book, priceOld);
        bookStoreService.makeDeal(customer2, book, priceNew);

        BigDecimal actualTotal = bookStoreService.calcSalesOfBook(book);

        System.out.println(actualTotal);

        assertEquals(expectedTotal, actualTotal);

    }

    @Test
    public void calcCustomerTotalPurchases() {
        Book boringBook = new Book();
        boringBook.setDescription("Boring description of book");
        boringBook.setTitle("Boring title");
        boringBook.setYear(2007);
        bookStoreService.save(boringBook);

        Book amazingBook = new Book();
        amazingBook.setDescription("Amazing description of book");
        amazingBook.setTitle("Amazing title");
        amazingBook.setYear(1897);
        bookStoreService.save(amazingBook);

        Customer customerRick = new Customer();
        customerRick.setName("Rick");
        customerRick.setAddress("Universe");
        bookStoreService.save(customerRick);

        Customer customerMorty = new Customer();
        customerRick.setName("Morty");
        customerRick.setAddress("Universe");
        bookStoreService.save(customerMorty);

        BigDecimal priceOfAmazingBook = BigDecimal.valueOf(1445.30);
        BigDecimal priceOfBoringBook = BigDecimal.valueOf(799.99);
        BigDecimal expectedTotal = priceOfAmazingBook.add(priceOfBoringBook);

        bookStoreService.makeDeal(customerRick, amazingBook, priceOfAmazingBook);
        bookStoreService.makeDeal(customerRick, boringBook, priceOfBoringBook);

        BigDecimal actualTotal = bookStoreService.calcPurchasesByCustomer(customerRick);

        System.out.println(actualTotal);

        assertEquals(expectedTotal, actualTotal);
    }
}