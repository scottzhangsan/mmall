package com.springboot.mmall.controller.backend;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.mmall.common.Const;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.pojo.MmallCategory;
import com.springboot.mmall.pojo.MmallUser;
import com.springboot.mmall.service.ICategoryService;


/**
 * 后台品类接口
 * @author yzhang98
 *
 */
@RequestMapping("/manage/category")
@RestController
public class CategoryManageController {
	
	@Autowired
	private ICategoryService categoryService ;
	/**
	 * 
	 * @param session
	 * @param categoryName
	 * @param parentId
	 * @return
	 */
	@RequestMapping("add")
	public ServerResponse<String> addCategory(HttpSession session,@RequestParam("categoryName")String categoryName,@RequestParam(value="parentId",defaultValue="0")Integer parentId){
		MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER) ;
		if (user == null ) {
			return ServerResponse.createByErrorMessage("创建品类失败，用户未登录");
		}
		
		return categoryService.addCategory(categoryName, parentId) ;
	}
	/**
	 * 插叙当前的节点和当前节点的子节点
	 * @param parentId
	 * @return
	 */
	@RequestMapping("get_paraller_category")
	public ServerResponse<List<MmallCategory>> getParallerCategory(@RequestParam(value="parentId",defaultValue="0")Integer parentId){
		return categoryService.listParallerChildren(parentId) ;
	}
	
	/**
	 * 插叙当前的节点和当前节点的所有子节点信息
	 * @param parentId
	 * @return
	 */
	@RequestMapping("get_deep_category")
	public ServerResponse<List<Integer>> getDeepCategory(@RequestParam(value="parentId",defaultValue="0")Integer parentId){
		return categoryService.listDeepChildren(parentId) ;
	}
	
	

}
