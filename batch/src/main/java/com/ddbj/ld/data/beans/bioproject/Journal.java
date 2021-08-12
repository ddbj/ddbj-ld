package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Journal {
    private String journalTitle;
    private String year;
    private String volume;
    private String issue;
    private String pagesFrom;
    private String pagesTo;

    @JsonProperty("JournalTitle")
    public String getJournalTitle() { return journalTitle; }
    @JsonProperty("JournalTitle")
    public void setJournalTitle(String value) { this.journalTitle = value; }

    @JsonProperty("Year")
    public String getYear() { return year; }
    @JsonProperty("Year")
    public void setYear(String value) { this.year = value; }

    @JsonProperty("Volume")
    public String getVolume() { return volume; }
    @JsonProperty("Volume")
    public void setVolume(String value) { this.volume = value; }

    @JsonProperty("Issue")
    public String getIssue() { return issue; }
    @JsonProperty("Issue")
    public void setIssue(String value) { this.issue = value; }

    @JsonProperty("PagesFrom")
    public String getPagesFrom() { return pagesFrom; }
    @JsonProperty("PagesFrom")
    public void setPagesFrom(String value) { this.pagesFrom = value; }

    @JsonProperty("PagesTo")
    public String getPagesTo() { return pagesTo; }
    @JsonProperty("PagesTo")
    public void setPagesTo(String value) { this.pagesTo = value; }
}
