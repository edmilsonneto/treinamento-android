package br.com.ciberian.agendatelefonica.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.ciberian.agendatelefonica.R;
import br.com.ciberian.agendatelefonica.data.ContatoDBHelper;
import br.com.ciberian.agendatelefonica.models.Contato;

public class CadastroContatoActivity extends AppCompatActivity {

    public static Integer idContato;
    private EditText etNome;
    private EditText etSobrenome;
    private EditText etNumero;
    private EditText etEmail;
    private ContatoDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato);
        this.setTitle("Criar Contato");

        inicializar();
    }

    private void inicializar() {
        etNome = (EditText) findViewById(R.id.etNome);
        etSobrenome = (EditText) findViewById(R.id.etSobrenome);
        etNumero = (EditText) findViewById(R.id.etNumero);
        etEmail = (EditText) findViewById(R.id.etEmail);

        db = new ContatoDBHelper(this);

        if(idContato != null) {
            Contato contato = db.getById(idContato);

            etNome.setText(contato.getNome());
            etSobrenome.setText(contato.getSobrenome());
            etNumero.setText(contato.getNumero().toString());
            etEmail.setText(contato.getEmail());
        }

    }

    public void onClickSalvar(View v) {
        Contato contato = new Contato();

        contato.setNome(etNome.getText().toString());
        contato.setSobrenome(etSobrenome.getText().toString());
        contato.setNumero(Long.parseLong(etNumero.getText().toString()));
        contato.setEmail(etEmail.getText().toString());

        boolean salvou = false;

        if(idContato != null) {
            contato.setId(idContato);
            salvou = db.update(contato);
        } else {
            salvou = db.save(contato);
        }


        if(salvou) {
            Toast.makeText(this, "Contato salvo com sucesso.", Toast.LENGTH_SHORT).show();
            limparCampos();
        } else {
            Toast.makeText(this, "Falha ao salvar contato.", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private void limparCampos() {
        etNome.setText("");
        etSobrenome.setText("");
        etNumero.setText("");
        etEmail.setText("");
    }
}
