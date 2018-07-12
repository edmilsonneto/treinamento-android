package br.com.ciberian.agendatelefonica.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.ciberian.agendatelefonica.R;
import br.com.ciberian.agendatelefonica.data.APIClient;
import br.com.ciberian.agendatelefonica.data.APIInterface;
import br.com.ciberian.agendatelefonica.data.ContatoDBHelper;
import br.com.ciberian.agendatelefonica.models.Contato;
import br.com.ciberian.agendatelefonica.adapters.ContatoAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView lvContatos;
    private APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar();
    }

    private void inicializar() {

        lvContatos = (ListView) findViewById(R.id.lvContatos);

        lvContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contato contato = (Contato) parent.getItemAtPosition(position);

                CadastroContatoActivity.idContato = contato.getId();
                Intent i = new Intent(MainActivity.this, CadastroContatoActivity.class);
                startActivity(i);
            }
        });
    }

    private void carregarContatos() {
        apiInterface = APIClient.getClient().create(APIInterface.class);

        apiInterface.list().enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                lvContatos.setAdapter(new ContatoAdapter((List<Contato>) response.body(), MainActivity.this));
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
            }
        });
    }

    @Override
    protected void onResume() {
        carregarContatos();
        super.onResume();
    }

    public void onClickAdicionarContato (View v) {
        CadastroContatoActivity.idContato = null;
        Intent i = new Intent(MainActivity.this, CadastroContatoActivity.class);
        startActivity(i);
    }


}
