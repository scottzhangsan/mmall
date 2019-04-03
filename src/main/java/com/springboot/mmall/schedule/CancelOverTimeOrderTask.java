package com.springboot.mmall.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.springboot.mmall.common.Const;
import com.springboot.mmall.dao.MmallOrderMapper;

/**
 * 取消超时的订单的定时任务
 * @author yzhang98
 *
 */
@Component
public class CancelOverTimeOrderTask {
	
	private Logger logger = LoggerFactory.getLogger(CancelOverTimeOrderTask.class) ;
	
	@Autowired
	private MmallOrderMapper orderMapper ;
	
	
	@Scheduled(cron="0/5 * * * * ?")
	public void cancelOverTimeOrder(){
		logger.info("取消超时定时任务开始执行");
		long time = System.currentTimeMillis()-Const.LIMIT_OVER_TIEM_ORDER ;
		orderMapper.cancelOrderTimeOrder(new Date(time)) ;
		logger.info("结束取消订单的定时任务");
	}
	
}
