package com.dohyun.shop.domain.item;

import com.dohyun.shop.domain.Category;
import com.dohyun.shop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    //다대다 관계는 중간 테이블있어야 매핑 처리가능
    @ManyToMany
    @JoinTable(name="category_item",
            joinColumns= @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name="item_id"))
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//

    /**
     * 재고 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     *
     * 재고 감소
     */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if( restStock < 0 ){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }


}
