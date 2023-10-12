package dev.app.test.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    /**
     * <pre>The method extract result count frmo the ask.com search engine, the result is retrieved in below format: </pre>
     * <h4>1-10 of 5,480,000,000 results</h4>
     * <pre>Taking the total count from the input String and converting to Long</pre>
     * @param input String
     * @return Long
     */
    public static Long extractAskResult(String input) {
        if (input != null) {
            String regexPattern = "(\\d{1,3}(,\\d{3})*)-(\\d{1,3}(,\\d{3})*)(?:\\s+of\\s+(\\d{1,3}(,\\d{3})*))?(?:\\s+results)?";

            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                String totalResults = matcher.group(5);
                return totalResults != null ? Long.parseLong(totalResults.replaceAll(",","")) : 0L;
            }
        }

        return 0L;
    }

    /**
     * <pre>The method extracts result count from the sogou search engine. The format of result retrieved is as below</pre>
     *  <h4>搜狗已为您找到约1,652,402条相关结果</h4>
     * <pre>The result contain minimum 13 chinese character hence checking if it is more than 13 then extract value.</pre>
     *
     * @param input String
     * @return Long
     */
    public static Long extractSogouResult(String input) {
        if (input != null) {
            String regexPattern = "搜狗已为您找到约(\\d{1,3}(,\\d{3})*)(?:条)?相关结果";

            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                String totalResults = matcher.group(1);
                return totalResults != null ? Long.parseLong(totalResults.replaceAll(",","")) : 0L;
            }
        }
        return 0L;
    }
}
