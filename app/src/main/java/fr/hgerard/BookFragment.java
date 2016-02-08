package fr.hgerard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import fr.hgerard.model.Book;
import timber.log.Timber;

public class BookFragment extends Fragment {

    private Book book;

    private TextView titleTextView;
    private TextView priceTextView;
    private TextView isbnTextView;
    private ImageView coverImageView;
    private Button addToCartButton;
    private RequestManager glideRequestManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (this.getArguments() != null) {
            book = (Book) this.getArguments().get(Book.class.getName());
            if (book != null) {
                Timber.i(String.format("Détails du livre : %s", book.getTitle()));
                this.glideRequestManager = Glide.with(context);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_fragment, container, false);
        view.setVisibility(book != null? View.VISIBLE : View.GONE);

        titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        priceTextView = (TextView) view.findViewById(R.id.priceTextView);
        isbnTextView = (TextView) view.findViewById(R.id.isbnTextView);
        coverImageView = (ImageView) view.findViewById(R.id.coverImageView);
        addToCartButton = (Button) view.findViewById(R.id.addToCartButton);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (book != null) {

            titleTextView.setText(book.getTitle());
            priceTextView.setText(String.format("%s€", String.valueOf(book.getPrice())));
            isbnTextView.setText(book.getIsbn());

            glideRequestManager
                .load(book.getCover())
                .centerCrop()
                .into(coverImageView);

            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String confirmMsg = getResources().getString(R.string.confirmationAjout);
                    Toast.makeText(getContext(), confirmMsg + book.getTitle(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
