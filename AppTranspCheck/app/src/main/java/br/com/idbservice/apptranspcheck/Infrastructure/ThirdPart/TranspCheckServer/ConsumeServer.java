package br.com.idbservice.apptranspcheck.Infrastructure.ThirdPart.TranspCheckServer;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;

import br.com.idbservice.apptranspcheck.Application.IPostTaskListener;
import br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting.MultipartConcerns;
import br.com.idbservice.apptranspcheck.Infrastructure.Data.JsonData;
import br.com.idbservice.apptranspcheck.Presentation.BaseActivity;
import br.com.idbservice.apptranspcheck.R;

public class ConsumeServer {

    public static void sendJson(final String urlParam, final Object dataJson, final Class<?> classeType,
                                final String method, final IPostTaskListener<Object> iPostTaskListener) throws Exception
    {
        AsyncTask<Void, String, Object> task = new AsyncTask<Void, String, Object>() {

            @Override
            protected Object doInBackground(Void... params) {

                Object retorno = null;

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
                } catch (SocketTimeoutException e) {
                    retorno = new Exception("Nao foi possivel conectar ao servidor de dados", e);
                } catch (SocketException e) {
                    retorno = new Exception("Nao foi possivel conectar ao servidor de dados", e);
                } catch (Exception e) {
                    retorno = e;
                }

                return retorno;
            }

            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);

                if (result != null && iPostTaskListener != null)
                    iPostTaskListener.onPostTask(result);
                else
                    iPostTaskListener.onPostTask(null);
            }
        };

        task.execute();
    }

    public static void sendMultPartBuilder(final String urlParam, final String[] uriFilesParam, final IPostTaskListener<Object> iPostTaskListener) {

        AsyncTask<Void, String, Object> task = new AsyncTask<Void, String, Object>() {

            @Override
            protected Object doInBackground(Void... params) {

                String charset = "UTF-8";
                Object retorno = false;

                try {

                    MultipartConcerns multipart = new MultipartConcerns(urlParam, charset);

                    multipart.addHeaderField("idUsuario", BaseActivity.ID_USUARIO.toString());

                    for (int i = 0; i < uriFilesParam.length; i++) {
                        multipart.addFilePart("fileUpload", new File(uriFilesParam[i].toString()));
                    }

                    multipart.finish();

                    /*List<String> response = multipart.finish();
                    for (String line : response) {
                        System.out.println(line);
                    }*/

                    retorno = true;

                } catch (SocketException e) {
                    retorno = new Exception("Nao foi possivel conectar ao servidor de dados");
                } catch (IOException e) {
                    retorno = e;
                }

                return retorno;
            }

            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);

                if (result != null && iPostTaskListener != null)
                    iPostTaskListener.onPostTask(result);
                else
                    iPostTaskListener.onPostTask(false);
            }
        };

        task.execute((Void)null);
    }
}