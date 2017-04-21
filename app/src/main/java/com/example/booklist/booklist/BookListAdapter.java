package com.example.booklist.booklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class BookListAdapter extends ArrayAdapter<BookList> {


     // Constructs a new {@link BookListAdapter}.
    public BookListAdapter(Context context, List<BookList> bookLists) {
        super(context, 0, bookLists);
    }

    /**
     * Returns a list item view that displays information about the booklists at the given position
     * in the list of booklists.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.booklist_list_item, parent, false);
        }

        // Find the booklist at the given position in the list of booklists
        BookList currentBooklist = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID title.
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentBooklist.getTitle());

        // Find the TextView in the list_item.xml layout with the ID author.
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        authorView.setText(currentBooklist.getAuthor());

        // Find the ImageView in the list_item.xml layout with the ID image.
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        // Get the URL of the image
        String imgURL = currentBooklist.getImageURL();
        // Display the provided image based on the URL by Picasso
        Picasso.with(getContext()).load(imgURL).into(imageView);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }


}
