package br.com.ciberian.componentes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txvTexto;
    private EditText edtTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txvTexto = (TextView) findViewById(R.id.txvTexto);
        edtTexto = (EditText) findViewById(R.id.edtTexto);
    }

    public void btnOkClick (View v) {
        txvTexto.setText(edtTexto.getText());
    }
}
