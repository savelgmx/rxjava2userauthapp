package fb.fandroid.adv.rxjava2userauthapp;

import fb.fandroid.adv.rxjava2userauthapp.model.Album;
import fb.fandroid.adv.rxjava2userauthapp.model.Albums;
import fb.fandroid.adv.rxjava2userauthapp.model.Song;
import fb.fandroid.adv.rxjava2userauthapp.model.Songs;
import fb.fandroid.adv.rxjava2userauthapp.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by marat.taychinov
 */

public interface AcademyApi {

    @POST("registration")
    Completable registration(@Body User.UserBean user);

    @GET("albums")
    Single<Albums> getAlbums();

    @GET("albums/{id}")
    Call<Album> getAlbum(@Path("id") int id);

    @GET("songs")
    Call<Songs> getSongs();

    @GET("songs/{id}")
    Call<Song> getSong(@Path("id") int id);

    @GET("user")
    Single<User.UserBean> getUser(@Header("Authorization") String authHeader);
}
