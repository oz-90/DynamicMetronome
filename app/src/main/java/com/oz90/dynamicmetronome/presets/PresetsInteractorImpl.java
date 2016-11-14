package com.oz90.dynamicmetronome.presets;

import com.oz90.dynamicmetronome.entities.Section;

/**
 * Created by Orlando on 16/07/2016.
 */
public class PresetsInteractorImpl implements PresetsInteractor {
    PresetsRepository repository;

    public PresetsInteractorImpl(PresetsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeGetPresets() {
        repository.getPresets();
    }

    @Override
    public void executeDeletePreset(Section section) {
        repository.deletePreset(section);
    }

    @Override
    public void executeUndoDelete(Section section) {
        repository.undoDelete(section);
    }
}
