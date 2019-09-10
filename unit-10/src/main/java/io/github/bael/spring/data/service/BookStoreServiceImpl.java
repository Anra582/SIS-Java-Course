package io.github.bael.spring.data.service;

import io.github.bael.spring.data.data.AuthorRepository;
import io.github.bael.spring.data.data.BookRepository;
import io.github.bael.spring.data.data.CustomerRepository;
import io.github.bael.spring.data.data.PurchasedBookRepository;
import io.github.bael.spring.data.entity.Book;
import io.github.bael.spring.data.entity.Customer;
import io.github.bael.spring.data.entity.PurchasedBook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class BookStoreServiceImpl implements BookStoreService {

    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final PurchasedBookRepository purchasedBookRepository;

    public BookStoreServiceImpl(AuthorRepository authorRepository,
                                BookRepository bookRepository,
                                CustomerRepository customerRepository,
                                PurchasedBookRepository purchasedBookRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.purchasedBookRepository = purchasedBookRepository;
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void makeDeal(Customer customer, Book book, BigDecimal price) {
        PurchasedBook purchasedBook = new PurchasedBook();
        purchasedBook.setCustomer(customer);
        purchasedBook.setBook(book);
        purchasedBook.setPrice(price);

        purchasedBookRepository.save(purchasedBook);
    }

    @Override
    public BigDecimal calcSalesOfBook(Book book) {
        return purchasedBookRepository
                .findByBook(book)
                .stream()
                .map(PurchasedBook::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal calcPurchasesByCustomer(Customer customer) {
        return purchasedBookRepository
                .findByCustomer(customer)
                .stream()
                .map(PurchasedBook::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
