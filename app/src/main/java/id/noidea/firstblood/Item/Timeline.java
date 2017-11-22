package id.noidea.firstblood.Item;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;

/**
 * Created by ziterz on 11/22/2017.
 */

public class Timeline implements Parcelable{
    String picture;
    String desc;
    String blood;
    String rhesus;
    String status;
    String date;

    public Timeline(String picture, String desc, String blood, String rhesus, String status, String link, String date) {
        this.picture = picture;
        this.desc = desc;
        this.blood = blood;
        this.rhesus = rhesus;
        this.status = status;
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getRhesus() {
        return rhesus;
    }

    public void setRhesus(String rhesus) {
        this.rhesus = rhesus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.picture);
        parcel.writeString(this.desc);
        parcel.writeString(this.blood);
        parcel.writeString(this.rhesus);
        parcel.writeString(this.status);
        parcel.writeString(this.date);
    }

    protected Timeline(Parcel in) {
        this.picture = in.readString();
        this.desc = in.readString();
        this.blood = in.readString();
        this.rhesus = in.readString();
        this.status = in.readString();
        this.date = in.readString();
    }
    public static final Parcelable.Creator<Timeline> CREATOR = new Parcelable.Creator<Timeline>() {
        @Override
        public Timeline createFromParcel(Parcel source) {
            return new Timeline(source);
        }

        @Override
        public Timeline[] newArray(int size) {
            return new Timeline[size];
        }
    };
}
