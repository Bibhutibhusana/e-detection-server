//package com.nic.edetection.utils;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ListUtility {
//	
//	@Autowired
//	ModelMapper modelMapper;
//	public  List<R> mapList(List<?> source, Class targetClass) {
//	    return source.stream().map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
//	}
//	public <T> mapClass(Class s, <T> t) {
//		return modelMapper.map(s,t);
//	}
//
//}
