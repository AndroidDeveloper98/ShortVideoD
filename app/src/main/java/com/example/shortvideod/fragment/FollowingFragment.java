package com.example.shortvideod.fragment;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.SuggstedUserAdapter;
import com.example.shortvideod.databinding.FragmentFollowingBinding;
import com.example.shortvideod.design.Democontents;

public class FollowingFragment extends BaseFragment {

    FragmentFollowingBinding binding;
    SuggstedUserAdapter suggstedUserAdapter;
    TransitionInflater inflater;
    NavController navController;


    public FollowingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_following, container, false);
        initView();
        return binding.getRoot();
    }


    private void initView() {
        navController = Navigation.findNavController(requireActivity(), R.id.host);

        suggstedUserAdapter = new SuggstedUserAdapter();
        binding.rvSuggestedUser.setAdapter(suggstedUserAdapter);
        suggstedUserAdapter.addData(Democontents.getSuggestedUser());

        inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));

        binding.back.setOnClickListener(v ->
                navController.popBackStack());


        suggstedUserAdapter.setOnClickUser(suggestedUser -> {
            Bundle bundle = new Bundle();
            bundle.putString("Const.USERIMAGE", suggestedUser.getImage());
            bundle.putString("Const.USERNAMELIST", suggestedUser.getName());
//
            Log.d("TAG", "initView: image " + suggestedUser.getImage());
            Log.d("TAG", "initView: name " + suggestedUser.getName());

            navController.navigate(R.id.action_searchUserHashFragment_to_userProfileFragment, bundle);

        });
    }


}
