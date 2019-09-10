package io.github.bael.spring.data.service;

import io.github.bael.spring.data.SpringDataApplication;
import io.github.bael.spring.data.entity.Book;
import io.github.bael.spring.data.entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataApplication.class)
public class BookStoreServiceImplTest {

    @Autowired
    private BookStoreService bookStoreService;

    private Book boringBook;
    private Book amazingBook;
    private Customer customerRick;
    private Customer customerMorty;

    @Before
    public void init() {
        boringBook = new Book();
        boringBook.setDescription("Boring description of book");
        boringBook.setTitle("Boring title");
        boringBook.setYear(2007);
        bookStoreService.save(boringBook);

        amazingBook = new Book();
        amazingBook.setDescription("Amazing description of book");
        amazingBook.setTitle("Amazing title");
        amazingBook.setYear(1897);
        bookStoreService.save(amazingBook);

        customerRick = new Customer();
        customerRick.setName("Rick");
        customerRick.setAddress("Universe");
        bookStoreService.save(customerRick);

        customerMorty = new Customer();
        customerMorty.setName("Morty");
        customerMorty.setAddress("Universe");
        bookStoreService.save(customerMorty);
    }

    @Test
    public void calcSalesOfBook() {

        BigDecimal priceOld = BigDecimal.valueOf(500);
        BigDecimal priceNew = BigDecimal.valueOf(399.99);
        BigDecimal expectedTotal = priceOld.add(priceNew);

        bookStoreService.makeDeal(customerRick, amazingBook, priceOld);
        bookStoreService.makeDeal(customerMorty, amazingBook, priceNew);

        BigDecimal actualTotal = bookStoreService.calcSalesOfBook(amazingBook);

        assertEquals(expectedTotal, actualTotal);

    }

    @Test
    public void calcPurchasesByCustomer() {

        BigDecimal priceOfAmazingBook = BigDecimal.valueOf(1445.30);
        BigDecimal priceOfBoringBook = BigDecimal.valueOf(799.99);
        BigDecimal expectedTotal = priceOfAmazingBook.add(priceOfBoringBook);

        bookStoreService.makeDeal(customerRick, amazingBook, priceOfAmazingBook);
        bookStoreService.makeDeal(customerRick, boringBook, priceOfBoringBook);

        BigDecimal actualTotal = bookStoreService.calcPurchasesByCustomer(customerRick);

        assertEquals(expectedTotal, actualTotal);
    }
}