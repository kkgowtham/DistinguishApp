package fictionstudios.com.distinguishapp;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Query;

@Entity(tableName = "distinguish",indices = {@Index(value = "postid",unique = true)})
public class RoomPostModel {

    @PrimaryKey
    @ColumnInfo(name="postid")
     private    String postid;

    @ColumnInfo(name = "imageurl1")
    private String imageurl1;

    @ColumnInfo(name = "imageurl2")
    private String imageurl2;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "term1")
    private String term1;

    @ColumnInfo(name = "term2")
    private String term2;

    @ColumnInfo(name = "explain1")
    private String explain1;

    @ColumnInfo(name= "explain2")
    private String explain2;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "addedby")
    private String addedby;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "timestamp")
    private String timestamp;

    @ColumnInfo(name = "likes")
    private String likes;

    @ColumnInfo(name = "comments")
    private String comments;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

}

