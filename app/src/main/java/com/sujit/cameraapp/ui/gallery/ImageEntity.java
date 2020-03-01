package com.sujit.cameraapp.ui.gallery;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageEntity implements Parcelable {

    private String Name;
    private String Path;
    private String URI;

    protected ImageEntity(Parcel in) {
        Name = in.readString();
        Path = in.readString();
        URI = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Path);
        dest.writeString(URI);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageEntity> CREATOR = new Creator<ImageEntity>() {
        @Override
        public ImageEntity createFromParcel(Parcel in) {
            return new ImageEntity(in);
        }

        @Override
        public ImageEntity[] newArray(int size) {
            return new ImageEntity[size];
        }
    };


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public static Creator<ImageEntity> getCREATOR() {
        return CREATOR;
    }

    public ImageEntity(String path) {
        Path = path;
    }
}
