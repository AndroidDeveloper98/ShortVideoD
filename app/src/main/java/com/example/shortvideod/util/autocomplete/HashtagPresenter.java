package com.example.shortvideod.util.autocomplete;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.shortvideod.design.Democontents;
import com.example.shortvideod.design.HashtagAuto;
import com.otaliastudios.autocomplete.RecyclerViewPresenter;


public class HashtagPresenter extends RecyclerViewPresenter<HashtagAuto> {

    private static final String TAG = "HashtagPresenter";
    private final Context mContext;
    private HashtagAdapter mAdapter;

    public HashtagPresenter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected HashtagAdapter instantiateAdapter() {
        return mAdapter = new HashtagAdapter(mContext, this::dispatchClick);
    }

    @Override
    protected void onQuery(@Nullable CharSequence q) {
        Log.v(TAG, "Querying '" + q + "' for hashtags autocomplete.");
        mAdapter.submitData(Democontents.getHashtagsAuto());
    }
}
