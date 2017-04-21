package com.example.booklist.booklist;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity implements LoaderCallbacks<List<BookList>> {

    private static final String LOG_TAG = BookListActivity.class.getName();

    // URL for Book Listing data from the Google Book dataset
    private static String BOOK_LIST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=10";

    // Constant value for the BookList loader ID
    private static final int BOOKLIST_LOADER_ID = 1;

    // Adapter for the list of BookList
    private BookListAdapter mAdapter;

    // TextView that is displayed when the list is empty
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklist_activity);

        // Create OnClickListener of ImageView (search_icon)
        ImageView imageView = (ImageView) findViewById(R.id.search_Icon);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // To clear previous adapter
                mAdapter.clear();

                // Get a reference to the ConnectivityManager to check state of network connectivity
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                View loadingIndicator = findViewById(R.id.loading_indicator);

                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {

                    // String to get the keyword from EditText
                    String keyWord;
                    // String to check if keyword isn't empty
                    String keyWordCheck;

                    EditText editText = (EditText) findViewById(R.id.search_EditText);
                    keyWord = editText.getText().toString();

                    // To remove all spaces in the keyword to check it
                    keyWordCheck = keyWord.trim();

                    if (keyWordCheck.matches("")) {
                        Toast.makeText(BookListActivity.this, "Please make sure to type your keyword", Toast.LENGTH_SHORT).show();
                    } else {
                        // First, Show loading indicator so error message will be visible
                        loadingIndicator.setVisibility(View.VISIBLE);

                        // Hide empty status
                        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
                        mEmptyStateTextView.setText("");
                        // Clear out existing data.
                        mAdapter.clear();

                        keyWord = keyWord.replace(' ', '+');
                        // New URL with the @keyWord
                        BOOK_LIST_URL = "https://www.googleapis.com/books/v1/volumes?q=" + keyWord;


                        // To restart Loader by new @BOOK_LIST_URL
                        getLoaderManager().restartLoader(BOOKLIST_LOADER_ID, null, BookListActivity.this);

                    }
                } else {
                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible
                    loadingIndicator.setVisibility(View.GONE);

                    // Update empty state with no connection error message
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                }
            }
        });

        // Find a reference to the {@link ListView} in the layout
        ListView booklistListView = (ListView) findViewById(R.id.list);

        // First launches of empty status
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        booklistListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of BookList as input
        mAdapter = new BookListAdapter(this, new ArrayList<BookList>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        booklistListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected booklist.
        booklistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current booklist that was clicked on
                BookList currentBooklist = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri booklistUri = Uri.parse(currentBooklist.getUrl());

                // Create a new intent to view the booklist URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, booklistUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }

            public Loader<List<BookList>> onCreateLoader(int i, Bundle bundle) {
                // Create a new loader for the given URL
                return new BookListLoader(BookListActivity.this, BOOK_LIST_URL);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOKLIST_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<BookList>> onCreateLoader(int i, Bundle bundle) {

        // Create a new loader for the given URL
        return new BookListLoader(this, BOOK_LIST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<BookList>> loader, List<BookList> bookLists) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No booklist found."
        mEmptyStateTextView.setText(R.string.empty_booklist);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Booklist}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (bookLists != null && !bookLists.isEmpty()) {
            mAdapter.addAll(bookLists);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<BookList>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

}
