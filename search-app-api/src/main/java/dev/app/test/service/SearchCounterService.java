package dev.app.test.service;

import dev.app.test.counter.SearchCounter;
import reactor.core.publisher.Mono;

public interface SearchCounterService {
    Mono<SearchCounter> getKeywordCounts(Mono<String> keywords);
}
