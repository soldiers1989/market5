package com.c2b.coin.market.service;

import com.c2b.coin.common.DateUtil;
import com.c2b.coin.market.thread.RealTimeBlockDataThread;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class DataService_1Day extends TaskServiceBase{

  public static String CACHE_LOCK = "cache.lock.1day";

  private static String DAY_PATH_NAME = "1DAY/";

  private static int INTERVAL_TIME_SECONDS = 5;

  @Scheduled(cron = "0 0 1 1/1 * ?")
  public void task() {
    logger.debug("go Task 1 day!");
    if(setex(CACHE_LOCK,DateUtil.getCurrentTimestamp()+"")){
      logger.debug("get 1 day lock!" + DateUtil.getCurrentTimestamp());
      stringRedisTemplate.expire(CACHE_LOCK,INTERVAL_TIME_SECONDS,TimeUnit.SECONDS);
      doJob();
    }else {
      logger.debug("NOT get 1 day lock!");
    }
  }

  @Override
  public void doJob() {
    List<String> list = super.getTradePairs();
    for(String tradePair: list){
      logger.debug("Trade Pairs:"+tradePair);
      new Thread(new RealTimeBlockDataThread(tradePair,filePath + DAY_PATH_NAME,
        this.getPahtEnd("/yyyy/MM/dd"),getBeginTime_Day(1),getEndTime_Day(), FILE_SUFFIX, matchMoneyMapper)).start();
    }
  }
}
