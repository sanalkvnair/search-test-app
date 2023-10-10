package dev.app.test.service.impl;

import dev.app.test.counter.KeywordHit;
import dev.app.test.service.SearchProvider;
import dev.app.test.utility.Utility;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class SearchProviderImpl implements SearchProvider {
    private final static String ASK_DOT_COM_URL = "https://www.ask.com/web?q=";
    private final static String ASK_DOT_COM_CSS_QUERY_SEL = "div.PartialResultsHeader-summary";
    private final static String SOGOU_DOT_COM_URL = "https://www.sogou.com/web?query=";
    private final static String SOGOU_DOT_COM_CSS_QUERY_SEL = "div.main .search-info .num-tips";

    @Override
    public Mono<KeywordHit> getKeywordHits(String keyword) {
        CompletableFuture<String> searchAskDotCom = searchKeywordCount(ASK_DOT_COM_URL, keyword, ASK_DOT_COM_CSS_QUERY_SEL);
        CompletableFuture<String> searchSogouDotCom = searchKeywordCount(SOGOU_DOT_COM_URL, keyword, SOGOU_DOT_COM_CSS_QUERY_SEL);
        return Mono.zip(
                Mono.fromFuture(searchAskDotCom).map(Utility::extractAskResult),
                Mono.fromFuture(searchSogouDotCom).map(Utility::extractSogouResult)
        ).map(resultCount -> new KeywordHit(keyword, resultCount.getT1() + resultCount.getT2()));
    }

    private CompletableFuture<String> searchKeywordCount(String searchUrl, String query, String queryElement) {
        try {
            Document doc = Jsoup.connect(searchUrl + query).get();
            Elements resultStats = doc.select(queryElement);
            if (!resultStats.isEmpty() && resultStats.first() != null) {
                return CompletableFuture.completedFuture(resultStats.first().text());
            }
            return CompletableFuture.completedFuture("0");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
