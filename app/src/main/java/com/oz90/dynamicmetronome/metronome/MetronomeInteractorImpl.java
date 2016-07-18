package com.oz90.dynamicmetronome.metronome;

import com.oz90.dynamicmetronome.entities.Section;

/**
 * Created by Orlando on 04/07/2016.
 */
public class MetronomeInteractorImpl implements MetronomeInteractor {
    private MetronomeRepository repository;

    public MetronomeInteractorImpl(MetronomeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeGet() {
        repository.getDefaultSection();
    }

    @Override
    public void executeGetSection(int sectionId) {
        repository.getSection(sectionId);
    }

    @Override
    public void executeUpdate(Section section) {
        repository.updateSection(section);
    }

    @Override
    public void executeCreate(String name) {
        repository.createNewSection(name);
    }
}
