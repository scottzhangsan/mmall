package com.springboot.mmall.interceptor;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.springboot.mmall.common.Const;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.pojo.MmallUser;
import com.springboot.mmall.util.CookieUtil;
import com.springboot.mmall.util.JsonUtil;
import com.springboot.mmall.util.StringRedisUtil;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	@Autowired
	private StringRedisUtil redisUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestPath = request.getRequestURI(); // 获取请求的url
		Map<String, String> value = Maps.newHashMap();
		Map<String, String[]> params = request.getParameterMap();
		Iterator<Map.Entry<String, String[]>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String[]> entryMap = iterator.next();
			String key = entryMap.getKey();
			String[] values = entryMap.getValue();
			String temp = "";
			for (int i = 0; i < values.length; i++) {
				temp = i == values.length - 1 ? temp + values[i] : temp + values[i] + ",";
			}
			value.put(key, temp);
		}
		logger.info("请求的路径是：" + requestPath);
		logger.info("输入参数是：" + value.toString());
		// 判断是否登录
		String sessionId = CookieUtil.getUid(request, Const.MMALL_COOKIE_NAME);
		if (StringUtils.isEmpty(sessionId)) {
			response.reset(); 
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(JsonUtil.object2String(ServerResponse.createByErrorMessage("not login")));
			logger.info("用户未登录");
			return false;
		}

		MmallUser user = JsonUtil.json2Object(redisUtil.getValue(sessionId), MmallUser.class);
		if (user == null) {
			logger.info("当前用户不存在");
			return false;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
