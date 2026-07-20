package com.back.domain.member.member.service;

import com.back.domain.member.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("findPaged: all, 1")
    void t1() {
        Page<Member> page = memberService.findPaged("all", "1", PageRequest.of(0, 10, Sort.by("id").ascending()));
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("findPaged: username, 유저")
    void t2() {
        Page<Member> page = memberService.findPaged("username", "유저", PageRequest.of(0, 10, Sort.by("username").ascending()));
        assertThat(page.getTotalElements()).isEqualTo(0);
    }

    @Test
    @DisplayName("findPaged: nickname, 유저")
    void t3() {
        Page<Member> page = memberService.findPaged("nickname", "유저", PageRequest.of(0, 10, Sort.by("nickname").ascending()));
        assertThat(page.getTotalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("findPaged: username, user")
    void t4() {
        Page<Member> page = memberService.findPaged("username", "user", PageRequest.of(0, 10, Sort.by("username").ascending()));
        assertThat(page.getTotalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("findPaged: nickname, user")
    void t5() {
        Page<Member> page = memberService.findPaged("nickname", "user", PageRequest.of(0, 10, Sort.by("nickname").ascending()));
        assertThat(page.getTotalElements()).isEqualTo(0);
    }

    @Test
    @DisplayName("findPaged: username, user, 2")
    void t6() {
        Page<Member> page = memberService.findPaged("username", "user", PageRequest.of(0, 2, Sort.by("username").ascending()));
        assertThat(page.getContent().size()).isEqualTo(2);
        assertThat(page.getTotalElements()).isEqualTo(3);
    }
}
