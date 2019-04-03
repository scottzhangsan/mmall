package com.springboot.mmall.schedule;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.springboot.mmall.common.Const;
import com.springboot.mmall.common.OrderEnum;
import com.springboot.mmall.dao.MmallOrderMapper;
import com.springboot.mmall.dao.MmallProductMapper;
import com.springboot.mmall.pojo.MmallOrder;
import com.springboot.mmall.pojo.MmallProduct;
import com.springboot.mmall.vo.OrderItemVo;
import com.springboot.mmall.vo.ProductOrderItemVo;

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
	@Autowired
	private MmallProductMapper productMapper ;
	
	
	@Scheduled(cron="* 0/10 * * * ?")
	public void cancelOverTimeOrder(){
		logger.info("取消超时定时任务开始执行");
		long time = System.currentTimeMillis()-Const.LIMIT_OVER_TIEM_ORDER ;
		List<MmallOrder> orders = orderMapper.listOverTimeOrder(new Date(time)) ;
		for (MmallOrder mmallOrder : orders) {
			mmallOrder.setStatus(OrderEnum.CANCEL.getCode());
			//1:取消订单
			orderMapper.updateByPrimaryKeySelective(mmallOrder);
			//2：增加库存
			ProductOrderItemVo vo = orderMapper.getOrderProductInfo(mmallOrder.getOrderNo()) ;
			List<OrderItemVo> orderItemVos = vo.getOrderItemVoList() ;
			for (OrderItemVo orderItemVo : orderItemVos) {
				MmallProduct product = productMapper.selectByPrimaryKey(orderItemVo.getProductId());
				product.setUpdateTime(new Date());
				product.setStock(product.getStock()+orderItemVo.getQuantity());
				productMapper.updateByPrimaryKeySelective(product) ;
			}
			
		}
		logger.info("结束取消订单的定时任务");
	}
	
}
