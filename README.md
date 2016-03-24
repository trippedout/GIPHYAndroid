# GIPHYAndroid

Simple Retrofit wrapper for the GIHPY API to make things a bit easier.

Sample project uses the GIPHY translate API 

![rad right?](https://raw.githubusercontent.com/trippedout/GIPHYAndroid/master/assets/in-action.gif)

If cloning repo:

    compile project(':giphyandroidlib')
  
    ...
    // so far only simple translate api is implemented, adding more soon
    // get() will use default GIPHY api key, get(String) will allow to pass your production one
    GIPHY.get().translate(text).enqueue(new Callback<Responses.Translate>() {
        @Override
        public void onResponse(Call<Responses.Translate> call, Response<Responses.Translate> response) {
            //add to dataset and notify 
            mWords.add(new Pair<>(text, response.body().data.images.fixed_height_downsampled.url));
            notifyItemInserted(mWords.size() - 1);
    
            mLayoutManager.setSmoothScrollbarEnabled(true);
            mLayoutManager.scrollToPosition(mWords.size() - 1);
        }
    
        @Override
        public void onFailure(Call<Responses.Translate> call, Throwable t) {
            Log.e(TAG, call.toString());
        }
    });
