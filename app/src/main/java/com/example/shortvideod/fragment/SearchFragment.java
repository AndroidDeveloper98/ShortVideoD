package com.example.shortvideod.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.BannerAdapter;
import com.example.shortvideod.adapter.DotAdapter;
import com.example.shortvideod.adapter.HashtagAllAdapter;
import com.example.shortvideod.databinding.FragmentSearchBinding;
import com.google.android.exoplayer2.Player;

public class SearchFragment extends Fragment implements Player.EventListener {

    FragmentSearchBinding binding;
    HashtagAllAdapter hashtagAllAdapter;
    BannerAdapter bannerAdapter;
    NavController navController;
    DotAdapter dotAdapter;
    private int pos = 0;

    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        navController = Navigation.findNavController(requireActivity(), R.id.host);

        settop();

        bannerAdapter = new BannerAdapter();
        new PagerSnapHelper().attachToRecyclerView(binding.rvBanner);
        dotAdapter = new DotAdapter(4);
        binding.rrcyclerDots.setAdapter(dotAdapter);

        hashtagAllAdapter = new HashtagAllAdapter();

        binding.rvBanner.setAdapter(bannerAdapter);
        binding.rvAllHashtag.setAdapter(hashtagAllAdapter);


        hashtagAllAdapter.setOnViewAllClick(new HashtagAllAdapter.OnViewAllClick() {
            @Override
            public void onClick(String hashtag) {
                Bundle b = new Bundle();
                b.putString("Const.HASHTAG", hashtag);
                navController.navigate(R.id.action_searchFragment_to_viewHashtag, b);
            }

            @Override
            public void onVideoClick() {
                navController.navigate(R.id.action_searchFragment_to_homeFragment);
            }
        });

        binding.seachLay.setOnClickListener(v ->
                navController.navigate(R.id.action_searchFragmemt_to_searchUserHashFragment));

        binding.rvBanner.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    pos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    dotAdapter.changePos(pos);

                }
            }
        });


    }

    public void settop() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int pos = 0;
            boolean flag = true;

            @Override
            public void run() {
                if (pos == bannerAdapter.getItemCount() - 1) {
                    flag = false;
                } else if (pos == 0) {
                    flag = true;
                }
                if (flag) {
                    pos++;
                } else {
                    pos--;
                }
                binding.rvBanner.smoothScrollToPosition(pos);
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);

    }


}
