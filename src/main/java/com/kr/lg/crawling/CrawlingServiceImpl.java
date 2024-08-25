package com.kr.lg.crawling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kr.lg.dto.EnrollPositionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlingServiceImpl implements CrawlingService {

    @Value("${cground.url}")
    private String cgroundUrl;

    private final RestTemplate restTemplate;

    @Override
    @Async
    public void startCrawling(String url) throws IOException {
        try {
            var random = new Random();
            Document document = Jsoup.connect(url).get();
            var title = document.select(".title_subject");
            var elem = document.select(".write_div");
            var nickName = document.select(".nickname");
            var type = random.nextInt(5);

            var requset = EnrollPositionRequest.builder()
                    .id(nickName.get(0).text())
                    .password("123123")
                    .title(title.get(0).text())
                    .content(elem.get(0).text())
                    .lineType(type)
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            RequestEntity<?> httpRequestEntity = new RequestEntity<>(requset, headers, HttpMethod.POST, URI.create(cgroundUrl));

            restTemplate.exchange(httpRequestEntity, String.class);
        } catch (HttpStatusException e) {
            log.error("게시글 미존재");
        }
    }

}
