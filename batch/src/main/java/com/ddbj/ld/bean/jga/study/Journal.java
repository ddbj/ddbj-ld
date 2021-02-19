package com.ddbj.ld.bean.jga.study;

import com.fasterxml.jackson.annotation.*;

public class Journal {
    private String journalTitle;
    private String year;
    private String volume;
    private String issue;
    private String pagesFrom;
    private String pagesTo;

    @JsonProperty("JOURNAL_TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getJournalTitle() { return journalTitle; }
    @JsonProperty("JOURNAL_TITLE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setJournalTitle(String value) { this.journalTitle = value; }

    @JsonProperty("YEAR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getYear() { return year; }
    @JsonProperty("YEAR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setYear(String value) { this.year = value; }

    @JsonProperty("VOLUME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getVolume() { return volume; }
    @JsonProperty("VOLUME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setVolume(String value) { this.volume = value; }

    @JsonProperty("ISSUE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIssue() { return issue; }
    @JsonProperty("ISSUE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIssue(String value) { this.issue = value; }

    @JsonProperty("PAGES_FROM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPagesFrom() { return pagesFrom; }
    @JsonProperty("PAGES_FROM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPagesFrom(String value) { this.pagesFrom = value; }

    @JsonProperty("PAGES_TO")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPagesTo() { return pagesTo; }
    @JsonProperty("PAGES_TO")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPagesTo(String value) { this.pagesTo = value; }
}
