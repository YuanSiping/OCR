import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import sun.misc.BASE64Encoder;

public class Request {

	public static void main(String[] args){
		String imagePath="C:\\XX8.jpg";
		String imageBase=encodeImgageToBase64(imagePath);
		imageBase = imageBase.replaceAll("\r\n","");  
        imageBase = imageBase.replaceAll("\\+","%2B");
		String httpUrl = "http://apis.baidu.com/apistore/idlocr/ocr";
		String httpArg = "fromdevice=pc&clientip=172.0.0.1&detecttype=LocateRecognize&languagetype=CHN_ENG&imagetype=1&image="+imageBase;
		String jsonResult = request(httpUrl, httpArg);
		System.out.println("���صĽ��--------->"+jsonResult);
	}

	/**
	 * @param urlAll
	 *            :����ӿ�
	 * @param httpArg
	 *            :����
	 * @return ���ؽ��
	 */
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	        // ����apikey��HTTP header
	        connection.setRequestProperty("apikey",  "1b2f91f68c42eccb9abf3229608edc46");
	        connection.setDoOutput(true);
	        connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	/** 
	 * ������ͼƬ����Base64λ���� 
	 *  
	 * @param imgUrl 
	 *            ͼƬ��url·������d:\\**.jpg 
	 * @return 
	 */  
	public static String encodeImgageToBase64(String imagePath) {// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��  
	    // �����Base64���봦��  
	    byte[] data = null;  
	    // ��ȡͼƬ�ֽ�����  
	    try {  
	    	File imageFile = new File(imagePath);
	        InputStream in = new FileInputStream(imageFile);  
	        data = new byte[in.available()];  
	        in.read(data);  
	        in.close();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	  
	    // ���ֽ�����Base64����  
	    BASE64Encoder encoder = new BASE64Encoder();  
	    return encoder.encode(data);// ����Base64��������ֽ������ַ���  
	} 
}
