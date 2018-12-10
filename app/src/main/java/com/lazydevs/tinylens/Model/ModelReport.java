package com.lazydevs.tinylens.Model;

public class ModelReport {

    String imageUrl;
    String reporterId;
    String reportedId;

    public ModelReport() {
    }

    public ModelReport(String imageUrl, String reporterId, String reportedId) {
        this.imageUrl = imageUrl;
        this.reporterId = reporterId;
        this.reportedId = reportedId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }

    public String getReportedId() {
        return reportedId;
    }

    public void setReportedId(String reportedId) {
        this.reportedId = reportedId;
    }
}
