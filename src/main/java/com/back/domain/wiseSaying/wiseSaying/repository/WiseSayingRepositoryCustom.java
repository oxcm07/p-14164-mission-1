package com.back.domain.wiseSaying.wiseSaying.repository;

import com.back.domain.wiseSaying.wiseSaying.entity.WiseSaying;

import java.util.Optional;

public interface WiseSayingRepositoryCustom {
    Optional<WiseSaying> findQById(Long id);
}
