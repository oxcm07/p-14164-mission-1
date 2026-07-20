package com.back.domain.wiseSaying.wiseSaying.repository;

import com.back.domain.wiseSaying.wiseSaying.entity.WiseSaying;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;
import java.util.Optional;

import static com.back.domain.wiseSaying.wiseSaying.entity.QWiseSaying.wiseSaying;

public record WiseSayingRepositoryImpl(JPAQueryFactory queryFactory) implements WiseSayingRepositoryCustom{

    @Override
    public Optional<WiseSaying> findQById(int id) {
        WiseSaying result = queryFactory
                .selectFrom(wiseSaying)
                .where(wiseSaying.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<WiseSaying> findQAll() {
        return queryFactory
                .selectFrom(wiseSaying)
                .fetch();
    }
}
