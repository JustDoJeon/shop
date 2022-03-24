package com.dohyun.shop.repository;

import com.dohyun.shop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    //상품 저장 - 아이템 저장하기전까지 아이디값이 없으니깐 null일 것이고 신규로 등록하는것
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else{
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        System.out.println("id : " + id);
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i ",Item.class).getResultList();
    }
}
