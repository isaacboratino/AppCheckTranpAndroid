package br.com.idbservice.apptranspcheck.Presentation;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.simplify.ink.InkView;

import br.com.idbservice.apptranspcheck.R;

public class InkActivity extends BaseActivity {

    private InkView inkView;
    private FloatingActionButton fabClose;
    private FloatingActionButton fabOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ink);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.exibirLogo();
        this.inicializarComponentes();
    }

    private void inicializarComponentes() {

        this.inkView = (InkView) findViewById(R.id.inkView);
        this.inkView.setColor(getResources().getColor(android.R.color.black));
        this.inkView.setMinStrokeWidth(1.5f);
        this.inkView.setMaxStrokeWidth(6f);

        this.fabClose = (FloatingActionButton) findViewById(R.id.fabClose);
        this.fabOk = (FloatingActionButton) findViewById(R.id.fabOk);

        this.fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InkActivity.this.finish();
            }
        });

        this.fabOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bitmap drawing = this.inkView.getBitmap();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("imgAssinatura", recuperarAssinatura());
                setResult(Activity.RESULT_OK,returnIntent);
                InkActivity.this.finish();
            }
        });
    }

    private Bitmap recuperarAssinatura() {

        return this.inkView.getBitmap();

        /*Intent returnIntent = new Intent();
        returnIntent.putExtra("imgAssinatura", drawing);
        setResult(Activity.RESULT_OK,returnIntent);
        InkActivity.this.finish();*/
    }
}
