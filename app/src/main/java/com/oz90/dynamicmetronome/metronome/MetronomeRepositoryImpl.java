package com.oz90.dynamicmetronome.metronome;

import android.util.Log;

import com.oz90.dynamicmetronome.entities.Section;
import com.oz90.dynamicmetronome.entities.Section_Table;
import com.oz90.dynamicmetronome.entities.Track;
import com.oz90.dynamicmetronome.libs.base.EventBus;
import com.oz90.dynamicmetronome.metronome.events.MetronomeEvent;
import com.raizlabs.android.dbflow.list.FlowCursorList;
import com.raizlabs.android.dbflow.sql.language.Condition;


/**
 * Created by Orlando on 04/07/2016.
 */
public class MetronomeRepositoryImpl implements MetronomeRepository {
    private EventBus eventBus;

    public MetronomeRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getDefaultSection() {
        try {
            Section section = new FlowCursorList<Section>(false, Section.class,
                                    Condition.column(Section_Table.trackId_trackId.getNameAlias()).is(Track.ID_DEFAULT)).getItem(0);
            //Verifica si ya existe la sección default. Si no, la crea.
            if (section == null) {
                section = createDefaultSection();
            }
            post(MetronomeEvent.READ_EVENT, section);
        } catch (Exception e) {
            post(MetronomeEvent.READ_EVENT, e.getLocalizedMessage());
        }
    }

    @Override
    public void getSection(int sectionId) {
        try {
            Section section = new FlowCursorList<Section>(false, Section.class,
                    Condition.column(Section_Table.trackId_trackId.getNameAlias()).is(sectionId)).getItem(0);
            post(MetronomeEvent.READ_EVENT, section);
        } catch (Exception e) {
            post(MetronomeEvent.READ_EVENT, e.getLocalizedMessage());
        }
    }

    private Section createDefaultSection() {
        //Crea la track default.
        Track track = new Track(Track.ID_DEFAULT, Track.NAME_DEFAULT);
        track.save();
        //Crea la sección default.
        long miliseconds = 60000 / Section.BEAT_DEFAULT;
        Section section = new Section(Track.ID_DEFAULT,
                Track.ID_DEFAULT,
                Section.NAME_DEFAULT,
                Section.BEAT_DEFAULT,
                miliseconds,
                Section.BEATS_PER_BAR_DEFAULT,
                Section.REPETITION_DEFAULT);
        section.save();
        return section;
    }

    @Override
    public void updateSection(Section section) {
        try {
            section.save();
        } catch (Exception e) {
            post(MetronomeEvent.UPDATE_EVENT, e.getLocalizedMessage());
        }
    }

    @Override
    public void createNewSection(String name) {
        try {
            long miliseconds = 60000 / Section.BEAT_DEFAULT;
            Section newSection = new Section(name,
                    Section.BEAT_DEFAULT,
                    miliseconds,
                    Section.BEATS_PER_BAR_DEFAULT,
                    Section.REPETITION_DEFAULT);
            newSection.save();
            post(MetronomeEvent.CREATE_EVENT, newSection);
        } catch (Exception e) {
            post(MetronomeEvent.CREATE_EVENT, e.getLocalizedMessage());
        }
    }

    private void post(int type, Section section, String error) {
        MetronomeEvent event = new MetronomeEvent();
        event.setType(type);
        event.setSection(section);
        event.setError(error);
        eventBus.post(event);
    }

    private void post(int type, String error) {
        post(type, null, error);
    }

    private void post(int type, Section section) {
        post(type, section, "");
    }
}
