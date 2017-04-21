package com.example.booklist.booklist;

/**
 * Created by Abdullah on 4/21/2017.
 */

public class mMovie {

    String title;
    String releaseDate ;
    String poster_path;
    double rate_avg;


    public  mMovie (){

    }
    public mMovie (String title , String releaseDate , String poster_path , double rate_avg){
        this.title = title;
        this.releaseDate = releaseDate;
        this.poster_path=poster_path;
        this.rate_avg =rate_avg;

    }

    public String getTitle() {
        return title;
    }
    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public double getRate_avg() {
        return rate_avg;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setRate_avg(double rate_avg) {
        this.rate_avg = rate_avg;
    }
}
