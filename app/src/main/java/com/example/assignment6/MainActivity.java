/*
 * Assignment06
 * MainActivity.java
 * Lew Price
 */

package com.example.assignment6;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements
            GenresFragment.GenresListener,
            BooksFragment.BooksListener,
            BookDetailsFragment.BookDetailsListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, GenresFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onGenreSelected(String genre)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, BooksFragment.newInstance(genre))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBookSelected(Book book)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, BookDetailsFragment.newInstance(book))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackFromBooks()
    {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackFromBookDetails()
    {
        getSupportFragmentManager().popBackStack();
    }
}
