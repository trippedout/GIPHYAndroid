package net.trippedout.giphyandroidlib;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Main api class. Uses {@link Retrofit} for all API interfacing.
 */
public class GIPHY {

    private static final String DEFAULT_KEY = "dc6zaTOxFJmzC";

    private static GIPHYService mService;
    private static GIPHY __instance;

    private final String mApiKey;

    private GIPHY(String apiKey) {
        mApiKey = apiKey;
    }

    public static GIPHY get() {
        return get(DEFAULT_KEY);
    }
    
    public static GIPHY get(String apiKey) {
        if (mService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.giphy.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            
            mService = retrofit.create(GIPHYService.class);
        }
        
        if (__instance == null) {
            __instance = new GIPHY(apiKey);
        }
        
        return __instance;
    }
    
    public Call<Responses.Translate> translate(String input) {
        return mService.translate(mApiKey, input);
    }
}
