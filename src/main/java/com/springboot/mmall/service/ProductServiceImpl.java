package com.springboot.mmall.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.springboot.mmall.bo.MmallProductBo;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.dao.MmallCartMapper;
import com.springboot.mmall.dao.MmallCategoryMapper;
import com.springboot.mmall.dao.MmallProductMapper;
import com.springboot.mmall.pojo.MmallCategory;
import com.springboot.mmall.pojo.MmallProduct;
import com.springboot.mmall.util.BeanMapperUtil;
import com.springboot.mmall.util.FTPUtil;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private MmallProductMapper productMapper;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private MmallCategoryMapper categoryMapper;

	@Override
	public ServerResponse<PageInfo<MmallProduct>> listByPage(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<MmallProduct> list = productMapper.listProduct();
		return ServerResponse.createBySuccess(new PageInfo<>(list));
	}

	@Override
	public ServerResponse<PageInfo<MmallProduct>> serarcByPage(Integer pageNum, Integer pageSize, String productName,
			Integer categoryId) {
		if (StringUtils.isBlank(productName) && categoryId == null) {
			return ServerResponse.createByErrorMessage("参数有误");
		}
		List<Integer> list = Lists.newArrayList();

		if (categoryId != null) {
			list = categoryService.listDeepChildren(categoryId).getData();
		}
		PageHelper.startPage(pageNum, pageSize);

		List<MmallProduct> products = productMapper.serachProduct(productName, list);

		return ServerResponse.createBySuccess(new PageInfo<>(products));
	}

	@Override
	public ServerResponse<MmallProductBo> detail(Integer productId) {
		MmallProduct product = productMapper.selectByPrimaryKey(productId);
		if (product == null) {
			return ServerResponse.createByErrorMessage("无法找到该商品");
		}

		MmallCategory category = categoryMapper.selectByPrimaryKey(product.getCategoryId());

		return ServerResponse.createBySuccess(assemDetailProduct(product, category));
	}

	private MmallProductBo assemDetailProduct(MmallProduct product, MmallCategory category) {
		MmallProductBo bo = BeanMapperUtil.map(product, MmallProductBo.class);
		bo.setParentCategoryId(category.getParentId());
		return bo;
	}

	@Override
	public ServerResponse<String> updateSelStatus(Integer productId, Integer status) {
		MmallProduct record = new MmallProduct();
		record.setId(productId);
		record.setStatus(status);
		record.setUpdateTime(new Date());
		int result = productMapper.updateByPrimaryKey(record);
		if (result == 1) {
			return ServerResponse.createBySuccess("修改商品状态成功");
		}
		return ServerResponse.createByErrorMessage("修改商品状态失败");
	}

	@Override
	public ServerResponse<String> saveProduct(Integer categoryId, String name, String subtitle, String mainImage,
			String subImages, String detail, Double price, Integer stock, Integer status, Integer id) {
		MmallProduct product = new MmallProduct();
		product.setCategoryId(categoryId);
		product.setCreateTime(new Date());
		product.setDetail(detail);
		product.setId(id);
		product.setMainImage(mainImage);
		product.setName(name);
		product.setPrice(BigDecimal.valueOf(price));
		product.setStatus(status);
		product.setStock(stock);
		product.setSubImages(subImages);
		product.setSubtitle(subtitle);
		product.setUpdateTime(new Date());
		if (id != null) {
			int result = productMapper.updateByPrimaryKey(product);
			if (result == 1) {
				return ServerResponse.createBySuccessMessage("更新商品成功");
			}
			return ServerResponse.createBySuccessMessage("更新商品失败");
		}

		int result = productMapper.insertSelective(product);

		if (result == 1) {
			return ServerResponse.createBySuccessMessage("更新商品成功");
		}
		return ServerResponse.createBySuccessMessage("新增商品失败");
	}

	@Override
	public ServerResponse<PageInfo<MmallProduct>> listByOrderPage(String productName, Integer categoryId, Integer pageNum,
			Integer pageSize,String orderBy) {
		List<Integer> list = Lists.newArrayList();

		if (categoryId != null) {
			list = categoryService.listDeepChildren(categoryId).getData();
		}
		PageHelper.startPage(pageNum, pageSize) ;
		
		if (StringUtils.isNotBlank(orderBy)) {
			if (orderBy.contains("_")) {
				String [] strings = orderBy.split("_") ;
				
				PageHelper.orderBy(strings[0]+" "+strings[1]);
			}
		}
		
		List<MmallProduct> list2 = productMapper.serachProduct(productName, list);
		return ServerResponse.createBySuccess(new PageInfo<>(list2));
	}

}
