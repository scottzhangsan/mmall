package com.springboot.mmall.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springboot.mmall.Exception.MmallException;
import com.springboot.mmall.common.Const;
import com.springboot.mmall.common.OrderEnum;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.dao.MmallCartMapper;
import com.springboot.mmall.dao.MmallOrderItemMapper;
import com.springboot.mmall.dao.MmallOrderMapper;
import com.springboot.mmall.dao.MmallPayInfoMapper;
import com.springboot.mmall.dao.MmallProductMapper;
import com.springboot.mmall.pojo.MmallCart;
import com.springboot.mmall.pojo.MmallOrder;
import com.springboot.mmall.pojo.MmallOrderItem;
import com.springboot.mmall.pojo.MmallPayInfo;
import com.springboot.mmall.pojo.MmallProduct;
import com.springboot.mmall.util.BeanMapperUtil;
import com.springboot.mmall.util.BigDecimalUtil;
import com.springboot.mmall.util.DateUtil;
import com.springboot.mmall.util.FTPUtil;
import com.springboot.mmall.vo.OrderItemVo;
import com.springboot.mmall.vo.OrderVo;

@Service
public class OrderServiceImpl implements IOrderService {

	private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private MmallOrderMapper orderMapper;

	@Autowired
	private MmallOrderItemMapper orderItemMapper;
	@Autowired
	private MmallPayInfoMapper payInfoMapper ;
	@Autowired
	private MmallCartMapper cartMapper ;
	@Autowired
	private MmallProductMapper productMapper ;

	@Override
	public ServerResponse<Map<String, String>> pay(Long orderNo, Integer userId) {
		Map<String, String> map = Maps.newHashMap();
		MmallOrder order = orderMapper.selectByOrderNoAndUserId(orderNo, userId);
		if (order == null) {
			return ServerResponse.createByErrorMessage("没有相关订单");
		}

		// (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
		// 需保证商户系统端不能重复，建议通过数据库sequence生成，
		String outTradeNo = order.getOrderNo().toString();

		// (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
		String subject = new StringBuilder().append("欢迎在mmall商城购物，订单号：").append(outTradeNo).toString();
		// (必填) 订单总金额，单位为元，不能超过1亿元
		// 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
		String totalAmount = order.getPayment().toString();

		// (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
		// 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
		String undiscountableAmount = "0";

		// 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
		// 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
		String sellerId = "";

		List<MmallOrderItem> items = orderItemMapper.listOrderItemByOrderNoAndUserId(orderNo, userId);

		Integer totalNum = items.stream().mapToInt((e) -> e.getQuantity()).sum();
		// 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
		String body = new StringBuilder().append("总共购买").append(totalNum).append("件商品，共").append(order.getPayment())
				.append("元").toString();

		// 商户操作员编号，添加此参数可以为商户操作员做销售统计
		String operatorId = "test_operator_id";

		// (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
		String storeId = "test_store_id";

		// 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
		ExtendParams extendParams = new ExtendParams();
		extendParams.setSysServiceProviderId("2088100200300400500");

		// 支付超时，定义为120分钟
		String timeoutExpress = "120m";

		// 商品明细列表，需填写购买商品详细信息，
		List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
		// 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail

		for (MmallOrderItem item : items) {
			GoodsDetail good = GoodsDetail.newInstance(item.getProductId().toString(), item.getProductName(),
					BigDecimalUtil.multiply(item.getCurrentUnitPrice(), new BigDecimal("100")).longValue(),
					item.getQuantity());
			// 创建好一个商品后添加至商品明细列表
			goodsDetailList.add(good);
		}
		// 创建扫码支付请求builder，设置请求参数
		AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder().setSubject(subject)
				.setTotalAmount(totalAmount).setOutTradeNo(outTradeNo).setUndiscountableAmount(undiscountableAmount)
				.setSellerId(sellerId).setBody(body).setOperatorId(operatorId).setStoreId(storeId)
				.setExtendParams(extendParams).setTimeoutExpress(timeoutExpress)
				.setNotifyUrl("http://127.0.0.1:9090/order/alipay_callback")// 支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
				.setGoodsDetailList(goodsDetailList);
		// 支付宝当面付2.0服务（集成了交易保障接口逻辑）
		Configs.init("zfbinfo.properties");

		/**
		 * 使用Configs提供的默认参数 AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
		 */
		AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
		AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
		String filePath = "";
		switch (result.getTradeStatus()) {
		case SUCCESS:
			logger.info("支付宝预下单成功: )");

			AlipayTradePrecreateResponse response = result.getResponse();
			dumpResponse(response);

			// 需要修改为运行机器上的路径
			filePath = String.format("C:\\Users\\yzhang98\\Desktop\\qr-%s.png", response.getOutTradeNo());
			logger.info("filePath:" + filePath);
			ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
			File file = new File(filePath);
			try {
				// 上传到文件服务器
				FTPUtil.uploadFile(Lists.newArrayList(file));
			} catch (IOException e1) {
				logger.error("上传文件失败", e1);
			}
			break;

		case FAILED:
			logger.error("支付宝预下单失败!!!");
			break;

		case UNKNOWN:
			logger.error("系统异常，预下单状态未知!!!");
			break;

		default:
			logger.error("不支持的交易状态，交易返回异常!!!");
			break;
		}

		map.put("orderNo", orderNo.toString());
		map.put("qrPath", filePath);
		return ServerResponse.createBySuccess(map);
	}

