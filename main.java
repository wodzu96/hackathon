import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
public class main{
public String putDataToServer(String url,JSONObject returnedJObject) throws Throwable
{
    HttpPost request = new HttpPost(url);
    JSONStringer json = new JSONStringer();
    StringBuilder sb=new StringBuilder();


    if (returnedJObject!=null) 
    {
        Iterator<String> itKeys = returnedJObject.keys();
        if(itKeys.hasNext())
            json.object();
        while (itKeys.hasNext()) 
        {
            String k=itKeys.next();
            json.key(k).value(returnedJObject.get(k));
            Log.e("keys "+k,"value "+returnedJObject.get(k).toString());
        }             
    }
    json.endObject();


    StringEntity entity = new StringEntity(json.toString());
                         entity.setContentType("application/json;charset=UTF-8");
    entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
    request.setHeader("Accept", "application/json");
    request.setEntity(entity); 

    HttpResponse response =null;
    DefaultHttpClient httpClient = new DefaultHttpClient();

    HttpConnectionParams.setSoTimeout(httpClient.getParams(), Constants.ANDROID_CONNECTION_TIMEOUT*1000); 
    HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),Constants.ANDROID_CONNECTION_TIMEOUT*1000); 
    try{
        response = httpClient.execute(request); 
    }
    catch(SocketException se)
    {
        Log.e("SocketException", se+"");
        throw se;
    }
    InputStream in = response.getEntity().getContent();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line = null;
    while((line = reader.readLine()) != null){
        sb.append(line);
    }
    return sb.toString();
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
     JSONObject obj = new JSONObject(json);
		System.out.println("powitanie: " + obj.getString("powitanie"));
    }
}
