package com.oz90.dynamicmetronome.presets.ui.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.oz90.dynamicmetronome.R;
import com.oz90.dynamicmetronome.entities.Section;
import com.oz90.dynamicmetronome.entities.Track;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Orlando on 16/07/2016.
 */
public class PresetsAdapter extends RecyclerView.Adapter<PresetsAdapter.ViewHolder> {
    private List<Section> sectionList;
    private OnItemClickListener clickListener;

    public PresetsAdapter(List<Section> sectionList, OnItemClickListener clickListener) {
        this.sectionList = sectionList;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_preset, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Section currentSection = sectionList.get(position);
        if (currentSection != null) {
            holder.textSectionName.setText(currentSection.getName());
            holder.textBeat.setText("Beat: " + Integer.toString(currentSection.getBeat()));
            holder.textBeatsPerBar.setText("Beats per bar: " + Integer.toString(currentSection.getBeatsPerBar()));
            holder.setOnClickListener(currentSection, clickListener);
            if (currentSection.getSectionId() == Section.ID_DEFAULT){
                holder.imgDeleteSection.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
        notifyDataSetChanged();
    }

    public void removeSection(Section section) {
        sectionList.remove(section);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.textSectionName)
        TextView textSectionName;
        @Bind(R.id.imgDeleteSection)
        ImageButton imgDeleteSection;
        @Bind(R.id.textBeat)
        TextView textBeat;
        @Bind(R.id.textBeatsPerBar)
        TextView textBeatsPerBar;
        @Bind(R.id.viewpager)
        CardView container;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }

        public void setOnClickListener(final Section currentSection, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(currentSection);
                }
            });

            imgDeleteSection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(currentSection);
                }
            });
        }
    }
}
