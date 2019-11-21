package com.springboot.mmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.mmall.dao.MmallPayInfoMapper;
import com.springboot.mmall.pojo.MmallPayInfo;

@Service
public class PayInfoServiceImpl {
	@Autowired
	private MmallPayInfoMapper payInfoMapper ;
	
	@Transactional
	public synchronized void add(MmallPayInfo record){
		payInfoMapper.insertSelective(record);
	}

}
