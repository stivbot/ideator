package com.example.ideator.ui.edit_idea;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ideator.databinding.FragmentSectionBinding;
import com.example.ideator.model.section.Section;

import java.util.ArrayList;
import java.util.List;

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.ViewHolder> {

    private List<Section> sections = new ArrayList<Section>();
    private OnSectionClickListener listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentSectionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Section section = sections.get(position);
        holder.sectionName.setText(section.getTitle());
        holder.sectionDescription.setText(section.getDescription());
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
        public final TextView sectionName;
        public final TextView sectionDescription;

        public ViewHolder(FragmentSectionBinding binding) {
            super(binding.getRoot());
            sectionName = binding.sectionName;
            sectionDescription = binding.sectionDescription;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onSectionClick(sections.get(position));
                    }
                }
            });
        }
    }

    public interface OnSectionClickListener {
        void onSectionClick(Section section);
    }

    public void setOnSectionClickListener(OnSectionClickListener listener) {
        this.listener = listener;
    }
}