package br.com.idbservice.apptranspcheck.Infrastructure.ThirdPart.TranspCheckServer;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import br.com.idbservice.apptranspcheck.Application.IPostTaskListener;
import br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting.MultipartConcerns;
import br.com.idbservice.apptranspcheck.Infrastructure.Data.JsonData;
import br.com.idbservice.apptranspcheck.Presentation.BaseActivity;

public class ConsumeServer {

    public static void sendJson(final String urlParam, final Object dataJson, final Class<?> classeType,
                                final String method, final IPostTaskListener<Object> iPostTaskListener) throws Exception
    {
        AsyncTask<Void, String, Object> task = new AsyncTask<Void, String, Object>() {

            @Override
            protected Object doInBackground(Void... params) {

                Object retorno = false;

                try {

                    final URL url = new URL(urlParam);
                    final String json = JsonData.mapper.writeValueAsString(dataJson);

                    HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                    httpConnection.setReadTimeout(10000);
                    httpConnection.setConnectTimeout(15000);
                    httpConnection.setRequestMethod(method);
                    httpConnection.setDoInput(true);
                    httpConnection.setUseCaches(false);
                    httpConnection.setAllowUserInteraction(false);

                    if (method.equals("POST")) {
                        httpConnection.setDoOutput(true);

                        httpConnection.setFixedLengthStreamingMode(json.getBytes().length);

                        httpConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                        httpConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                        httpConnection.connect();

                        OutputStream envio = new BufferedOutputStream(httpConnection.getOutputStream());
                        envio.write(json.getBytes());
                        envio.flush();

                    } else { // GET
                        httpConnection.setDoOutput(false);
                        httpConnection.connect();
                    }

                    switch (httpConnection.getResponseCode()) {
                        case HttpURLConnection.HTTP_OK:

                            retorno = JsonData.inputStreamJsonToEntity(httpConnection.getInputStream(), classeType);
                            httpConnection.disconnect();

                            break;

                        case HttpURLConnection.HTTP_INTERNAL_ERROR:
                            retorno = JsonData.inputStreamJsonToEntity(httpConnection.getInputStream(), classeType);
                            throw new Exception(retorno.toString());
                    }

                } catch (Exception e) {
                    retorno = e;
                }

                return retorno;
            }

            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);

                if (result != null && iPostTaskListener != null)
                    try {
                        iPostTaskListener.onPostTask(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        };

        task.execute();
    }

    public static void sendMultPartBuilder(final String urlParam, final String[] uriFilesParam, final IPostTaskListener<Object> iPostTaskListener) {

        AsyncTask<Void, String, Boolean> task = new AsyncTask<Void, String, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {

                String charset = "UTF-8";

                try {

                    MultipartConcerns multipart = new MultipartConcerns(urlParam, charset);

                    multipart.addHeaderField("idUsuario", BaseActivity.ID_USUARIO.toString());

                    for (int i = 0; i < uriFilesParam.length; i++) {
                        multipart.addFilePart("fileUpload", new File(uriFilesParam[i].toString()));
                    }

                    List<String> response = multipart.finish();

                    System.out.println("SERVER REPLIED:");

                    for (String line : response) {
                        System.out.println(line);
                    }

                    return true;

                } catch (IOException ex) {
                    System.err.println(ex);
                }

                return false;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);

                if (result != false && iPostTaskListener != null)
                    try {
                        iPostTaskListener.onPostTask(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        };

        task.execute((Void)null);
    }
        /*
            @Override
            protected Boolean doInBackground(Void... params) {

                MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                        .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                        .addTextBody("idUsuario", BaseActivity.ID_USUARIO.toString())
                        .addBinaryBody("canhoto", new File(uriFilesParam[0].getPath()), ContentType.MULTIPART_FORM_DATA, null);


                for (int i = 0; i < 1; i++) {
                    FileBody fileBody = new FileBody(new File(uriFilesParam[i].getPath()));
                    builder.addPart("image" + i, fileBody);
                }

                try {
                    HttpPost httpPost = new HttpPost(urlParam);
                    httpPost.setEntity(builder.build());

                    HttpClient httpclient = new DefaultHttpClient();
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();

                    String responseBody = httpclient.execute(httpPost, responseHandler);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        task.execute((Void)null);
    }

    public static void sendMultPart(final String urlParam, final Uri[] uriFilesParam) {

        AsyncTask<Void, String, Boolean> task = new AsyncTask<Void, String, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlParam);

                try {

                    MultipartEntity reqEntity = new MultipartEntity();

                    for (int i = 0; i < 1; i++) {
                        FileBody fileBody = new FileBody(new File(uriFilesParam[i].toString()));
                        reqEntity.addPart("image" + i, fileBody);
                    }

                    reqEntity.addPart("idUsuario", new StringBody(BaseActivity.ID_USUARIO.toString()));
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
                return null;
            }
        };

        task.execute((Void) null);
    }*/
}
