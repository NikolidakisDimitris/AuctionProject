package com.nikolidakis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableConfigurationProperties({AuctionImage.class})
public class SpringBootRun {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRun.class, args);
    }


//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
//        multipart.setMaxUploadSize(3 * 1024 * 1024);
//        return multipart;
//    }
//
//    @Bean
//    @Order(0)
//    public MultipartFilter multipartFilter() {
//        MultipartFilter multipartFilter = new MultipartFilter();
//        multipartFilter.setMultipartResolverBeanName("multipartResolver");
//        return multipartFilter;
//    }

}
