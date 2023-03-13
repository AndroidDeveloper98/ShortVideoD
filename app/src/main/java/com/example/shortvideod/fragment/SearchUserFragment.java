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
import com.example.shortvideod.databinding.FragmentSearchUserBinding;
import com.example.shortvideod.design.Democontents;
import com.example.shortvideod.design.UserList;

import java.util.ArrayList;
import java.util.List;

public class SearchUserFragment extends Fragment {
    FragmentSearchUserBinding binding;
    NavController navController;
    List<UserList> user = new ArrayList<>();


    public SearchUserFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_user, container, false);
        initData();
        return binding.getRoot();
    }

    private void initData() {
        navController = Navigation.findNavController(requireActivity(), R.id.host);
        user = Democontents.getUserList();

    }

}
