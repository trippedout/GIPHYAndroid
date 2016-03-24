package net.trippedout.giphyandroidlib;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Main Retrofit service interface
 */
public interface GIPHYService {
    @GET("v1/gifs/translate")
    Call<Responses.Translate> translate(@Query("api_key") String apiKey, @Query("s") String input);

}
