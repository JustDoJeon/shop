package com.dohyun.shop.domain;

import com.dohyun.shop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;


    //다대다 관계는 중간 테이블있어야 매핑 처리가능 이거슨 Item의반대쪽입장
    @ManyToMany
    @JoinTable(name="category_item",
            joinColumns= @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name="item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy =  "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

}
