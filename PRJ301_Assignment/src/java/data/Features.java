/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Features {
    private int FeaturesID;
    private String url;
    private ArrayList<Roles> roles = new ArrayList<>();

    public int getFeaturesID() {
        return FeaturesID;
    }

    public void setFeaturesID(int FeaturesID) {
        this.FeaturesID = FeaturesID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
