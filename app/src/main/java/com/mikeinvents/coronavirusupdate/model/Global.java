package com.mikeinvents.coronavirusupdate.model;

public class Global {
    private String mHeader;
    private String mCount;
    public Global(String header, String count){
        mHeader = header;
        mCount = count;
    }

    public String getHeader() {
        return mHeader;
    }

    public String getCount() {
        return mCount;
    }

}
