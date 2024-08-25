package com.kr.lg.crawling;

import java.io.IOException;

public interface CrawlingService {

    void startCrawling(String url) throws IOException;
}
