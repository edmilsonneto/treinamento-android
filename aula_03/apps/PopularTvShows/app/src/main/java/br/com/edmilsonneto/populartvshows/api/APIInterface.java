package br.com.edmilsonneto.populartvshows.api;

import br.com.edmilsonneto.populartvshows.models.TvShowList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Edmilson Neto on 22/10/2017.
 */

public interface APIInterface {

    @GET("/3/tv/popular")
    Call<TvShowList> getPopularTvShows(@Query("api_key") String apiKey,
                                       @Query("language") String language,
                                       @Query("page") String page);
}
