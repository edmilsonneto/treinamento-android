package br.com.edmilsonneto.populartvshows.models;

/**
 * Created by Edmilson Neto on 22/10/2017.
 */

public class TvShow {
    private String name;
    private String poster_path;

    public String getName() {
        return name;
    }

    public String getPoster_path() {
        return "https://image.tmdb.org/t/p/w500/" + poster_path;
    }

}
