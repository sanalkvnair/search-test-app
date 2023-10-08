package dev.app.test.component;

import dev.app.test.service.SearchCounterService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class SearchCountHandler {
    private final SearchCounterService searchCounterService;

    SearchCountHandler(SearchCounterService searchCounterService) {
        this.searchCounterService = searchCounterService;
    }

    public Mono<ServerResponse> getKeywordSearchCounter(ServerRequest serverRequest) {
        return searchCounterService
                .getKeywordCounts(Mono.just(serverRequest.queryParam ("query").get()))
                .flatMap(keywordCounts -> ServerResponse.ok().bodyValue(keywordCounts))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
