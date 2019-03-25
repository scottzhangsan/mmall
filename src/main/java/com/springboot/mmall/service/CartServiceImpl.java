package com.springboot.mmall.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.springboot.mmall.common.Const;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.dao.MmallCartMapper;
import com.springboot.mmall.dao.MmallProductMapper;
import com.springboot.mmall.pojo.MmallCart;
import com.springboot.mmall.pojo.MmallProduct;
import com.springboot.mmall.util.BigDecimalUtil;
import com.springboot.mmall.vo.CartProductVo;
import com.springboot.mmall.vo.CartVo;
@Service
public class CartServiceImpl implements ICartService {
	
	@Autowired
	private MmallCartMapper cartMapper ;
	@Autowired
	private MmallProductMapper productMapper ;

	@Override
	@Transactional
	public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count) {
		userId = 22 ;
		//1:首先根据userid和商品的ID查询是否当前用户已经在购物车中增加过商品
		MmallCart cart = cartMapper.selectCartByUserAndProduct(userId, productId);
		//如果为空新增购物车
		if (cart == null ) {
			MmallCart record = new MmallCart() ;
			record.setProductId(productId);
			record.setQuantity(count);
			record.setUserId(userId);
			record.setCreateTime(new Date());
			record.setUpdateTime(new Date());
			record.setChecked(Const.Cart.notChecked);
			cartMapper.insertSelective(record);
		}else{
			//不为空更新购物车
			cart.setQuantity(cart.getQuantity()+count);
			cartMapper.updateByPrimaryKey(cart) ;
		}
		
		return ServerResponse.createBySuccess(assembleCartVo(userId));
	}
	/**
	 * 组装当前登录用户的购物车信息
	 * @param userId
	 * @return
	 */
	private CartVo assembleCartVo(Integer userId){
		CartVo vo = new CartVo() ;
		Boolean allChecked =true ;
		List<CartProductVo> cartProductVos = Lists.newArrayList() ;
		List<MmallCart> mmallCarts = cartMapper.listCartByUser(userId);
		//构建List<CartProductVo>
		for (MmallCart mmallCart : mmallCarts) {
			if (mmallCart.getChecked() == 0) {
				allChecked = false ;
			}
			CartProductVo cartProductVo = new CartProductVo() ;
			cartProductVo.setId(mmallCart.getId());
			cartProductVo.setProductChecked(mmallCart.getChecked());
			cartProductVo.setProductId(mmallCart.getProductId());
			MmallProduct product = productMapper.selectByPrimaryKey(mmallCart.getProductId()) ;
			cartProductVo.setProductMainImage(product.getMainImage());
			cartProductVo.setProductName(product.getName());
			cartProductVo.setProductPrice(product.getPrice());
			cartProductVo.setProductStatus(product.getStatus());
			cartProductVo.setProductStock(product.getStock());
			cartProductVo.setProductSubtitle(product.getSubtitle());
			cartProductVo.setProductTotalPrice(BigDecimalUtil.multiply(product.getPrice(), new BigDecimal(String.valueOf(mmallCart.getQuantity()))));
		    cartProductVo.setQuantity(mmallCart.getQuantity());
		    cartProductVo.setUserId(userId);
		    
		    //判断当前购物车商品的数量是否大于商品的库存，
		    Integer limitQuantity = 0 ;
		    if (mmallCart.getQuantity() > product.getStock()) {
				limitQuantity = product.getStock() ;
				cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
			}else{
				cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
				limitQuantity = mmallCart.getQuantity() ;
			}
		    //更新购物车商品的库存
		    MmallCart record = new MmallCart() ;
		    record.setId(mmallCart.getId());
		    record.setQuantity(limitQuantity);
		    record.setUpdateTime(new Date());
		    cartMapper.updateByPrimaryKeySelective(record) ;
		   // cartProductVo.setQuantity(limitQuantity);
		    cartProductVos.add(cartProductVo) ;
		}
		
		Double totalPrice = cartProductVos.stream().mapToDouble(
				(e)-> {
					if (e.getProductChecked() == 0) {
						return Double.valueOf("0");
					}
		return Double.valueOf(e.getProductTotalPrice().toString());
		}).summaryStatistics().getSum() ;
		vo.setCartTotalPrice(new BigDecimal(totalPrice));
		vo.setAllChecked(allChecked);
		vo.setCartProductVoList(cartProductVos);
		return vo ;
		
	}
	
	@Override
	@Transactional
	public ServerResponse<CartVo> updateProductCount(Integer userId, Integer productId, Integer count) {
		userId = 22 ;
		//1:首先根据userid和商品的ID查询是否当前用户已经在购物车中增加过商品
		MmallCart cart = cartMapper.selectCartByUserAndProduct(userId, productId);
		if (cart == null ) {
			return ServerResponse.createBySuccess(assembleCartVo(userId)) ;
		}
		
		MmallCart record = new MmallCart() ;
		record.setId(cart.getId());
		record.setQuantity(cart.getQuantity()+count);
		cartMapper.updateByPrimaryKeySelective(record) ;
		
		return ServerResponse.createBySuccess(assembleCartVo(userId));
	}
	@Override
	@Transactional
	public ServerResponse<CartVo> removeProduct(Integer userId,String productIds) {
		userId = 22 ;
		List<String> ids = Lists.newArrayList(productIds.split(",")) ;
		
		cartMapper.deletProductByProductIdAndUser(userId,ids) ;
		
		return ServerResponse.createBySuccess(assembleCartVo(userId));
	}
	@Override
	public ServerResponse<CartVo> list(Integer userId) {
		userId = 22 ;
		return ServerResponse.createBySuccess(assembleCartVo(userId));
	}
	
	@Override
	@Transactional
	public ServerResponse<CartVo> select(Integer userId,Integer productId) {
		userId = 22 ;
		MmallCart cart = cartMapper.selectCartByUserAndProduct(userId, productId);
		if (cart == null ) {
			return ServerResponse.createBySuccess(assembleCartVo(userId)) ;
		}
		MmallCart record = new MmallCart() ;
		record.setId(cart.getId());
		record.setUpdateTime(new Date());
		record.setChecked(Const.Cart.isChecked);
		cartMapper.updateByPrimaryKeySelective(record);
		return ServerResponse.createBySuccess(assembleCartVo(userId));
	}
	@Override
	@Transactional
	public ServerResponse<CartVo> unSelect(Integer userId, Integer productId) {
		userId = 22 ;
		MmallCart cart = cartMapper.selectCartByUserAndProduct(userId, productId);
		if (cart == null ) {
			return ServerResponse.createBySuccess(assembleCartVo(userId)) ;
		}
		MmallCart record = new MmallCart() ;
		record.setId(cart.getId());
		record.setUpdateTime(new Date());
		record.setChecked(Const.Cart.notChecked);
		cartMapper.updateByPrimaryKeySelective(record);
		return ServerResponse.createBySuccess(assembleCartVo(userId));
	}
	
	@Override
	public ServerResponse<Long> countProduct(Integer userId) {
		userId = 22 ;
		List<MmallCart> carts = cartMapper.listCartByUser(userId);
		if (CollectionUtils.isEmpty(carts)) {
			return ServerResponse.createBySuccess(0L);
		}
		Long result = carts.stream().mapToInt((e)->e.getProductId()).summaryStatistics().getSum() ;
		
	   return ServerResponse.createBySuccess(result);
	}
	@Override
	@Transactional
	public ServerResponse<CartVo> selectAll(Integer userId) {
		userId = 22 ;
		List<MmallCart> carts = cartMapper.listCartByUser(userId) ;
		for (MmallCart mmallCart : carts) {
			MmallCart record = new MmallCart() ;
			record.setId(mmallCart.getId());
			record.setChecked(Const.Cart.isChecked);
			record.setUpdateTime(new Date());
			cartMapper.updateByPrimaryKeySelective(record) ;
		}
		return ServerResponse.createBySuccess(assembleCartVo(userId));
	}
	@Override
	@Transactional
	public ServerResponse<CartVo> unSelectAll(Integer userId) {
		userId = 22 ;
		List<MmallCart> carts = cartMapper.listCartByUser(userId) ;
		for (MmallCart mmallCart : carts) {
			MmallCart record = new MmallCart() ;
			record.setId(mmallCart.getId());
			record.setChecked(Const.Cart.notChecked);
			record.setUpdateTime(new Date());
			cartMapper.updateByPrimaryKeySelective(record) ;
		}
		return ServerResponse.createBySuccess(assembleCartVo(userId));
	}

}
