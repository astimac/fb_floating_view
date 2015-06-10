package com.astimac.fb.popup.example.mocks;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alex on 6/10/15.
 */
public class MockModel implements Parcelable {

    private String imageUrl;

    private int numberOfMessages;

    public MockModel() {

    }

    public MockModel(String imageUrl, int numberOfMessages) {
        this.imageUrl = imageUrl;
        this.numberOfMessages = numberOfMessages;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
        dest.writeInt(this.numberOfMessages);
    }

    protected MockModel(Parcel in) {
        this.imageUrl = in.readString();
        this.numberOfMessages = in.readInt();
    }

    public static final Parcelable.Creator<MockModel> CREATOR = new Parcelable.Creator<MockModel>() {
        public MockModel createFromParcel(Parcel source) {
            return new MockModel(source);
        }

        public MockModel[] newArray(int size) {
            return new MockModel[size];
        }
    };

    @Override
    public String toString() {
        return "MockModel{" +
                "imageUrl='" + imageUrl + '\'' +
                ", numberOfMessages=" + numberOfMessages +
                '}';
    }
}
