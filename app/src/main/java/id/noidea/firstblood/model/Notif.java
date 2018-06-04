package id.noidea.firstblood.model;

public class Notif {
    private int id_notif, id_post;

    public Notif() {
    }

    public Notif(int id_notif, int id_post) {
        this.id_notif = id_notif;
        this.id_post = id_post;
    }

    public int getId_notif() {
        return id_notif;
    }

    public void setId_notif(int id_notif) {
        this.id_notif = id_notif;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }
}
