package controller.android.treedreamapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GiftCategory implements Parcelable {
    private int id;
    private String name;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private int price;
    public GiftCategory(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String icon;


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public GiftCategory createFromParcel(Parcel in) {
            return new GiftCategory(in);
        }

        public GiftCategory[] newArray(int size) {
            return new GiftCategory[size];
        }
    };


    // Parcelling part
    public GiftCategory(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.price =  in.readInt();
        this.icon = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.price);
        dest.writeString(this.icon);
    }


}
