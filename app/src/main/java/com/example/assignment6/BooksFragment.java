/*
 * Assignment06
 * BooksFragment.java
 * Lew Price
 */

package com.example.assignment6;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class BooksFragment extends Fragment
{

    private static final String ARG_GENRE = "ARG_GENRE";
    private String mGenre;
    private BooksListener mListener;

    public interface BooksListener
    {
        void onBookSelected(Book book);
        void onBackFromBooks();
    }

    public BooksFragment()
    {
        // Required empty public constructor
    }

    public static BooksFragment newInstance(String genre)
    {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GENRE, genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGenre = getArguments().getString(ARG_GENRE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        if (context instanceof BooksListener)
        {
            mListener = (BooksListener) context;
        }
        else
        {
            throw new RuntimeException(context + " must implement BooksListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        TextView tvTitle = view.findViewById(R.id.tv_books_title);
        ListView listView = view.findViewById(R.id.lv_books);
        Button btnBack = view.findViewById(R.id.btn_books_back);

        if (mGenre != null)
        {
            tvTitle.setText(mGenre);
        }

        ArrayList<Book> booksList = Data.getBooksByGenre(mGenre);
        if (booksList == null)
        {
            booksList = new ArrayList<>();
        }

        BookAdapter adapter = new BookAdapter(requireContext(), booksList);
        listView.setAdapter(adapter);

        final ArrayList<Book> finalBooks = booksList;
        listView.setOnItemClickListener((parent, v, position, id) ->
        {
            Book selectedBook = finalBooks.get(position);
            if (mListener != null) {
                mListener.onBookSelected(selectedBook);
            }
        });

        btnBack.setOnClickListener(v ->
        {
            if (mListener != null)
            {
                mListener.onBackFromBooks();
            }
        });

        return view;
    }

    private static class BookAdapter extends ArrayAdapter<Book>
    {

        public BookAdapter(@NonNull Context context, @NonNull List<Book> books)
        {
            super(context, 0, books);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            View itemView = convertView;
            if (itemView == null)
            {
                itemView = LayoutInflater.from(getContext()).inflate(R.layout.book_item, parent, false);
            }

            Book book = getItem(position);

            TextView tvTitle = itemView.findViewById(R.id.tv_book_title);
            TextView tvAuthor = itemView.findViewById(R.id.tv_book_author);
            TextView tvGenre = itemView.findViewById(R.id.tv_book_genre);
            TextView tvYear = itemView.findViewById(R.id.tv_book_year);

            if (book != null)
            {
                tvTitle.setText(book.getTitle());
                tvAuthor.setText(String.format("Author: %s", book.getAuthor()));
                tvGenre.setText(String.format("Genre: %s", book.getGenre()));
                tvYear.setText(String.format("Year: %d", book.getYear()));
            }

            return itemView;
        }
    }
}
