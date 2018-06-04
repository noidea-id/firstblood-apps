package id.noidea.firstblood.model;

public class Users {
    private String username, email, password, api_key, nama, goldar, rhesus;
    private String no_hp, foto_profil, role;

    public Users() {
    }

    public Users(String username, String email, String password, String api_key, String nama, String goldar, String rhesus, String no_hp, String foto_profil, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.api_key = api_key;
        this.nama = nama;
        this.goldar = goldar;
        this.rhesus = rhesus;
        this.no_hp = no_hp;
        this.foto_profil = foto_profil;
        this.role = role;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGoldar() {
        return goldar;
    }

    public void setGoldar(String goldar) {
        this.goldar = goldar;
    }

    public String getRhesus() {
        return rhesus;
    }

    public void setRhesus(String rhesus) {
        this.rhesus = rhesus;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getFoto_profil() {
        return foto_profil;
    }

    public void setFoto_profil(String foto_profil) {
        this.foto_profil = foto_profil;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
