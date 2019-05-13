package fictionstudios.com.distinguishapp;

public class CommentModel {

    private String postid;
   private String comment;
   private String name;
   private String imageurl;
    private String emailid;
   private String timestamp;
   private String id;

    public CommentModel(String postid, String comment, String name, String imageurl, String emailid, String timestamp,String id) {
        this.postid = postid;
        this.comment = comment;
        this.name = name;
        this.imageurl = imageurl;
        this.emailid = emailid;
        this.timestamp = timestamp;
        this.id=id;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
