package dev.app.test.service;

import dev.app.test.counter.KeywordHit;
import reactor.core.publisher.Mono;

public interface SearchProvider {
    Mono<KeywordHit> getKeywordHits(String keyword);
}
