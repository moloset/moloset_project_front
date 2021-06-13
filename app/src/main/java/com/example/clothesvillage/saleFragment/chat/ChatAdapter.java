package com.example.clothesvillage.saleFragment.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothesvillage.R;
import com.example.clothesvillage.model.ChatItem;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatItem> items = new ArrayList<ChatItem>();
    private Context context;

    public ChatAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == ChatType.LEFT_MESSAGE) {
            view = inflater.inflate(R.layout.chat_left_item, parent, false);
            return new LeftViewHolder(view);
        } else if (viewType == ChatType.RIGHT_MESSAGE) {
            view = inflater.inflate(R.layout.chat_right_item, parent, false);
            return new RightViewHolder(view);
        }
        throw new IllegalArgumentException("invalid viewType");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RightViewHolder) {
            ChatItem item = items.get(position);
            ((RightViewHolder) viewHolder).setItem(item);
        } else if (viewHolder instanceof LeftViewHolder) {
            ChatItem item = items.get(position);
            ((LeftViewHolder) viewHolder).setItem(item);
        }
    }

    public int getItemCount() {
        return items.size();
    }

    public void addItem(ChatItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void updateItems(List<ChatItem> items) {
        if (this.items == null) {
            items = new ArrayList<>();
        }
        this.items.clear();
        this.items.addAll(items);

        notifyDataSetChanged();
    }


    public ChatItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }


    public static class LeftViewHolder extends RecyclerView.ViewHolder {

        TextView contentText;

        public LeftViewHolder(View itemView) {
            super(itemView);

            contentText = itemView.findViewById(R.id.msg_text);

        }

        public void setItem(ChatItem item) {

            contentText.setText(item.getContent());

        }
    }

    public static class RightViewHolder extends RecyclerView.ViewHolder {
        TextView contentText;

        public RightViewHolder(View itemView) {
            super(itemView);

            contentText = itemView.findViewById(R.id.msg_text);
        }

        public void setItem(ChatItem item) {
            contentText.setText(item.getContent());

        }
    }

}
