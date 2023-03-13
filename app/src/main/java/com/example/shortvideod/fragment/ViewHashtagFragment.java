package com.example.shortvideod.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.shortvideod.R;
import com.example.shortvideod.adapter.ViewHashtagAdapter;
import com.example.shortvideod.databinding.FragmentViewHashtagBinding;
import com.example.shortvideod.design.Democontents;
import com.google.android.material.appbar.AppBarLayout;

public class ViewHashtagFragment extends Fragment {
    FragmentViewHashtagBinding binding;
    ViewHashtagAdapter viewHashtagAdapter;
    NavController controller;

    public ViewHashtagFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_hashtag, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.shootBelow.setVisibility(View.GONE);
        controller = Navigation.findNavController(requireActivity(), R.id.host);

        binding.hashtagName.setText(getArguments().getString("Const.HASHTAG"));

        viewHashtagAdapter = new ViewHashtagAdapter();

        binding.rvViewHashtag.setAdapter(viewHashtagAdapter);
        viewHashtagAdapter.addData(Democontents.getHashtagGroup());

        viewHashtagAdapter.setOnHashtagVideoClick(() -> {
            controller.navigate(R.id.action_viewhashtag_to_Homefragment);
        });


        if (binding.scrollView != null) {

            binding.scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {


                if (scrollY > oldScrollY) {
                    Animatoo.animateCard(requireActivity());

                }

                if (scrollY < oldScrollY) {
                    Animatoo.animateCard(requireActivity());
                }

                if (scrollY == 0) {
                    Log.i("TAG", "TOP SCROLL");
                }

                if (scrollY == (v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight())) {
                    Log.i("TAG", "BOTTOM SCROLL");
                }
            });
        }

        binding.appBar.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener)
                (appBarLayout, verticalOffset) -> {
                    if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                        // If collapsed, then do this

                        binding.shootBelow.setVisibility(View.VISIBLE);
                        binding.iconSmall.setVisibility(View.VISIBLE);
                        binding.bgImg.setVisibility(View.VISIBLE);
                        binding.txtToolbar.setVisibility(View.VISIBLE);
                        Animatoo.animateCard(requireActivity());
                        binding.toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.app_color));

                    } else if (verticalOffset == 0) {
                        // If expanded, then do this
                        binding.shootBelow.setVisibility(View.GONE);
                        binding.iconSmall.setVisibility(View.GONE);
                        binding.bgImg.setVisibility(View.GONE);
                        binding.txtToolbar.setVisibility(View.GONE);
                        binding.toolbar.setBackground(null);
                        Animatoo.animateCard(requireActivity());

                    } else {
                        // Somewhere in between
                        // Do according to your requirement
                    }
                });

        binding.back.setOnClickListener(v ->
                controller.popBackStack());

    }


}

