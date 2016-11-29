package org.huseyin.metrics;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huseyin on 25/11/2016.
 */
@Service
public class CurrencyService implements MetricsService {

    private GaugeService gaugeService;

    @Autowired
    public CurrencyService(GaugeService gaugeService){
        this.gaugeService = gaugeService;
        this.readCurrency("GBPUSD");
    }

    @Override
    @Scheduled(fixedDelay = 60000)
    public void read() {
        /**
        String[] lookup = {
                "GBPUSD", "GBPEUR"
        };
        ExecutorService executor = Executors.newFixedThreadPool(20);
        List<CompletableFuture<Currency>> currencies = Arrays.stream(lookup)
         **/
    }

    private void readCurrency(String currency) {

        Document doc;
        Double currencyValue;
        try {
            doc = Jsoup.connect(String.format("https://www.google.co.uk/search?as_q=%s", currency))
                    .timeout(5000)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36")
                    .get();
            Element input = doc.getElementById("pair_targ_input");
            if(input != null && input.val() != null) {
                currencyValue = Double.parseDouble(input.val());
                System.out.println(input.val());
                gaugeService.submit(String.format("currency.%s", currency), currencyValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Currency {
        private String key;
        private Double value;

        public Currency(String key, Double value) {
            this.key = key;
            this.value = value;
        }
    }
}
