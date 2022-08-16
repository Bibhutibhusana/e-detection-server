package com.nic.edetection.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class OTPController {
	@PostMapping("/otp")
	public String sendOtp(@Valid @RequestBody Map<String,String> mobile) throws ParseException {
		char[] otpArray = generateOTP(4);
		String otp = String.valueOf(otpArray);
		//System.out.print(mobile.get("mobile_no"));
		String mobile_no = mobile.get("mobile_no");
		//return smsSentOnTextLocal(mobile_no,otp);
		
		//Date date = new Date();
//				DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);
		Date today = new Date();
		DateFormat formatter = new SimpleDateFormat("dd-MM-yy;hh-mm");
		String date1 = formatter.format(today);
		
		Date date = formatter.parse(formatter.format(today));
		
		System.out.print(date);
		System.out.println("TolocalString : "+date.toLocaleString());

		System.out.println("ToInstant : "+date.toInstant());
		System.out.println("GetDate : "+date.getDate());
		System.out.println("GetDay : "+date.getDay());
		System.out.println("GetHours : "+date.getHours());
		System.out.println("GetMonth : "+date.getMonth());
		System.out.println("GetTime : "+date.getTime());
		System.out.println("GetYear : "+date.getYear());
		System.out.println("GetTimeZoneOffSet : "+date.getTimezoneOffset());
		System.out.println("GetMinutes : "+date.getMinutes());
		System.out.println("GetSecond : "+date.getSeconds());
		System.out.println("GetClass : "+date.getClass());
		System.out.println("ToString : "+date.toString());
		
		
		return date1;
		
	}
	
//	@PostMapping("/getDate")
//	public Map<String,Object> getDate(@RequestBody String date){
//		Date today = new Date();
//		java.sql.Date now = new java.sql.Date(today.getTime());
//		Map<String,Object> obj = new HashMap<>();
//		obj.put("javaDate",  today);
//		obj.put("sqlDate", now);
//		obj.put("angularDate", date);
//		return obj;
//	}
//	
//	@PostMapping("/otpSecure")
//	public String sendSecureOtp(@Valid @RequestBody Map<String,String> mobile) {
//		char[] otpArray = generateOTP(4);
//		String otp = String.valueOf(otpArray);
//		//System.out.print(mobile.get("mobile_no"));
//		String mobile_no = mobile.get("mobile_no");
//		
//		 TrustManager[] trustAllCerts = new TrustManager[]{
//		            new X509TrustManager() {
//		                @Override
//		                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//		                    return new X509Certificate[0];
//		                }
//		 
//		                @Override
//		                public void checkClientTrusted(
//		                        java.security.cert.X509Certificate[] certs, String authType) {
//		                }
//		 
//		                @Override
//		                public void checkServerTrusted(
//		                        java.security.cert.X509Certificate[] certs, String authType) {
//		                }
//		            }
//		        };
//		 
//		        try {
//		            SSLContext sc = SSLContext.getInstance("SSL");
//		            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//		            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//		 
//		            HostnameVerifier allHostsValid = new HostnameVerifier() {
//		                @Override
//		                public boolean verify(String hostname, SSLSession session) {
//		                    return true;
//		                }
//		            };
//		            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
//		 
//		        } catch (GeneralSecurityException e) {
//		            System.out.println(e.getMessage());
//		        }
//		 
//		        try {
//		            StringBuilder sendString = new StringBuilder();
//		            String username = "john";
//		            String password = "Xc3ffs";
//		            String messagetype = "SMS:TEXT";
//		            String httpUrl = "https://127.0.0.1:8090/";
//		            String recipient = URLEncoder.encode("+918327762384", "UTF-8");
//		            String messagedata = URLEncoder.encode("TestMessage", "UTF-8");
//		 
//		            sendString.append(httpUrl).append("api?action=sendmessage").
//		                    append("&username=").append(username).append("&password=").
//		                    append(password).append("&recipient=").append(recipient).
//		                    append("&messagetype=").append(messagetype).append("&messagedata=").
//		                    append(messagedata);
//		 
//		            System.out.println("Sending request: " + sendString.toString());
//		 
//		            URL url = new URL(sendString.toString());
//		            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//		            con.setRequestMethod("GET");
//		 
//		            BufferedReader br = null;
//		            System.out.println("Http response received: ");
//		            if (con.getResponseCode() == 200) {
//		                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//		                String strCurrentLine;
//		                while ((strCurrentLine = br.readLine()) != null) {
//		                    System.out.println(strCurrentLine);
//		                }
//		            } else {
//		                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
//		                String strCurrentLine;
//		                while ((strCurrentLine = br.readLine()) != null) {
//		                    System.out.println(strCurrentLine);
//		                }
//		            }
//		 
//		        } catch (Exception ex) {
//		            System.out.println(ex.getMessage());
//		        }
//		
//		return "Successfully send";
//	}
//	
	private static char[] generateOTP(int length) {
	      String numbers = "1234567890";
	      Random random = new Random();
	      char[] otp = new char[length];

	      for(int i = 0; i< length ; i++) {
	         otp[i] = numbers.charAt(random.nextInt(numbers.length()));
	      }
	      return otp;
	   }
	
	public String smsSentOnTextLocal(String mobile_no,String otp) {
		try {
			// Construct data
			/////  Code for Get Api????///////////////////////////////////////////////////
			//String apiKey = "apikey=" + URLEncoder.encode("NjQ3Nzc3NDU2MzU1NDk0MTM4NDM2NTY0NmY0YzRiNzE=","UTF-8");
			//String message =URLEncoder.encode("&message="+"Hi there, thank you for sending your first test message from Textlocal. Get 20% off today with our code: "+ otp,"UTF-8");
			//	+otp+".";
			//String message = "&message=" + "Your OTP For Login is "+otp;
			//String sender = URLEncoder.encode("&sender=" + "600010","UTF-8");
			//String numbers = URLEncoder.encode("&numbers=" +mobile_no,"UTF-8");
			
//////////////////////////  Code For Post Api /////////////////////////////////////////////////////////////////////			
			String apiKey = "apikey=" +"NjQ3Nzc3NDU2MzU1NDk0MTM4NDM2NTY0NmY0YzRiNzE=";
			String message ="&message="+"Hi there, thank you for sending your first test message from Textlocal. Get 20% off today with our code: "+ otp;
			//	+otp+".";
			//String message = "&message=" + "Your OTP For Login is "+otp;
			String sender ="&sender=" + "600010";
			String numbers = "&numbers=" +mobile_no;
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();

			//HttpURLConnection conn = (HttpURLConnection) new URL("https://www.smsgateway.center/library/api/self/deliveryErrorCodes/?userId=bibhuti&password=xxxxx&format=json").openConnection();
			
			String data = "https://api.textlocal.in/send/?"+apiKey + numbers +message +sender;
			
			//URL url = new URL(data);
			//URLConnection conn = url.openConnection();
			//conn.setDoOutput(true);
			//HttpURLConnection conn = (HttpURLConnection) new URL(data).openConnection();

			System.out.println(data);
			////////////////////////////////////////  Code For POST Api////////////////////////////////////////////////
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			System.out.println(stringBuffer.toString());
			rd.close();
			
			return stringBuffer.toString();
		} catch (Exception e) { 
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}
	
	@GetMapping("/getDate")
	public Date getDate(@RequestBody String dateStr) throws ParseException {
	    Date date;
	    try {
	    	 date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dateStr);
	    }catch(ParseException e) {
	    	//System.out.println(e);
	    	 date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(dateStr);
	    }
	    
	    System.out.println(date);
		
		return date;
	}

}
