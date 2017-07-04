package br.com.idbservice.apptranspcheck.Application;

public interface IPostTaskListener<K> {
    void onPostTask(K result) ;
}
