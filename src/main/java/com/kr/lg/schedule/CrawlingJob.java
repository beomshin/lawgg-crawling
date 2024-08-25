package com.kr.lg.schedule;

import com.kr.lg.crawling.CrawlingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlingJob {

    private final CrawlingService crawlingService;

    private static int DC_MAX = 2562000;
    private static int DC_MIN = 2000000;
    private static HashSet<Integer> DC_OVERLAP = new HashSet<>();

    @Scheduled(fixedRate = 1000)
    public void crawl() throws IOException {

        var currentTime = LocalTime.now();
        var midnight = LocalTime.MIDNIGHT; // 00:00:00을 나타냅니다.
        var random = new Random();


        // 분과 초가 0이면 정각
        if (currentTime.getMinute() == 0 && currentTime.getSecond() == 0) {
            DC_MAX += 300;
            log.info("DC_MAX: " + DC_MAX);
        }
        if (currentTime.equals(midnight)) DC_OVERLAP.clear();

        for (int i=0; i < 6; i++) {
            var no = random.nextInt(DC_MAX - DC_MIN + 1) + DC_MIN;
            var url = String.format("""
                https://gall.dcinside.com/board/view/?id=leagueoflegends6&no=%s
                """, no);

            if (DC_OVERLAP.contains(no)) return;
            crawlingService.startCrawling(url);
            DC_OVERLAP.add(no);
        }
    }



}
