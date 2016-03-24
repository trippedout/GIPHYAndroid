package net.trippedout.giphyandroid;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.trippedout.giphyandroidlib.GIPHY;
import net.trippedout.giphyandroidlib.Responses;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText mEditText;

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private GifAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViews();
    }

    private void setupViews() {
        mEditText = (EditText) findViewById(R.id.edit_text);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0 && s.charAt(s.length()-1) == ' ') {
                    String text = s.subSequence(0, s.length() - 1).toString();
                    mAdapter.addWord(text);

                    mEditText.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GifAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new ScaleInAnimator(new OvershootInterpolator(1.4f)));

        Button clearButton = (Button) findViewById(R.id.clear_btn);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.clearAll();
            }
        });
    }

    public class GifAdapter extends RecyclerView.Adapter<GifAdapter.ViewHolder> {

        private ArrayList<Pair<String, String>> mWords;

        public GifAdapter() {
            mWords = new ArrayList<>();
        }

        public void addWord(final String text) {
            GIPHY.get().translate(text).enqueue(new Callback<Responses.Translate>() {
                @Override
                public void onResponse(Call<Responses.Translate> call, Response<Responses.Translate> response) {
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
        }

        public void clearAll() {
            mWords.clear();
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setText(mWords.get(position).first);
            holder.setImage(mWords.get(position).second);
        }

        @Override
        public int getItemCount() {
            return mWords.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public ImageView gifView;
            public GifDrawable drawable;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text_view);
                gifView = (ImageView) itemView.findViewById(R.id.gif_view);
            }

            public void setText(String text) {
                textView.setText(text);
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void setImage(final String url_string) {
                gifView.setImageDrawable(null);

                if(drawable != null) {
                    drawable.recycle();
                    drawable = null;
                }

                new AsyncTask<Void, Void, GifDrawable>() {
                    @Override
                    protected GifDrawable doInBackground(Void... params) {
                        try {
                            Log.d(TAG, "loading image: " + url_string);
                            URL url = new URL(url_string);
                            URLConnection urlConnection = url.openConnection();
                            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                            return new GifDrawable(in);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(GifDrawable gifDrawable) {
                        drawable = gifDrawable;
                        gifView.setImageDrawable(drawable);
                    }
                }.execute();
            }
        }
    }
}
