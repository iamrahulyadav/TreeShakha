package controller.android.treedreamapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Plant implements Parcelable {
    private int id;
    private String title;
    private String subTitle;
    private double lattitude;
    private double longitude;
    private String address;
    private ArrayList<String> images;
    private String plantationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getPlantationDate() {
        return plantationDate;
    }

    public void setPlantationDate(String plantationDate) {
        this.plantationDate = plantationDate;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    private String treeName;


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Plant createFromParcel(Parcel in) {
            return new Plant(in);
        }

        public Plant[] newArray(int size) {
            return new Plant[size];
        }
    };


    // Parcelling part
    private Plant(Parcel in){
        this.id = in.readInt();
        this.title = in.readString();
        this.subTitle =  in.readString();
        this.address = in.readString();
        this.plantationDate = in.readString();
        this.lattitude = in.readDouble();
        this.longitude = in.readDouble();
        this.images = in.readArrayList(null);
    }

    public Plant(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.subTitle);
        dest.writeString(this.address);
        dest.writeString(this.plantationDate);
        dest.writeDouble(this.lattitude);
        dest.writeDouble(this.longitude);
        dest.writeList(images);
    }


}
