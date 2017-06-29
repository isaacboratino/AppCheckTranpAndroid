package br.com.idbservice.apptranspcheck.Session;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import br.com.idbservice.apptranspcheck.Infrastructure.Data.JsonData;

public class BaseSession {

    public static HttpResponse sendJson(String url, Object dataJson) throws Exception
    {
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httpost = new HttpPost(url);

        //JSONObject holder = getJsonObjectFromMap(params);
        StringEntity stringJson = new StringEntity(JsonData.mapper.writeValueAsString(dataJson));

        httpost.setEntity(stringJson);
        httpost.setHeader("Accept", "application/json");
        httpost.setHeader("Content-type", "application/json");

        ResponseHandler responseHandler = new BasicResponseHandler();
        return (HttpResponse) httpclient.execute(httpost, responseHandler);
    }

    /*public static HttpResponse sendMultPart() {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://localhost:8080/HTTP_TEST_APP/index.jsp");

        try {
            FileBody bin = new FileBody(new File("C:/ABC.txt"));
            StringBody comment = new StringBody("BETHECODER HttpClient Tutorials");

            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("fileup0", bin);
            reqEntity.addPart("fileup1", comment);

            reqEntity.addPart("ONE", new StringBody("11111111"));
            reqEntity.addPart("TWO", new StringBody("222222222"));
            httppost.setEntity(reqEntity);

            System.out.println("Requesting : " + httppost.getRequestLine());
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httppost, responseHandler);

            System.out.println("responseBody : " + responseBody);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }*/
}
