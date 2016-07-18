package com.oz90.dynamicmetronome.presets;

import android.content.res.Resources;

import com.oz90.dynamicmetronome.R;
import com.oz90.dynamicmetronome.entities.Section;
import com.oz90.dynamicmetronome.libs.base.EventBus;
import com.oz90.dynamicmetronome.presets.events.PresetsEvent;
import com.raizlabs.android.dbflow.list.FlowCursorList;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Orlando on 16/07/2016.
 */
public class PresetsRepositoryImpl implements PresetsRepository {
    private EventBus eventBus;

    public PresetsRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getPresets() {
        try {
            FlowCursorList<Section> sections = new FlowCursorList<Section>(false, Section.class);
            post(PresetsEvent.READ_EVENT, sections.getAll());
        } catch (Exception e) {
            post(PresetsEvent.READ_EVENT, e.getLocalizedMessage());
        }
    }

    @Override
    public void deletePreset(Section section) {
        try {
            if (section.getSectionId() != 1) {
                section.delete();
                post(PresetsEvent.DELETE_EVENT, Arrays.asList(section));
            } else {
                post(PresetsEvent.DELETE_EVENT, "Can't delete the default preset.");
            }
        } catch (Exception e) {
            post(PresetsEvent.DELETE_EVENT, e.getLocalizedMessage());
        }
    }

    private void post(int type, List<Section> sectionList, String error) {
        PresetsEvent event = new PresetsEvent();
        event.setType(type);
        event.setSectionList(sectionList);
        event.setError(error);
        eventBus.post(event);
    }

    private void post(int type, List<Section> sectionList) {
        post(type, sectionList, "");
    }

    private void post(int type, String error) {
        post(type, null, error);
    }
}
