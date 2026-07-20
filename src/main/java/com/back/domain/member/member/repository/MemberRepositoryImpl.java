package com.back.domain.member.member.repository;

import com.back.domain.member.member.entity.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.back.domain.member.member.entity.QMember.member;

public record MemberRepositoryImpl(JPAQueryFactory queryFactory) implements MemberRepositoryCustom {

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

    @Override
    public Page<Member> findQPaged(String kwType, String kw, Pageable pageable) {
        JPAQuery<Member> query = queryFactory
                .selectFrom(member)
                .where(searchCondition(kwType, kw));

        for (Sort.Order order : pageable.getSort()) {
            switch (order.getProperty()) {
                case "id" -> query.orderBy(order.isAscending() ? member.id.asc() : member.id.desc());
                case "username" -> query.orderBy(order.isAscending() ? member.username.asc() : member.username.desc());
                case "nickname" -> query.orderBy(order.isAscending() ? member.nickname.asc() : member.nickname.desc());
            }
        }

        List<Member> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalQuery = queryFactory
                .select(member.count())
                .from(member)
                .where(searchCondition(kwType, kw));

        return PageableExecutionUtils.getPage(results, pageable, () -> {
            Long total = totalQuery.fetchOne();
            return total != null ? total : 0L;
        });
    }

    private BooleanExpression searchCondition(String kwType, String kw) {
        if (!StringUtils.hasText(kwType) || !StringUtils.hasText(kw)) {
            return null;
        }

        return switch (kwType) {
            case "username" -> member.username.contains(kw);
            case "nickname" -> member.nickname.contains(kw);
            case "all" -> member.username.contains(kw).or(member.nickname.contains(kw));
            default -> null;
        };
    }
}
