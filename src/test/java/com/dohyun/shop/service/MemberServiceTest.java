package com.dohyun.shop.service;

import com.dohyun.shop.domain.Member;
import com.dohyun.shop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //GIVEN
        Member member = new Member();
        member.setName("kim");
        //WHEN
        Long savedId = memberService.join(member);

        //THEN
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    void 중복_회원_예외() {
        //GIVEN
        Member member1 = new Member();
        member1.setName("전도현");

        Member member2 = new Member();
        member2.setName("전도현");
        //WHEN
        memberService.join(member1);
        try{
            memberService.join(member2);
        }catch (IllegalStateException e) {
            return;
        }
        //THEN
        fail("예외가 발생해야 한다.");
    }
}