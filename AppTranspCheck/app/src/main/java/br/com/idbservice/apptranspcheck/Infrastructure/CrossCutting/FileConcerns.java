package br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

import br.com.idbservice.apptranspcheck.R;

public class FileConcerns {

    public static File CriarArquivo(String nomeArquivo) throws Exception {

        try {

            File diretorio = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator  + "jsondata");

            boolean success = true;

            if (!diretorio.exists()) {
                success = diretorio.mkdirs();
            }

            if (success) {
                File arquivo = new File(String.format("%s/%s.json", diretorio, nomeArquivo));
                return arquivo;
            } else {
                throw new Exception(String.format("Nao foi possivel criar o diretorio ou arquivo %s", diretorio));
            }

        } catch (IOException e) {
            throw new IOException();
        }catch (Exception e) {
            throw new Exception();
        }
    }
}
