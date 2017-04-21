package com.example.booklist.booklist;

public class BookList {

    // Title of the booklist
    private String mTitle;

    // Author of the booklist
    private String mAuthor;

    // Image URL of the booklist
    private String mImageURL;

    // Website URL of the BookList
    private String mUrl;

     // Constructs a new {@link BookList} object.
    public BookList(String title, String author, String ImgeURL, String url) {
        mTitle = title;
        mAuthor = author;
        mImageURL = ImgeURL;
        mUrl = url;
    }

    /**
     * Returns the title of the BookList.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the author of the BookList.
     */
    public String getAuthor() {
        return mAuthor;
    }


    /**
     * Returns the website URL to find more information about the BookList.
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Return the image URL of the booklist.
     */
    public String getImageURL() {return mImageURL;}

}
