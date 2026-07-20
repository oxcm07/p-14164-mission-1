package com.back.domain.member.member.repository;

import com.back.domain.member.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.Optional;

import static com.back.domain.member.member.entity.QMember.member;

public class MemberRepositoryImpl implements MemberRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public long qCount() {
        Long count = queryFactory
                .select(member.count())
                .from(member)
                .fetchOne();

        return count != null ? count : 0L;
    }

    @Override
    public Optional<Member> findQByUsername(String username) {
        Member result = queryFactory
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
