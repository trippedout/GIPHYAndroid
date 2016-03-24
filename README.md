# GIPHYAndroid

Simple Retrofit wrapper for the GIHPY API to make things a bit easier.

##DISCLAIMER

The third party library this sample uses does a pretty good job at implementing gifs in android, which is notoriously terrible. But even still, because im not only displaying one at a time, I have to make sure to use the smaller compressed URL rather than 'original' from GIPHY. And sometimes the gifs don't start or play correctly, which seems to be internal issues with the library and the way its allocation its memory. Still nice for a proof of concept tho.

###Check it

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
