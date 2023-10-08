package dev.app.test.counter;

import java.util.Map;

public record SearchCounter(int totalHits, Map<String, Integer> keywordHits) {
}
