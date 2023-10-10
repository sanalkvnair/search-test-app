package dev.app.test.component;

import dev.app.test.service.SearchCounterService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
        return serverRequest
                .queryParam("query")
                .map(query -> {
                    if(!StringUtils.hasText(query))
                        return ServerResponse.badRequest().bodyValue("Missing search keyword");
                    return searchCounterService
                            .getKeywordCounts(Mono.just(query))
                            .flatMap(keywordCounts -> ServerResponse.ok().bodyValue(keywordCounts))
                            .switchIfEmpty(ServerResponse.notFound().build());
                })
                .orElseGet(() -> ServerResponse.badRequest().bodyValue("Query param missing."));
    }

}
