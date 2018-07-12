package br.com.ciberian.agendatelefonica.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.ciberian.agendatelefonica.R;
import br.com.ciberian.agendatelefonica.adapters.ContatoAdapter;
import br.com.ciberian.agendatelefonica.data.APIClient;
import br.com.ciberian.agendatelefonica.data.APIInterface;
import br.com.ciberian.agendatelefonica.data.ContatoDBHelper;
import br.com.ciberian.agendatelefonica.models.Contato;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroContatoActivity extends AppCompatActivity {

    public static Integer idContato;
    private EditText etNome;
    private EditText etSobrenome;
    private EditText etNumero;
    private EditText etEmail;

    private APIInterface apiInterface;

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

        apiInterface = APIClient.getClient().create(APIInterface.class);

        apiInterface.get(idContato).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Contato contato = (Contato) response.body();

                etNome.setText(contato.getNome());
                etSobrenome.setText(contato.getSobrenome());
                etNumero.setText(contato.getNumero().toString());
                etEmail.setText(contato.getEmail());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
            }
        });

    }

    public void onClickSalvar(View v) {
        Contato contato = new Contato();

        contato.setNome(etNome.getText().toString());
        contato.setSobrenome(etSobrenome.getText().toString());
        contato.setNumero(Long.parseLong(etNumero.getText().toString()));
        contato.setEmail(etEmail.getText().toString());

        boolean salvou = false;

        if(idContato != null) {
            apiInterface.update(contato, idContato).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    emitirMensagemSucesso();
                    limparCampos();
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                    emitirMensagemFalha();
                    call.cancel();
                }
            });
        } else {
            apiInterface.save(contato).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    emitirMensagemSucesso();
                    limparCampos();
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                    emitirMensagemFalha();
                    call.cancel();
                }
            });
        }

        finish();
    }

    private void emitirMensagemFalha() {
        Toast.makeText(CadastroContatoActivity.this, "Falha ao salvar contato.", Toast.LENGTH_SHORT).show();
    }

    private void emitirMensagemSucesso() {
        Toast.makeText(CadastroContatoActivity.this, "Contato salvo com sucesso.", Toast.LENGTH_SHORT).show();
    }

    private void limparCampos() {
        etNome.setText("");
        etSobrenome.setText("");
        etNumero.setText("");
        etEmail.setText("");
    }
}
