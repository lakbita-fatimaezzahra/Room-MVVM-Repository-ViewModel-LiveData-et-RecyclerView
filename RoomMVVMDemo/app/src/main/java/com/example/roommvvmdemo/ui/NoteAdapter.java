package com.example.roommvvmdemo.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roommvvmdemo.R;
import com.example.roommvvmdemo.data.local.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> dataList = new ArrayList<>();
    private OnItemClickListener tapListener;
    private OnItemLongClickListener holdListener;

    public interface OnItemClickListener {
        void onTap(Note item);
    }

    public interface OnItemLongClickListener {
        void onHold(Note item);
    }

    public void updateData(List<Note> newList) {
        this.dataList = newList;
        notifyDataSetChanged();
    }

    public void setTapListener(OnItemClickListener listener) {
        this.tapListener = listener;
    }

    public void setHoldListener(OnItemLongClickListener listener) {
        this.holdListener = listener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note item = dataList.get(position);
        holder.titleView.setText(item.getNoteTitle());
        holder.descView.setText(item.getNoteContent());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView descView;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.tvTitle);
            descView = itemView.findViewById(R.id.tvDescription);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (tapListener != null && pos != RecyclerView.NO_POSITION) {
                    tapListener.onTap(dataList.get(pos));
                }
            });

            itemView.setOnLongClickListener(v -> {
                int pos = getAdapterPosition();
                if (holdListener != null && pos != RecyclerView.NO_POSITION) {
                    holdListener.onHold(dataList.get(pos));
                    return true;
                }
                return false;
            });
        }
    }
}