package com.oz90.dynamicmetronome.presets;

import android.content.res.Resources;

import com.oz90.dynamicmetronome.R;
import com.oz90.dynamicmetronome.entities.Section;
import com.oz90.dynamicmetronome.entities.Section_Table;
import com.oz90.dynamicmetronome.entities.Track;
import com.oz90.dynamicmetronome.libs.base.EventBus;
import com.oz90.dynamicmetronome.presets.events.PresetsEvent;
import com.raizlabs.android.dbflow.list.FlowCursorList;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.Arrays;
import java.util.Collections;
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
            List<Section> sections = getSectionsAllList();
            post(PresetsEvent.READ_EVENT, sections);
        } catch (Exception e) {
            post(PresetsEvent.READ_EVENT, e.getLocalizedMessage());
        }
    }

    @Override
    public void deletePreset(Section section) {
        try {
            if (section.getSectionId() != 1) {
                section.delete();
                post(PresetsEvent.DELETE_EVENT, Collections.singletonList(section));
            } else {
                post(PresetsEvent.DELETE_EVENT, "Can't delete the default preset.");
            }
        } catch (Exception e) {
            post(PresetsEvent.DELETE_EVENT, e.getLocalizedMessage());
        }
    }

    @Override
    public void undoDelete(Section section) {
        try {
            section.save();
            List<Section> sections = getSectionsAllList();
            post(PresetsEvent.UNDO_EVENT, sections);
        } catch (Exception e) {
            post(PresetsEvent.UNDO_EVENT, e.getLocalizedMessage());
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

    public List<Section> getSectionsAllList() {
        //GET ALL THE SECTIONS, EXCEPT FOR THE DEFAULT ONE.
        List<Section> sections = new FlowCursorList<Section>(false, new Select().from(Section.class)
                                                                                    .where(Condition.column(Section_Table.trackId_trackId.getNameAlias()).is(Track.ID_DEFAULT)
                                                                                            ,Condition.column(Section_Table.sectionId.getNameAlias()).isNot(Section.ID_DEFAULT))
                                                                                    .orderBy(Section_Table.name.getNameAlias(), true)).getAll();
        //GET THE DEFAULT SECTION AND ADD IT AT THE TOP OF THE SECTIONS LIST.
        Section defaultSection = new FlowCursorList<Section>(false, Section.class,
                Condition.column(Section_Table.sectionId.getNameAlias()).is(Section.ID_DEFAULT)).getItem(0);
        sections.add(0, defaultSection);
        return sections;
    }
}
