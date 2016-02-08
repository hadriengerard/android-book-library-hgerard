package fr.hgerard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.hgerard.model.Book;
import fr.hgerard.services.BookService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import timber.log.Timber;

public class BookListFragment extends Fragment implements MultiColumnListView {

    private ArrayList<Book> books = new ArrayList<>();
    private BookRecyclerAdapter bookRecyclerAdapter;
    private GridLayoutManager gLayoutManager;
    private boolean landscape;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_list_fragment, container, false);

        landscape = getResources().getBoolean(R.bool.landscape);

        gLayoutManager = new GridLayoutManager(super.getContext(), landscape ? 2 : 1);
        bookRecyclerAdapter = new BookRecyclerAdapter(super.getContext(), books);
        RecyclerView bookRecyclerView = (RecyclerView) view.findViewById(R.id.book_recycler_view);
        bookRecyclerView.setHasFixedSize(true);
        bookRecyclerView.setLayoutManager(gLayoutManager);
        bookRecyclerView.setAdapter(bookRecyclerAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadBookList();
    }

    private void loadBookList() {
        Call<List<Book>> call = BookService.getInstance().listBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Response<List<Book>> response, Retrofit retrofit) {
                books.clear();
                books.addAll(response.body());
                bookRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                Timber.e(t, "Une erreur est survenue");
            }
        });
    }

    @Override
    public void onAvailableSpaceChange(boolean fullSpaceAvailable) {
        if (gLayoutManager != null) {
            gLayoutManager.setSpanCount((landscape && fullSpaceAvailable) ? 2 : 1);
        }
    }

}
