/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;
import java.sql.*;
import java.util.List;
/**
 *
 * @author ADMIN
 */
public class LeaveRequests extends DBEntity{
    private String title;
    private Date from;
    private Date to;
    private String reason;
    private int status;
    private java.util.Date createddate;
    private String processedByDisplayName;

    public String getProcessedByDisplayName() {
        return processedByDisplayName;
    }

    public void setProcessedByDisplayName(String processedByDisplayName) {
        this.processedByDisplayName = processedByDisplayName;
    }

    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public java.util.Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(java.util.Date createddate) {
        this.createddate = createddate;
    }

    public void add(List<LeaveRequests> leaveRequests) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
}
