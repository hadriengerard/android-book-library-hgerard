package fr.hgerard;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import fr.hgerard.model.Book;

public class BookItemView extends CardView {

    private TextView nameTextView;
    private TextView priceTextView;
    private ImageView coverImageView;
    private RequestManager glideRequestManager;

    public BookItemView(Context context) {
        this(context, null);
    }

    public BookItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BookItemView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.glideRequestManager = Glide.with(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        priceTextView = (TextView) findViewById(R.id.priceTextView);
        coverImageView = (ImageView) findViewById(R.id.coverImageView);
    }

    public void bindView(Book book) {
        nameTextView.setText(book.getTitle());
        priceTextView.setText(String.format("%s€", String.valueOf(book.getPrice())));
        glideRequestManager
            .load(book.getCover())
            .centerCrop()
            .into(coverImageView);
    }
}
