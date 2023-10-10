package dev.app.test.service.impl;

import dev.app.test.counter.KeywordHit;
import dev.app.test.counter.SearchCounter;
import dev.app.test.service.SearchCounterService;
import dev.app.test.service.SearchProvider;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchCounterServiceImpl implements SearchCounterService {

    private final SearchProvider searchProvider;

    SearchCounterServiceImpl(SearchProvider searchProvider) {
        this.searchProvider = searchProvider;
    }

    @Override
    public Mono<SearchCounter> getKeywordCounts(Mono<String> query) {
        return query
                .map(this::splitKeywords)
                .flatMapMany(keyword -> keyword)
                .flatMap(searchProvider::getKeywordHits)
                .collectList()
                .map(kh -> {
                    final Map<String, Long> res = new HashMap<>();
                    long totalHits = 0;
                    for (KeywordHit keywordHit : kh) {
                        long i = (res.get(keywordHit.keyword()) != null ? res.get(keywordHit.keyword()) : 0) + keywordHit.totalHits();
                        totalHits += i;
                        res.put(keywordHit.keyword(), i);
                    }
                    return new SearchCounter(totalHits, res);
                });

    }

    private Flux<String> splitKeywords(String query) {
        return Flux.just(query)
                .flatMap(k -> Flux.fromArray(k.split("\\s+")));
    }

}
