package controller.android.treedreamapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
    private int orderId;
    private String title;
    private String orderDate;

    public Order(){

    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private int amount;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    private String thumbnail;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };


    // Parcelling part
    public Order(Parcel in){
        this.orderId = in.readInt();
        this.title = in.readString();
        this.amount =  in.readInt();
        this.status = in.readString();
        this.thumbnail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.orderId);
        dest.writeString(this.title);
        dest.writeInt(this.amount);
        dest.writeString(this.status);
        dest.writeString(this.thumbnail);
    }

}
