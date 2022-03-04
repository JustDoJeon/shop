package com.dohyun.shop.member.repository;

import com.dohyun.shop.member.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;  //스프링부트에선 이렇게 하면 엔티티매니저 주입됨

    //저장
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    //조회
    public Member find(Long id){
        return em.find(Member.class, id);
    }

}
