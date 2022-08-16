package com.nic.edetection.ip;

import javax.servlet.http.HttpServletRequest;

public interface IRequestService {
	
	String getClientIp(HttpServletRequest request);
	
}