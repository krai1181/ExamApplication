package com.example.examapplication.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Movie implements Comparable<Movie> {

    String title;
    String image;
    double rating;
    int releaseYear;
    String[] genre;

    public Movie() {
    }

    public Movie(String title, String image, double rating, int releaseYear, String[] genre) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public double getRating() {
        return rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String[] getGenre() {
        return genre;
    }


    @Override
    public int compareTo(Movie o) {
        int newYear =((Movie)o).getReleaseYear();
        /* For Ascending order*/
        return newYear - this.releaseYear;

        //return getReleaseYear().compareTo(o.getReleaseYear());;
}



    /* For Descending order do like this */
    //return compareage-this.studentage;
}

    /* {
                "title": "Dawn of the Planet of the Apes",
                "image": "https://api.androidhive.info/json/movies/1.jpg",
                "rating": 8.3,
                "releaseYear": 2014,
                "genre": ["Action", "Drama", "Sci-Fi"]
                },*/
