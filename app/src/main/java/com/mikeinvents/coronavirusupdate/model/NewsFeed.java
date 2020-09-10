package com.mikeinvents.coronavirusupdate.model;

public class NewsFeed {
    private String mImageUrl;
    private String mNewsTitle;
    private String mNewsContent;
    private String mNewsName;
    private String mWebsiteUrl;

    public NewsFeed(String imageUrl, String newsTitle, String newsContent, String newsName,
                    String websiteUrl){

        mImageUrl = imageUrl;
        mNewsTitle = newsTitle;
        mNewsContent = newsContent;
        mNewsName = newsName;
        mWebsiteUrl = websiteUrl;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getNewsTitle() {
        return mNewsTitle;
    }

    public String getNewsContent() {
        return mNewsContent;
    }

    public String getNewsName() {
        return mNewsName;
    }

    public String getWebsiteUrl() {
        return mWebsiteUrl;
    }
}
