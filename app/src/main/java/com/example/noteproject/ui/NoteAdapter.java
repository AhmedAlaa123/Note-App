package com.example.noteproject.ui;

import android.provider.ContactsContract;

import com.example.noteproject.databse.Note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteproject.R;


import java.util.List;
//ListAdapter class is class used for adapting recycler view and animate for adding and deleting item
public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder>

{
    private ItemClickListener listener;

    protected NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return (oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getPariorty() == newItem.getPariorty());
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_data, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = getItem(position);
        holder.textView_pariorty.setText(String.valueOf(note.getPariorty()));
        holder.textView_Title.setText(note.getTitle());
        holder.textView_description.setText(note.getDescription());
    }


    public Note getNote(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        public TextView textView_pariorty;
        public TextView textView_Title;
        public TextView textView_description;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textView_pariorty = itemView.findViewById(R.id.text_view_pariorty);
            textView_Title = itemView.findViewById(R.id.text_view_title);
            textView_description = itemView.findViewById(R.id.text_view_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface ItemClickListener {
        void onItemClick(Note note);

    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }
}
