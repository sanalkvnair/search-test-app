package dev.app.test.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SearchCountRouter {
    @Bean
    RouterFunction<ServerResponse> routes(SearchCountHandler searchCountHandler) {
        return route(RequestPredicates.GET("/app/search"), searchCountHandler::getKeywordSearchCounter);
    }
}
