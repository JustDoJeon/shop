package com.dohyun.shop.service;

import com.dohyun.shop.domain.Address;
import com.dohyun.shop.domain.Member;
import com.dohyun.shop.domain.Order;
import com.dohyun.shop.domain.OrderStatus;
import com.dohyun.shop.domain.item.Book;
import com.dohyun.shop.domain.item.Item;
import com.dohyun.shop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        Book book = createBook();
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
        return member;
    }

    private Book createBook() {
        Book book = new Book();
        book.setName("시골jpa");
        book.setPrice(100000);
        book.setStockQuantity(10);
        return book;
    }

    @Test
    public void 주문취소() throws Exception {

    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {

    }

}