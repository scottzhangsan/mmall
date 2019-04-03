package com.springboot.mmall.controller;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.springboot.mmall.common.Const;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.service.IOrderService;
import com.springboot.mmall.vo.OrderVo;
import com.springboot.mmall.vo.ProductOrderItemVo;

@RestController
@RequestMapping("/order")
public class OrderController {

	private static Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private IOrderService orderService;
    
	/**
     * 付款
     * @param session
     * @param orderNo
     * @param request
     * @return
     */
	@RequestMapping("/pay")
	public ServerResponse<Map<String, String>> pay(HttpSession session, Long orderNo, HttpServletRequest request) {
		return orderService.pay(orderNo, null);

	}
    /**
     * 查看订单的支付状态
     * @param session
     * @param orderNo
     * @return
     */
	@RequestMapping("/query_order_pay_status")
	public ServerResponse<Boolean> queryOrderStatus(HttpSession session, Long orderNo) {
		if (orderService.queryOrderPayStatus(orderNo, null).isSuccess()) {
			return ServerResponse.createBySuccess(true);
		}
		return ServerResponse.createBySuccess(false);

	}

	/**
	 * 回调
	 * 如果给支付宝返回success支付宝就不会再进行回调了
	 * @param request
	 * @return
	 */
	@RequestMapping("/alipay_callback")
	public Object alipayCallback(HttpServletRequest request) {
		Map<String, String> map = Maps.newHashMap();
		Map requestParams = request.getParameterMap();
		Iterator iterator = requestParams.keySet().iterator();
		while (iterator.hasNext()) {
			String name = (String) iterator.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			// 如果是最后一个不进行拼接逗号
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			map.put(name, valueStr);
		}
		map.remove("sign_type");
		try {
			// 签名验证
			boolean flag = AlipaySignature.rsaCheckV2(map, Configs.getAlipayPublicKey(), "utf-8",
					Configs.getSignType());
			if (!flag) {
				return ServerResponse.createByErrorMessage("非法请求，在使用非法的请求参数，我就报网警了");
			}
		} catch (AlipayApiException e) {
			logger.error("调用支付宝回调验证接口异常", e);
			return ServerResponse.createByErrorMessage("调用支付宝回调验证接口异常");
		}

		ServerResponse<String> response = orderService.saveAlipayInfo(map);
		if (response.isSuccess()) {
			return Const.AlipayNotify.NOTIFY_SUCCESS;
		}
		return Const.AlipayNotify.NOTIFY_FAILURE;
	}
	/**创建订单
	 * 
	 * @param session
	 * @param shippingId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/create")
	public ServerResponse createOrder(HttpSession session,Integer shippingId){
		return orderService.createOrder(null, shippingId) ;
	}
	
	@RequestMapping("/get_order_cart_product")
	public ServerResponse<ProductOrderItemVo> getProductOrderItem(Long orderNo,HttpSession session){
		return orderService.getProductOrderItem(orderNo);
	}
	@RequestMapping("/list")
	public ServerResponse<PageInfo<OrderVo>> listOrder(HttpSession session,@RequestParam(value="pageSize",defaultValue="10")Integer pageSize,@RequestParam(value="pageNum",defaultValue="1")Integer pageNum){
		return orderService.listByPage(pageSize, pageNum) ;
	}
	@RequestMapping("/cancel")
	public ServerResponse<String> cancel(HttpSession session,Long orderNo,HttpServletRequest request){
		return orderService.cancel(orderNo) ;
	}

}
