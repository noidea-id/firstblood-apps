package id.noidea.firstblood.model;

public class Posting {
    private String username, nama, foto_profil, goldar, rhesus, descrip, rumah_sakit, status, inserted_at, updated_at;

    public Posting() {
    }

    public Posting(String username, String nama, String foto_profil, String goldar, String rhesus, String descrip, String rumah_sakit, String status, String inserted_at, String updated_at) {
        this.username = username;
        this.nama = nama;
        this.foto_profil = foto_profil;
        this.goldar = goldar;
        this.rhesus = rhesus;
        this.descrip = descrip;
        this.rumah_sakit = rumah_sakit;
        this.status = status;
        this.inserted_at = inserted_at;
        this.updated_at = updated_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto_profil() {
        return foto_profil;
    }

    public void setFoto_profil(String foto_profil) {
        this.foto_profil = foto_profil;
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

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getRumah_sakit() {
        return rumah_sakit;
    }

    public void setRumah_sakit(String rumah_sakit) {
        this.rumah_sakit = rumah_sakit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInserted_at() {
        return inserted_at;
    }

    public void setInserted_at(String inserted_at) {
        this.inserted_at = inserted_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