	// 简单打印应答
	private void dumpResponse(AlipayResponse response) {
		if (response != null) {
			logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
			if (StringUtils.isNotEmpty(response.getSubCode())) {
				logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(), response.getSubMsg()));
			}
			logger.info("body:" + response.getBody());
		}
	}

	@Override
	public ServerResponse queryOrderPayStatus(Long orderNo, Integer userId) {
		MmallOrder order = orderMapper.selectByOrderNoAndUserId(orderNo, userId);
		if (order == null) {
			return ServerResponse.createByErrorMessage("订单不存在");
		}
		if (order.getStatus() >=OrderEnum.ALREADY_PAY.getCode() ) {
			return ServerResponse.createBySuccess();
		}
		return ServerResponse.createByError();
	}

	@Override
	@Transactional
	public ServerResponse<String> saveAlipayInfo(Map<String, String> map) {
		Long orderNo = Long.valueOf(map.get("out_trade_no"));
		MmallOrder order = orderMapper.selectByOrderNo(orderNo);
		if (order == null) {
			return ServerResponse.createByError();
		}
		// 验证数据
		/**
		 * 商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		 * 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		 * 同时需要校验通知中的seller_id（或者seller_email)
		 * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
		 * 上述有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
		 * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据
		 * 。在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功
		 */
		String totalAmount = order.getPayment().toString();
		String backTotalAmount = map.get("total_amount");
		if (!totalAmount.equals(backTotalAmount)) {
           return ServerResponse.createByError() ;
		}
		//查看订单是否是已经支付的订单,如果是已经支付的返回success
		Integer status =order.getStatus() ;
		if (status >= OrderEnum.ALREADY_PAY.getCode()) {
			return ServerResponse.createBySuccess() ;
		}
		//如果交易成功更新订单的状态
		//更新订单信息
		if (Const.AlipayNotify.TRADE_SUCCESS.equals(map.get("trade_status"))) {
			order.setPaymentTime(DateUtil.str2Date(map.get("gmt_paymen"), null));
			order.setStatus(OrderEnum.ALREADY_PAY.getCode());
			orderMapper.updateByPrimaryKey(order) ;
		}	
		//保存支付信息
		MmallPayInfo record = new MmallPayInfo();
		record.setCreateTime(new Date());
		record.setOrderNo(orderNo);
		record.setPayPlatform(Const.PayPlatform.ALIPAY.getCode());
		record.setPlatformNumber("2345");
		record.setPlatformStatus(map.get("trade_status"));
		record.setUpdateTime(new Date());
		record.setUserId(22);
		payInfoMapper.insertSelective(record) ;
		return ServerResponse.createBySuccess();
	}
	
	@Transactional
	public ServerResponse createOrder(Integer userId,Integer shippingId){
		userId =21 ;
		List<MmallCart> carts = cartMapper.listByUserIdSelected(userId);
		if (CollectionUtils.isEmpty(carts)) {
			return ServerResponse.createByErrorMessage("创建订单失败，选中的购物车为空") ;
		}
		long orderNo = createOrderNo() ;
		MmallOrder order = new MmallOrder() ;
		order.setCreateTime(new Date());
		order.setOrderNo(orderNo);
		order.setPaymentType(Const.PayPlatform.ALIPAY.getCode());
		order.setPostage(1); //全场包邮
		order.setShippingId(shippingId);
		order.setStatus(OrderEnum.NOT_PAY.getCode());
		order.setUpdateTime(new Date());
		order.setUserId(userId);
		ServerResponse<List<MmallOrderItem>>  response =assembleOrderItem(userId, carts, orderNo) ;
		if (!response.isSuccess()) {
			return response ;
		} 
		//java8 精确的求和运算
		BigDecimal totalPayment =  response.getData().stream().map(
	    		(e) -> e.getTotalPrice()).reduce(BigDecimal.ZERO, BigDecimal::add) ;
	    order.setPayment(totalPayment);	
	    //增加订单
	    int result  = orderMapper.insertSelective(order) ;
	    if (result != 1) {
			throw new MmallException("创建订单失败") ;
		}
	    //增加订单详情
	  int totalResult = orderItemMapper.batchInsertOrderItem(response.getData()) ;
	  if (totalResult != response.getData().size()) {
		  throw new MmallException("创建订单失败") ;
	   }
		
	    OrderVo vo = assembleOrderVo(order, response.getData()) ;
	    return ServerResponse.createBySuccess(vo) ;
	}
	
	private OrderVo assembleOrderVo(MmallOrder order,List<MmallOrderItem> items){
		OrderVo orderVo = BeanMapperUtil.map(order, OrderVo.class) ;
		List<OrderItemVo> orderItemVos = BeanMapperUtil.mapList(items, OrderItemVo.class) ;
		orderVo.setOrderItemVoList(orderItemVos);
		return orderVo ;
	}
	
	/**
	 * 生成订单号
	 * @return
	 */
	private long createOrderNo(){
		return System.currentTimeMillis()+new Random().nextInt(100) ;
	}
	
	
	
	/**
	 * 组装订单详情信息
	 * @param userId
	 * @param carts
	 * @return
	 */
	private ServerResponse assembleOrderItem(Integer userId,List<MmallCart> carts,long orderNo){
		List<MmallOrderItem> orderItems = Lists.newArrayList() ;
		for (MmallCart cart : carts) {
			MmallOrderItem orderItem = new MmallOrderItem() ;
			MmallProduct product = productMapper.selectByPrimaryKey(cart.getProductId()) ;
			//判断商品的状态和库存
			if (Const.ProductStatus.SOLD_OUT.getCode() == product.getStatus()) {
				return ServerResponse.createByErrorMessage(product.getName()+"已经下架") ;
			}
			if (cart.getQuantity() > product.getStock()) {
				return ServerResponse.createByErrorMessage(product.getName()+"库存不足") ;
			}
			orderItem.setCreateTime(new Date());
			orderItem.setCurrentUnitPrice(product.getPrice());
			orderItem.setOrderNo(orderNo);
			orderItem.setProductId(cart.getProductId());
			orderItem.setProductImage(product.getMainImage());
			orderItem.setProductName(product.getName());
			orderItem.setQuantity(cart.getQuantity());
			orderItem.setTotalPrice(BigDecimalUtil.multiply(product.getPrice(), new BigDecimal(cart.getQuantity().toString())));
			orderItem.setUpdateTime(new Date());
			orderItem.setUserId(userId);
			orderItems.add(orderItem);
		}
		return ServerResponse.createBySuccess(orderItems) ;
	}

}
