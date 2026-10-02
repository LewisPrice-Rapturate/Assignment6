/*
 * Assignment06
 * BookDetailsFragment.java
 * Lew Price
 */

package com.example.assignment6;

import static java.lang.String.*;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BookDetailsFragment extends Fragment
{

    private static final String ARG_BOOK = "ARG_BOOK";
    private Book mBook;
    private BookDetailsListener mListener;

    public interface BookDetailsListener
    {
        void onBackFromBookDetails();
    }

    public BookDetailsFragment()
    {
        // Required empty public constructor
    }

    public static BookDetailsFragment newInstance(Book book)
    {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mBook = getArguments().getSerializable(ARG_BOOK, Book.class);
        }
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        if (context instanceof BookDetailsListener)
        {
            mListener = (BookDetailsListener) context;
        } else {
            throw new RuntimeException(context + " must implement BookDetailsListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        TextView tvTitle = view.findViewById(R.id.tv_detail_title);
        TextView tvAuthor = view.findViewById(R.id.tv_detail_author);
        TextView tvGenre = view.findViewById(R.id.tv_detail_genre);
        TextView tvYear = view.findViewById(R.id.tv_detail_year);
        Button btnBack = view.findViewById(R.id.btn_details_back);

        if (mBook != null)
        {
            tvTitle.setText(format("Title: %s", mBook.getTitle()));
            tvAuthor.setText(format("Author: %s", mBook.getAuthor()));
            tvGenre.setText(format("Genre: %s", mBook.getGenre()));
            tvYear.setText(format("Year: %d", mBook.getYear()));
        }

        btnBack.setOnClickListener(v ->
        {
            if (mListener != null)
            {
                mListener.onBackFromBookDetails();
            }
        });

        return view;
    }
}
