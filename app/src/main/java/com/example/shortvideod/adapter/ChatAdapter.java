package com.example.shortvideod.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemChatMainUserBinding;
import com.example.shortvideod.databinding.ItemChatUserBinding;
import com.example.shortvideod.design.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MAIN_USER_TYPE = 1;
    Context context;
    Bitmap bitmap;
    Intent intent;
    boolean issingle = false;
    String strchar;
    String authorimage;
    List<Chat> chatlist = new ArrayList<>();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainUserChatViewHolder) {
            Log.d("TAG", "onBindViewHolder: admin");
            ((MainUserChatViewHolder) holder).setdata(position);

        } else {
            Log.d("TAG", "onBindViewHolder: user");
            ((UserChatViewHolder) holder).setdata(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return MAIN_USER_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_main_user, parent, false);
        return new MainUserChatViewHolder(view);
    }

    public void setAuthorimage(String authorimage) {
        this.authorimage = authorimage;
    }


    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public void addData(List<Chat> data) {
        Log.d("TAG", "addData: course" + data.size());
        Log.d("TAG", "addData: course" + data.size());
        for (int i = 0; i < data.size(); i++) {
            chatlist.add(data.get(i));
            notifyItemInserted(chatlist.size() - 1);
        }
    }
//

    public void addSingleMessage(Chat dataItem) {
        chatlist.add(dataItem);
        notifyItemInserted(chatlist.size() - 1);
        issingle = true;
    }

    public void setRecieverText(String str) {
        this.strchar = str;
    }


    public class MainUserChatViewHolder extends RecyclerView.ViewHolder {
        ItemChatMainUserBinding mainuserbinding;

        public MainUserChatViewHolder(@NonNull View itemView) {
            super(itemView);
            mainuserbinding = ItemChatMainUserBinding.bind(itemView);
        }

        public void setdata(int position) {
            Glide.with(context).load(R.drawable.splash_girl).circleCrop().placeholder(R.drawable.shape_round__dark_blue).into(mainuserbinding.mainUserImg);

            if (chatlist.get(position).getMsgtye().equalsIgnoreCase("image")) {
                mainuserbinding.imageLay.setVisibility(View.VISIBLE);
                mainuserbinding.TextLay.setVisibility(View.GONE);
                Glide.with(context).load(chatlist.get(position).getImg()).into(mainuserbinding.userImg);
            } else if (chatlist.get(position).getMsgtye().equalsIgnoreCase("text")) {
                mainuserbinding.imageLay.setVisibility(View.GONE);
                mainuserbinding.TextLay.setVisibility(View.VISIBLE);
                mainuserbinding.chatMsg.setText(chatlist.get(position).getData());
            }


            if (issingle) {
                mainuserbinding.time.setText("Now");
            }

        }

    }

    public class UserChatViewHolder extends RecyclerView.ViewHolder {
        ItemChatUserBinding userbinding;

        public UserChatViewHolder(@NonNull View itemView) {
            super(itemView);
            userbinding = ItemChatUserBinding.bind(itemView);
        }

        public void setdata(int position) {
            userbinding.chatMsg.setText(chatlist.get(position).getData());
            userbinding.imgUser.setText(strchar);
        }
    }

}
