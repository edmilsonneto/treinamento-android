package br.com.ciberian.agendatelefonica.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.com.ciberian.agendatelefonica.R;
import br.com.ciberian.agendatelefonica.data.ContatoDBHelper;
import br.com.ciberian.agendatelefonica.models.Contato;

/**
 * Created by edmilson.neto on 13/09/2017.
 */

public class ContatoAdapter extends BaseAdapter {
    private final List<Contato> contatos;
    private final Activity activity;
    private ContatoDBHelper db;

    public ContatoAdapter(List<Contato> contatos, Activity activity) {
        this.contatos = contatos;
        this.activity = activity;

        db = new ContatoDBHelper(this.activity);
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Object getItem(int i) {
        return contatos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater()
                .inflate(R.layout.layout_lista_contato, parent, false);

        Contato contato = contatos.get(position);

        TextView txNomeSobrenome = view.findViewById(R.id.txNomeSobrenome);
        TextView txNumero = view.findViewById(R.id.txNumero);
        TextView txEmail = view.findViewById(R.id.txEmail);

        txNomeSobrenome.setText(contato.getNome() + " " + contato.getSobrenome());
        txNumero.setText(contato.getNumero().toString());
        txEmail.setText(contato.getEmail());

        Button btnDeletar = view.findViewById(R.id.btnDeletar);
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contato contato = contatos.get(position);

                db.deleteById(contato.getId());
                contatos.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
