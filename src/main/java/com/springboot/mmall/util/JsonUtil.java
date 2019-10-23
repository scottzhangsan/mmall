package com.springboot.mmall.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json转换工具类
 * @author yzhang98
 *
 */
public class JsonUtil {
	
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class) ;
	
	private static ObjectMapper objectMapper = new ObjectMapper() ;     
	static{
    	 
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		//设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
     }
	
	/**
	 * 对象转换为String的 json字符串
	 * @param object
	 * @return
	 */
	public static String object2String(Object object){
		if (object == null ) {
			return null ;
		}
		String value = null ;
		
		try {
			value = objectMapper.writeValueAsString(object) ;
		} catch (Exception e) {
			logger.error("对象转换为字符串失败",e);
		}
		
		return value ;
	}
	
	
	public static <T> List<String> listObject2ListString(List<T> list){
		if (list == null ) {
			return null ;
		}
		List<String> values = new ArrayList<>() ;
		
		for (T t : list) {
			values.add(object2String(t)) ;
		}
		
		return values ;
	}

	public static <T>T json2Object(String json,Class<T> t){
		if (json == null ) {
			return null ;
		}
		T value = null ;
		
		try {
			value =objectMapper.readValue(json, t) ;
		} catch (Exception e) {
			logger.error("转换出错",e);
		}
		
		return value ;
	}
	
	public static <T>List<T> listJson2ListObject(List<String> jsons,Class<T> t){
		if (jsons == null ) {
			return null ;
		}
		List<T> values = new ArrayList<>() ;
		
		for (String str : jsons) {
			values.add(json2Object(str, t)) ;
		}
		
		return values ;
	}
	
	/**
	 * json字符串，转换为 对象
	 * @param json
	 * @param tr
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public static <T> T json2Object(String json, TypeReference<T> tr) {
        if (StringUtils.isBlank(json))
            return null;

        T t = null;
        try {
            t = (T) objectMapper.readValue(json, tr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) t;
    }
	
	
	public static void main(String[] args) {
		
		Student student = new Student("zhangsan", 16);
		
		String result = JsonUtil.object2String(student) ;
		
		Student student2 = JsonUtil.json2Object(result,new TypeReference<Student>() {
		}) ;
		
		System.out.println(student2.getName());
		
		/*Map<String, Student> map = new HashMap<>() ;
		map.put("S1", new Student("zhangsan", 12)) ;
		map.put("S2", new Student("zhangsan", 13)) ;
		
		String result = JsonUtil.object2String(map);
		System.out.println(result);
		
		Map<String, Student> studetns = JsonUtil.json2Object(result, new TypeReference<Map<String,Student>>() {
		}) ;
		
		for (Entry<String, Student> student : studetns.entrySet()) {
			System.out.println(student.getKey());
			System.out.println(student.getValue().getName());
		}*/
	/*	List<Student> list = new ArrayList<>();
		list.add(new Student("zhangsan", 12));
		list.add(new Student("lisi", 14)) ;
		
		String string =JsonUtil.object2String(list);
		
		@SuppressWarnings("unchecked")
		List<Student> students = (List<Student>) JsonUtil.json2Object(string, new TypeReference<List<Student>>() {
		}) ;
		
		for (Student student : students) {
			System.out.println(student.getAge());
		}*/
	}
	
  public static class Student{
	  private String name ;
	  private int age ;
	  
	  
	public Student() {
		super();
	}
	public Student(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	  
	  
  }

}
