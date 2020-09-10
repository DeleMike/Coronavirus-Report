package com.mikeinvents.coronavirusupdate.model;

public class Country {
    private String mCountryName;
    private String mNumOfCases;
    private String mNumOfTodayCases;
    private String mNumOfActiveCases;
    private String mNumOfDeaths;
    private String mNumOfTodayDeaths;
    private String mRecoveries;

    public Country(String countryName, String numOfCases, String numOfActiveCases,
                   String numOfTodayCases, String numOfDeaths, String numOfTodayDeaths, String recoveries){

        mCountryName = countryName;
        mNumOfCases = numOfCases;
        mNumOfActiveCases = numOfActiveCases;
        mNumOfTodayCases = numOfTodayCases;
        mNumOfDeaths = numOfDeaths;
        mNumOfTodayDeaths = numOfTodayDeaths;
        mRecoveries = recoveries;

    }

    public String getCountryName() {
        return mCountryName;
    }

    public String getNumOfCases() {
        return mNumOfCases;
    }

    public String getNumOfTodayCases() {
        return mNumOfTodayCases;
    }

    public String getNumOfActiveCases() {
        return mNumOfActiveCases;
    }

    public String getNumOfDeaths() {
        return mNumOfDeaths;
    }

    public String getNumOfTodayDeaths() {
        return mNumOfTodayDeaths;
    }

    public String getRecoveries() {
        return mRecoveries;
    }
}
