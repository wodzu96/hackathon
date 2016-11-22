import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.apache.http.util.EntityUtils;

public class main{
private static String sendJson(String json){


String result="";
		    HttpClient httpClient = new DefaultHttpClient();

		    try {
		        HttpPost request = new HttpPost("http://partygoer.org/test1.php");
		        StringEntity params =new StringEntity("message=" + json);
		        request.addHeader("content-type", "application/x-www-form-urlencoded");
		        request.setEntity(params);
		        HttpResponse response = httpClient.execute(request);

		        // handle response here...

		        HttpEntity entity = response.getEntity(); 
		        InputStream is = entity.getContent(); 
		        BufferedReader in = new BufferedReader(new InputStreamReader(is));
		      
		        StringBuffer buffer = new StringBuffer();
		        int read;
		        char[] chars = new char[1024];
		        while ((read = in.read(chars)) != -1)
		            buffer.append(chars, 0, read); 

		        result =  buffer.toString();
		    } catch (Exception ex) {
		        // handle exception here
		    } finally {
		        httpClient.getConnectionManager().shutdown();
		    }
			return result;
}
private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read); 

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }

    }
    public static void main(String[] args) throws Exception {
     String json = readUrl("http://partygoer.org/test.php");
     JSONObject obj1 = new JSONObject();
     obj1.put("powitanie", "siema");
     System.out.println("powitanie 1: "+sendJson(obj1.toString()));
     JSONObject obj = new JSONObject(json);
    
     
		System.out.println("powitanie: " + obj.getString("powitanie"));
    }
}
