package fictionstudios.com.distinguishapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class PostModel implements Parcelable{

    private String id;
    private String imageurl1;
    private String imageurl2;
    private String description;
    private String term1;
    private String term2;
    private String explain1;
    private String explain2;
    private String date;
    private String addedby;
    private String category;
    private String timestamp;
    private String likes;
    private String comments;

    protected PostModel(Parcel in) {
        id = in.readString();
        imageurl1 = in.readString();
        imageurl2 = in.readString();
        description = in.readString();
        term1 = in.readString();
        term2 = in.readString();
        explain1 = in.readString();
        explain2 = in.readString();
        date = in.readString();
        addedby = in.readString();
        category = in.readString();
        timestamp = in.readString();
        likes = in.readString();
        comments = in.readString();
    }

    public static final Creator<PostModel> CREATOR = new Creator<PostModel>() {
        @Override
        public PostModel createFromParcel(Parcel in) {
            return new PostModel(in);
        }

        @Override
        public PostModel[] newArray(int size) {
            return new PostModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageurl1() {
        return imageurl1;
    }

    public void setImageurl1(String imageurl1) {
        this.imageurl1 = imageurl1;
    }

    public String getImageurl2() {
        return imageurl2;
    }

    public void setImageurl2(String imageurl2) {
        this.imageurl2 = imageurl2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTerm1() {
        return term1;
    }

    public void setTerm1(String term1) {
        this.term1 = term1;
    }

    public String getTerm2() {
        return term2;
    }

    public void setTerm2(String term2) {
        this.term2 = term2;
    }

    public String getExplain1() {
        return explain1;
    }

    public void setExplain1(String explain1) {
        this.explain1 = explain1;
    }

    public String getExplain2() {
        return explain2;
    }

    public void setExplain2(String explain2) {
        this.explain2 = explain2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(imageurl1);
        dest.writeString(imageurl2);
        dest.writeString(description);
        dest.writeString(term1);
        dest.writeString(term2);
        dest.writeString(explain1);
        dest.writeString(explain2);
        dest.writeString(date);
        dest.writeString(addedby);
        dest.writeString(category);
        dest.writeString(timestamp);
        dest.writeString(likes);
        dest.writeString(comments);
    }
}