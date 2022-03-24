package com.dohyun.shop.service;

import com.dohyun.shop.domain.Address;
import com.dohyun.shop.domain.Member;
import com.dohyun.shop.domain.Order;
import com.dohyun.shop.domain.OrderStatus;
import com.dohyun.shop.domain.item.Book;
import com.dohyun.shop.domain.item.Item;
import com.dohyun.shop.exception.NotEnoughStockException;
import com.dohyun.shop.repository.OrderRepository;
import com.sun.istack.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {

        //given
        Member member = createMember();
        em.persist(member);
        Book book = createBook("jpa책",10000,100);
        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getOrderStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(8,book.getStockQuantity());

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stock) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stock);
        em.persist(book);
        return book;
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("jpa 휴",100000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(),item.getId(),orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL,getOrder.getOrderStatus());
        assertEquals(10,item.getStockQuantity());
    }

    @Test
    public void 상품주문_재고수량초과() throws NotEnoughStockException {
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA" , 10000, 10);
        int orderCount = 8;

        //WHEN
        orderService.order(member.getId(),item.getId(),orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야한다.");
//        assertEquals(2,item.getStockQuantity());
    }


}