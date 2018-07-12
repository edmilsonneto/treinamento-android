package br.com.ciberian.agendatelefonica.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.ciberian.agendatelefonica.R;
import br.com.ciberian.agendatelefonica.data.ContatoDBHelper;
import br.com.ciberian.agendatelefonica.models.Contato;
import br.com.ciberian.agendatelefonica.adapters.ContatoAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView lvContatos;
    private ContatoDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar();
    }

    private void inicializar() {
        db = new ContatoDBHelper(this);

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

        carregarContatos();
    }

    private void carregarContatos() {
        lvContatos.setAdapter(new ContatoAdapter(db.getAll(), this));
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
