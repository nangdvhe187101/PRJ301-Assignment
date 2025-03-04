/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author ADMIN
 */
public class User {
    private String username;
    private String password;
    private String displayname;

    public String getUsernameString() {
        return username;
    }

    public void setUsernameString(String usernameString) {
        this.username = usernameString;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  

    public String getDisplaynameString() {
        return displayname;
    }

    public void setDisplaynameString(String displaynameString) {
        this.displayname = displaynameString;
    }

    public void setUsername(String username) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setDisplayname(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
