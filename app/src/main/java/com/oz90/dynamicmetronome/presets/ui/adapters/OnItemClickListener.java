package com.oz90.dynamicmetronome.presets.ui.adapters;

import com.oz90.dynamicmetronome.entities.Section;

/**
 * Created by Orlando on 16/07/2016.
 */
public interface OnItemClickListener {
    void onItemClick(Section section);
    void onDeleteClick(Section section);
}
