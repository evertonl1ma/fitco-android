package com.mangobits.fitco.product

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator

import com.mangobits.fitco.R

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieList = ArrayList<Movie>()

        movieList.add(Movie("Schindler's List", "Biography, Drama, History", 1993))
        movieList.add(Movie("Pulp Fiction", "Crime, Drama", 1994))
        movieList.add(Movie("No Country for Old Men", "Crime, Drama, Thriller", 2007))
        movieList.add(Movie("Léon: The Professional", "Crime, Drama, Thriller", 1994))
        movieList.add(Movie("Fight Club", "Drama", 1999))
        movieList.add(Movie("Forrest Gump", "Drama, Romance", 1994))
        movieList.add(Movie("The Shawshank Redemption", "Crime, Drama", 1994))
        movieList.add(Movie("The Godfather", "Crime, Drama", 1972))
        movieList.add(Movie("A Beautiful Mind", "Biography, Drama", 2001))
        movieList.add(Movie("Good Will Hunting", "Drama", 1997))

        val adapter = RecAdapter(movieList)

        val recyclerView = findViewById<RecyclerView>(R.id.recview)

        (recyclerView.getItemAnimator() as SimpleItemAnimator).supportsChangeAnimations = false

        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setAdapter(adapter)
        recyclerView.setHasFixedSize(true)
    }
}