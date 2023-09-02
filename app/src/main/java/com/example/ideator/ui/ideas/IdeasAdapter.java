package com.example.ideator.ui.ideas;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.ideator.databinding.FragmentItemBinding;
import com.example.ideator.model.idea.Idea;
import com.example.ideator.model.idea.IdeaWithSections;

import java.util.ArrayList;
import java.util.List;

public class IdeasAdapter extends RecyclerView.Adapter<IdeasAdapter.ViewHolder> {

    private List<IdeaWithSections> ideas = new ArrayList<IdeaWithSections>();
    private OnItemClickListener listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        IdeaWithSections idea = ideas.get(position);
        holder.itemName.setText(idea.idea.getTitle());
        holder.itemDescription.setText(idea.idea.getDescription());
    }

    @Override
    public int getItemCount() {
        return ideas.size();
    }

    public void setIdeas(List<IdeaWithSections> ideas) {
        this.ideas = ideas;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView itemName;
        public final TextView itemDescription;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            itemName = binding.itemName;
            itemDescription = binding.itemDescription;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(ideas.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(IdeaWithSections idea);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}