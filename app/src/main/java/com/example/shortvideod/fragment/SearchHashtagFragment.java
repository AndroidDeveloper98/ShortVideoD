package com.example.shortvideod.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.FragmentSearchHashtagBinding;
import com.example.shortvideod.design.Democontents;
import com.example.shortvideod.design.Hashtag;

import java.util.ArrayList;
import java.util.List;

public class SearchHashtagFragment extends Fragment {
    FragmentSearchHashtagBinding binding;
    NavController navController;

    List<Hashtag> hash = new ArrayList<>();

    public SearchHashtagFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_hashtag, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        navController = Navigation.findNavController(requireActivity(), R.id.host);
        hash = Democontents.getHashtag();


    }



}
