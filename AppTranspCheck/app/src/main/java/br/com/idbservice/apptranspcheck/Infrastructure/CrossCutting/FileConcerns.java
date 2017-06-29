package br.com.idbservice.apptranspcheck.Infrastructure.CrossCutting;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileConcerns {

    public static File criarArquivo(String nomeArquivo) throws Exception {

        try {

            File diretorio = new File(Environment.getExternalStorageDirectory() + File.separator, "jsondata");

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

    public static File criarArquivoTemporario(String nomeArquivo, String extensaoArquivo, File diretorioArquivo) throws IOException {

        File arquivoTemporario = null;

        try {
            arquivoTemporario = File.createTempFile(
                    nomeArquivo,
                    extensaoArquivo,
                    diretorioArquivo
            );
        } catch (IOException e) {
            throw new IOException(String.format("Não foi possivel criar o arquivo %s/%s", diretorioArquivo.getAbsolutePath(), nomeArquivo), e);
        }

        return arquivoTemporario;
    }

    public static void bitmapToJpg(Bitmap bitmap, File arquivoAlvo) throws Exception {

        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(arquivoAlvo);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
    }
}
