package br.com.edmilsonneto.populartvshows.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import br.com.edmilsonneto.populartvshows.R;
import br.com.edmilsonneto.populartvshows.adapters.RecyclerViewAdapter;
import br.com.edmilsonneto.populartvshows.api.APIClient;
import br.com.edmilsonneto.populartvshows.api.APIInterface;

import br.com.edmilsonneto.populartvshows.models.TvShowList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private APIInterface apiInterface;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bind();
        initialize();


        Call call = apiInterface.getPopularTvShows(APIClient.apiKey, "en-US", "1");

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                Log.d("TAG",response.code()+"");

                String displayResponse = "";

                TvShowList tvShowList = (TvShowList) response.body();

                recyclerView.setAdapter(new RecyclerViewAdapter(getBaseContext(), tvShowList.getResults()));
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
            }
        });

    }

    private void initialize() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    private void bind() {
        recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
