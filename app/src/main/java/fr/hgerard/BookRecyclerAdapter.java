package fr.hgerard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import fr.hgerard.model.Book;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.ViewHolder> {

    private final List<Book> books;
    private final LayoutInflater inflater;
    private OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }


    public BookRecyclerAdapter(Context context, List<Book> books) {
        this.books = books;
        inflater = LayoutInflater.from(context);
        this.listener = (OnBookClickListener) context;
    }

    @Override
    public BookRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.custom_view_item_book, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookRecyclerAdapter.ViewHolder holder, int position) {
        BookItemView view = ((BookItemView) holder.view);
        final Book book = books.get(position);

        view.bindView(book);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBookClick(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

}