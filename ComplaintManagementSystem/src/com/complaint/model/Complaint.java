package com.complaint.model;

public class Complaint {

    private int complaintId;
    private int userId;
    private String complaintText;
    private String status;
    private int officerId;

    public Complaint() {
    }

    public Complaint(int userId, String complaintText) {
        this.userId = userId;
        this.complaintText = complaintText;
        this.status = "REGISTERED";
    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOfficerId() {
        return officerId;
    }

    public void setOfficerId(int officerId) {
        this.officerId = officerId;
    }
}