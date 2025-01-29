package campus.u2.entrysystem.Utilities;

import com.fasterxml.jackson.annotation.JsonIgnore;
public class LoginUser {

    private String user;
    private String token;

    // Default constructor
    public LoginUser() {
    }

    // Constructor without password
    public LoginUser(String user, String token) {
        this.user = user;
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
