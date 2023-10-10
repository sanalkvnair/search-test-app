package dev.app.test.counter;

import java.util.Map;

public record SearchCounter(Long totalHits, Map<String, Long> keywordHits) {
}
