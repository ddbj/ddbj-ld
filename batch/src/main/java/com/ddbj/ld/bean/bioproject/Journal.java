package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Journal {
    private String journalTitle;
    private String year;
    private String volume;
    private String issue;
    private String pagesFrom;
    private String pagesTo;

    @JsonProperty("JournalTitle")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getJournalTitle() { return journalTitle; }
    @JsonProperty("JournalTitle")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setJournalTitle(String value) { this.journalTitle = value; }

    @JsonProperty("Year")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getYear() { return year; }
    @JsonProperty("Year")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setYear(String value) { this.year = value; }

    @JsonProperty("Volume")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getVolume() { return volume; }
    @JsonProperty("Volume")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setVolume(String value) { this.volume = value; }

    @JsonProperty("Issue")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIssue() { return issue; }
    @JsonProperty("Issue")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIssue(String value) { this.issue = value; }

    @JsonProperty("PagesFrom")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPagesFrom() { return pagesFrom; }
    @JsonProperty("PagesFrom")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPagesFrom(String value) { this.pagesFrom = value; }

    @JsonProperty("PagesTo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPagesTo() { return pagesTo; }
    @JsonProperty("PagesTo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPagesTo(String value) { this.pagesTo = value; }
}