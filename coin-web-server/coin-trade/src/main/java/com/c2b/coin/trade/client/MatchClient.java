package com.c2b.coin.trade.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.c2b.coin.trade.vo.ExchangeVO;
import com.c2b.coin.trade.vo.ResultCallbackVO;


@FeignClient("matching-service")
public interface MatchClient {
	@PostMapping("/match/callback")
	public ResultCallbackVO callBack(@RequestParam("tradePair")String tradePair,@RequestParam("seq")String seq);
}
