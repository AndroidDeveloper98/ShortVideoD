package com.example.shortvideod.fragment;

import android.content.Intent;
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
import com.example.shortvideod.activity.ChatActivity;
import com.example.shortvideod.adapter.ChatUserListAdapter;
import com.example.shortvideod.databinding.FragmentChatBinding;
import com.example.shortvideod.design.Democontents;

public class ChatFragment extends Fragment {
    FragmentChatBinding binding;
    ChatUserListAdapter chatUserListAdapter;
    int postion;
    NavController navController;

    public ChatFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        chatUserListAdapter = new ChatUserListAdapter();
        binding.rvChatUser.setAdapter(chatUserListAdapter);
        chatUserListAdapter.addData(Democontents.getChatUsers());

        navController = Navigation.findNavController(requireActivity(), R.id.host);


        chatUserListAdapter.setOnItemClick(new ChatUserListAdapter.OnItemClick() {
            @Override
            public void onClick(int pos) {
                Intent i = new Intent(getActivity(), ChatActivity.class);
                i.putExtra("Const.CHATUSERNAME", Democontents.getChatUsers().get(pos).getUsername());
                i.putExtra("Const.CHATUSERIMAGE", Democontents.getChatUsers().get(pos).getImg());
                startActivity(i);
            }

            @Override
            public void onProfile(int pos) {
                Bundle b = new Bundle();
                b.putString("Const.USERIMAGE", Democontents.getChatUsers().get(pos).getImg());
                b.putString("Const.USERNAMELIST", Democontents.getChatUsers().get(pos).getUsername());
                navController.navigate(R.id.action_chatfragment_to_userProfileFragment, b);
            }

        });
    }

   
}
