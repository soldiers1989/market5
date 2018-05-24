package com.c2b.coin.trade.api;

import com.c2b.coin.common.AjaxResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("coin-trade")
public interface ConsignationClient {

  @RequestMapping(value = "/client/trade/limitPriceBuyConsignation", method = RequestMethod.POST)
  AjaxResponse limitPriceBuyConsignation(@RequestParam("userId") long userId, @RequestParam("bizType") String bizType, @RequestParam("consignationPrice") String consignationPrice, @RequestParam("consignationCount") String consignationCount);

  @RequestMapping(value = "/client/trade/limitPriceSellConsignation", method = RequestMethod.POST)
  AjaxResponse limitPriceSellConsignation(@RequestParam("userId") long userId, @RequestParam("bizType") String bizType, @RequestParam("consignationPrice") String consignationPrice, @RequestParam("consignationCount") String consignationCount);

  @RequestMapping(value = "/client/trade/marketPriceBuyConsignation", method = RequestMethod.POST)
  AjaxResponse marketPriceBuyConsignation(@RequestParam("userId") long userId, @RequestParam("bizType") String bizType, @RequestParam("consignationCount") String consignationCount);

  @RequestMapping(value = "/client/trade/marketPriceSellConsignation", method = RequestMethod.POST)
  AjaxResponse marketPriceSellConsignation(@RequestParam("userId") long userId, @RequestParam("bizType") String bizType, @RequestParam("consignationCount") String consignationCount);

}
