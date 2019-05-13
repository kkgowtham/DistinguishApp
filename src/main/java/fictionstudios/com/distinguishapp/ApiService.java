package fictionstudios.com.distinguishapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("distinguish/readcomments.php")
    Call<List<CommentModel>> getComments(@Query("postid") String postid);

    @GET("distinguish/insertcomment.php")
    Call<CommentModel> getComment(@Query("name")String name,@Query("comment")String comment,
                                  @Query("postid")String postid,@Query("email")String email,@Query("imageurl")String imageurl);
}
