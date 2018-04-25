package id.noidea.firstblood.model;

public class Users {
    private String username, email, password, api_key;

    public Users() {
    }

    public Users(String username, String email, String password, String api_key) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.api_key = api_key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }
}
