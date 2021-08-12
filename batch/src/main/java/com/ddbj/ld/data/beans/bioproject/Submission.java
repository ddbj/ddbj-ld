package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Submission {
    private LocalDate submitted;
    private LocalDate lastUpdate;
    private String status;
    private String relatedTo;
    private String submissionID;
    private Description description;
    private Context context;
    private Action action;

    @JsonProperty("submitted")
    public LocalDate getSubmitted() { return submitted; }
    @JsonProperty("submitted")
    public void setSubmitted(LocalDate value) { this.submitted = value; }

    @JsonProperty("last_update")
    public LocalDate getLastUpdate() { return lastUpdate; }
    @JsonProperty("last_update")
    public void setLastUpdate(LocalDate value) { this.lastUpdate = value; }

    @JsonProperty("status")
    public String getStatus() { return status; }
    @JsonProperty("status")
    public void setStatus(String value) { this.status = value; }

    @JsonProperty("related_to")
    public String getRelatedTo() { return relatedTo; }
    @JsonProperty("related_to")
    public void setRelatedTo(String value) { this.relatedTo = value; }

    @JsonProperty("submission_id")
    public String getSubmissionID() { return submissionID; }
    @JsonProperty("submission_id")
    public void setSubmissionID(String value) { this.submissionID = value; }

    @JsonProperty("Description")
    public Description getDescription() { return description; }
    @JsonProperty("Description")
    public void setDescription(Description value) { this.description = value; }

    @JsonProperty("Context")
    public Context getContext() { return context; }
    @JsonProperty("Context")
    public void setContext(Context value) { this.context = value; }

    @JsonProperty("Action")
    public Action getAction() { return action; }
    @JsonProperty("Action")
    public void setAction(Action value) { this.action = value; }
}
