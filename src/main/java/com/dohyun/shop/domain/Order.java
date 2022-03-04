package com.dohyun.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //fk가 저 아이디라고 보면됨
    private Member member;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
/*
    persist(orderItemA)
    persist(orderItemB)
    persist(orderItemC)
    persist(order)
    해야하는데 cascade 하면 persist를 전파하기때문에
    persist(order) 만하면됨.
  */

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id") //연관관계의 주인이 오더
    private Delivery delivery;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus; // 주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

//    public static void main(String[] args) {
//        Member member = new Member();
//        Order order = new Order();
//        member.getOrders().add(order);
//        order.setMember(member);
//    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    //양방향세팅 메소드
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

}
