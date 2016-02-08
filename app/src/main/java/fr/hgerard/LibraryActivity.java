package fr.hgerard;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import fr.hgerard.model.Book;
import timber.log.Timber;

public class LibraryActivity extends AppCompatActivity implements BookRecyclerAdapter.OnBookClickListener {

    static { Timber.plant(new Timber.DebugTree()); }

    private Fragment bookListFragment;
    private Book currentBook;
    private boolean landscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        if (savedInstanceState != null) {
            clearFragmentBackStack();
        }

        this.landscape = getResources().getBoolean(R.bool.landscape);
        this.bookListFragment = getSupportFragmentManager().findFragmentByTag(BookListFragment.class.getSimpleName());

        if (bookListFragment == null) {
            this.bookListFragment = new BookListFragment();
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFrameLayout, bookListFragment, BookListFragment.class.getSimpleName())
                .commit();
        }
    }

    private void showBookDetails(Book book) {
        this.currentBook = book;
        boolean show = (book != null);

        if (show) {
            clearFragmentBackStack();

            Bundle args = new Bundle();
            args.putParcelable(Book.class.getName(), book);
            Fragment bookDetailsFragment = new BookFragment();
            bookDetailsFragment.setArguments(args);

            int destContainerID = landscape ?  R.id.secondFrameLayout : R.id.mainFrameLayout;
            getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(BookListFragment.class.getSimpleName())
                .replace(destContainerID, bookDetailsFragment, BookFragment.class.getSimpleName())
                .commit();
        }

        if (landscape) {
            findViewById(R.id.secondFrameLayout).setVisibility(show ? View.VISIBLE : View.GONE);
            ((MultiColumnListView) bookListFragment).onAvailableSpaceChange(!show);
        }
    }

    @Override
    public void onBookClick(Book book) {
        // Toast.makeText(this, book.getTitle(), Toast.LENGTH_SHORT).show();
        this.showBookDetails(book);
    }

    @Override
    public void onBackPressed() {
        this.showBookDetails(null);
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (currentBook != null) {
            savedInstanceState.putParcelable(Book.class.getName(), currentBook);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            this.showBookDetails((Book) savedInstanceState.getParcelable(Book.class.getName()));
        }
    }

    private void clearFragmentBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack(fm.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}

