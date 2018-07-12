package br.com.ciberian.agendatelefonica.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.com.ciberian.agendatelefonica.R;
import br.com.ciberian.agendatelefonica.activities.MainActivity;
import br.com.ciberian.agendatelefonica.data.APIClient;
import br.com.ciberian.agendatelefonica.data.APIInterface;
import br.com.ciberian.agendatelefonica.data.ContatoDBHelper;
import br.com.ciberian.agendatelefonica.models.Contato;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edmilson.neto on 13/09/2017.
 */

public class ContatoAdapter extends BaseAdapter {
    private final List<Contato> contatos;
    private final Activity activity;
    private APIInterface apiInterface;

    public ContatoAdapter(List<Contato> contatos, Activity activity) {
        this.contatos = contatos;
        this.activity = activity;

        this.apiInterface = APIClient.getClient().create(APIInterface.class);
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
                apiInterface.delete(contatos.get(position).getId()).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        contatos.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });

        return view;
    }
}
