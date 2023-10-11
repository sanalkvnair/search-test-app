package dev.app.test.component;

import dev.app.test.counter.SearchCounter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SearchCountRouter {
    @Bean
    @RouterOperations(
            @RouterOperation(path="/app/search?query={query}",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanMethod = "getKeywordSearchCounter",
                    operation = @Operation(operationId = "getKeywordSearchCounter",
                            responses = {
                            @ApiResponse(responseCode = "200",
                                    description = "Search counter successful",
                                    content = @Content(schema = @Schema(implementation = SearchCounter.class))),
                                    @ApiResponse(responseCode = "400", description = "Query param missing.")
                            },
                            parameters = {@Parameter(in = ParameterIn.QUERY, name = "query")}
                    )
            )
    )
    RouterFunction<ServerResponse> routes(SearchCountHandler searchCountHandler) {
        return route(RequestPredicates.GET("/app/search"), searchCountHandler::getKeywordSearchCounter);
    }

    @Bean
    CorsWebFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
