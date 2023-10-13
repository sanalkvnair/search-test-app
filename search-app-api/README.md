# Search App API
This is a Spring Boot based reactive application using Webflux framework. The rest API will search for provided keywords and search on two search engines and will provide total hits of each keyword on those search engines. The search engines used are ask.com and sogou.com and method to extract total result is web scraping the result using the Jsoup library.


### Steps to run
1. Build the project using `gradlew build` or `./gradlew build`.
2. Run using `gradlew bootRun` or `./gradlew bootRun`
3. The rest API is accessible at http://localhost/app/search?query={keyword}


