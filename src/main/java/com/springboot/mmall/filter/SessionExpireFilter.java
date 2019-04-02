package com.springboot.mmall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.springboot.mmall.common.Const;
import com.springboot.mmall.pojo.MmallUser;
import com.springboot.mmall.util.CookieUtil;
import com.springboot.mmall.util.JsonUtil;
import com.springboot.mmall.util.StringRedisUtil;
/**
 * 过滤器，访问网页重新设置redis中sessionId的过期时间
 * @author yzhang98
 *
 */
@WebFilter(filterName="sessionExpireFilter",urlPatterns={"/*"})
public class SessionExpireFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(SessionExpireFilter.class) ;
	/*@Value("${filter.allow.path}")
	private List<String> allowPath ;*/
	
	@Autowired
	private StringRedisUtil redisUtil ;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("init filter");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("开始使用过滤器");
		HttpServletRequest servletRequest = (HttpServletRequest) request ;
		String sessionId = CookieUtil.getUid(servletRequest, Const.MMALL_COOKIE_NAME) ;
		if (StringUtils.isNotBlank(sessionId)) {
			MmallUser user = JsonUtil.json2Object(redisUtil.getValue(sessionId), MmallUser.class);
			if (user != null ) {
				redisUtil.deleteKey(sessionId);
				redisUtil.putKeyWithTime(sessionId,user, Const.SESSIONID_4_REDIS_EXPIRE_TIME);
			}
		}
		
		logger.info("结束使用过滤器");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
