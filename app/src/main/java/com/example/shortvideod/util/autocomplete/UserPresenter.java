package com.example.shortvideod.util.autocomplete;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.shortvideod.design.Democontents;
import com.example.shortvideod.design.MentionImg;
import com.example.shortvideod.util.SessionManager;
import com.otaliastudios.autocomplete.RecyclerViewPresenter;

public class UserPresenter extends RecyclerViewPresenter<MentionImg> {

    private static final String TAG = "UserPresenter";
    private final Context mContext;
    SessionManager sessionManager;
    private UserAdapter mAdapter;

    public UserPresenter(Context context) {
        super(context);
        mContext = context;
        sessionManager = new SessionManager(context);
    }

    @Override
    protected UserAdapter instantiateAdapter() {
        return mAdapter = new UserAdapter(mContext, this::dispatchClick);
    }

    @Override
    protected void onQuery(@Nullable CharSequence q) {
        Log.v(TAG, "Querying '" + q + "' for users autocomplete.");
        mAdapter.submitData(Democontents.getMentionSlim());
    }
}
