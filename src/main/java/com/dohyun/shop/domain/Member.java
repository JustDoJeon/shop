package com.dohyun.shop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;
    // 한 멤버가 여러개의 주문을 함, 읽기전용으로됨 나는 그냥 order테이블 member의 거울
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();




}
