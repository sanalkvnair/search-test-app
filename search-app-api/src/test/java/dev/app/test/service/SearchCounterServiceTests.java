package dev.app.test.service;

import dev.app.test.counter.KeywordHit;
import dev.app.test.counter.SearchCounter;
import dev.app.test.service.impl.SearchCounterServiceImpl;
import dev.app.test.service.impl.SearchProviderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class SearchCounterServiceTests {

    @InjectMocks
    private SearchCounterServiceImpl searchCounterService;

    @Mock
    private SearchProviderImpl searchProvider;

    @BeforeEach
    void setup() {
        when(searchProvider.getKeywordHits("Hello")).thenReturn(Mono.just(new KeywordHit("Hello", 5L)));
        when(searchProvider.getKeywordHits("world")).thenReturn(Mono.just(new KeywordHit("world", 5L)));
        when(searchProvider.getKeywordHits("from")).thenReturn(Mono.just(new KeywordHit("from", 10L)));
        when(searchProvider.getKeywordHits("java")).thenReturn(Mono.just(new KeywordHit("java", 20L)));
    }

    @Test
    @DisplayName("verify searching of single keyword")
    public void getKeywordCount() {
        String keyword = "Hello";

        Mono<SearchCounter> searchCounterMono = searchCounterService.getKeywordCounts(Mono.just(keyword));
        StepVerifier
                .create(searchCounterMono)
                .consumeNextWith(searchCounter -> {
                    assertEquals(5L, searchCounter.totalHits());
                    assertTrue(searchCounter.keywordHits().containsKey(keyword));
                    assertEquals(5L, searchCounter.keywordHits().get(keyword));
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("verify searching of multiple keywords")
    public void getMultiKeywordCount() {
        String keywords = "Hello world from java";
        Mono<SearchCounter> searchCounterMono = searchCounterService.getKeywordCounts(Mono.just(keywords));
        StepVerifier
                .create(searchCounterMono)
                .consumeNextWith(searchCounter -> {
                    assertEquals(40L, searchCounter.totalHits());
                    assertTrue(searchCounter.keywordHits().containsKey("java"));
                    assertEquals(20L, searchCounter.keywordHits().get("java"));
                })
                .verifyComplete();
    }
}
