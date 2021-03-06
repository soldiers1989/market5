package com.c2b.coin.matching;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import com.c2b.coin.common.Constants;
import com.c2b.coin.matching.util.HttpClientUtils;


/**
 * Hello world!
 *
 */
@SpringCloudApplication
@EnableHystrix
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.c2b.coin"})
@RefreshScope
@EnableEurekaClient
@EnableFeignClients
@EnableScheduling
public class MatchingApplication {
	public final static String MATCHING_QUEUE="com.coin.match"; 

	public static void main(String[] args) {
		SpringApplication.run(MatchingApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		CloseableHttpClient httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient();
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		return restTemplate;
	}

	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setKeepAliveSeconds(60);
		threadPoolTaskExecutor.setMaxPoolSize(50);
		threadPoolTaskExecutor.setCorePoolSize(20);
		threadPoolTaskExecutor.setQueueCapacity(100);
		return threadPoolTaskExecutor;
	}

	@Bean
	public Queue matchingQueue() {
		return new ActiveMQQueue(MATCHING_QUEUE);
	}
	
	
	@Bean
	public Queue tradeOnceQueue() {
		return new ActiveMQQueue(Constants.TRADE_SUCCESS_DEAL_QUEUE_DESTINATION);
	}
	
	@Bean
	public Queue endMatchQueue() {
		return new ActiveMQQueue( Constants.TRADE_END_QUEUE_DESTINATION);
	}
}