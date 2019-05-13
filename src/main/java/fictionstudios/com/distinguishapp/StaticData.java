package fictionstudios.com.distinguishapp;

public class StaticData {
   static private String postId;

    public static String getPostId() {
        return postId;
    }

    public static void setPostId( String postId) {
        StaticData.postId = postId;
    }
}
