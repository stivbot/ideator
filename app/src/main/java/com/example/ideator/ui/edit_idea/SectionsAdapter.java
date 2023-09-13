package com.example.ideator.ui.edit_idea;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ideator.databinding.FragmentItemBinding;
import com.example.ideator.model.section.Section;

import java.util.ArrayList;
import java.util.List;

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.ViewHolder> {

    private List<Section> sections = new ArrayList<Section>();
    private OnItemClickListener listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Section section = sections.get(position);
        holder.itemName.setText(section.getTitle());
        holder.itemDescription.setText(section.getDescription());
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
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
                        listener.onItemClick(sections.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Section section);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}