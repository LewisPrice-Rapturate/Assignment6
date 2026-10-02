/*
 * Assignment06
 * GenresFragment.java
 * Lew Price
 */

package com.example.assignment6;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class GenresFragment extends Fragment
{

    public interface GenresListener
    {
        void onGenreSelected(String genre);
    }

    private GenresListener mListener;

    public GenresFragment()
    {
        // Required empty public constructor
    }

    public static GenresFragment newInstance()
    {
        return new GenresFragment();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        if (context instanceof GenresListener)
        {
            mListener = (GenresListener) context;
        } else {
            throw new RuntimeException(context + " must implement GenresListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_genres, container, false);

        ListView listView = view.findViewById(R.id.lv_genres);
        ArrayList<String> genres = Data.getAllGenres();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                genres);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, v, position, id) ->
        {
            String selectedGenre = genres.get(position);
            if (mListener != null)
            {
                mListener.onGenreSelected(selectedGenre);
            }
        });

        return view;
    }
}
