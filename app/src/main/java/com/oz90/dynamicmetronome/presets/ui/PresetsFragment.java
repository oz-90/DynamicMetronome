package com.oz90.dynamicmetronome.presets.ui;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oz90.dynamicmetronome.DynamicMetronomeApp;
import com.oz90.dynamicmetronome.MainActivity;
import com.oz90.dynamicmetronome.R;
import com.oz90.dynamicmetronome.entities.Section;
import com.oz90.dynamicmetronome.presets.PresetsPresenter;
import com.oz90.dynamicmetronome.presets.di.PresetsComponent;
import com.oz90.dynamicmetronome.presets.ui.adapters.OnItemClickListener;
import com.oz90.dynamicmetronome.presets.ui.adapters.PresetsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PresetsFragment extends Fragment implements PresetsView, OnItemClickListener {
    @Bind(R.id.recyclerPresets)
    RecyclerView recyclerPresets;
    @Bind(R.id.viewpager)
    CardView container;

    private MainActivity mainActivity;

    @Inject
    PresetsPresenter presenter;
    @Inject
    PresetsAdapter adapter;

    public PresetsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.presets_fragment, container, false);
        ButterKnife.bind(this, view);
        setupInjection();
        setupRecyclerView();
        presenter.subscribeEventbus();
        presenter.getPresets();
        mainActivity = (MainActivity) getActivity();
        return view;
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerPresets.setLayoutManager(layoutManager);
        recyclerPresets.setAdapter(adapter);
    }

    private void setupInjection() {
        DynamicMetronomeApp app = (DynamicMetronomeApp) getActivity().getApplication();
        PresetsComponent presetsComponent = app.getPresetsComponent(this, this, this);
        presetsComponent.inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setSections(List<Section> sectionList) {
        adapter.setSectionList(sectionList);
    }

    @Override
    public void sectionDeleted(Section section) {
        adapter.removeSection(section);
    }

    @Override
    public void refreshSections() {
        presenter.getPresets();
    }

    @Override
    public void onError(String error) {
        //Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Section section) {
        mainActivity.getSelectedSection(section);
    }

    @Override
    public void onDeleteClick(final Section section) {
        int playingSection = mainActivity.getPlayingSection();
        //Snackbar.make(container, String.format(getString(R.string.sections_delete_message), section.getName()), Snackbar.LENGTH_LONG).show();
        Snackbar.make(container, String.format(getString(R.string.sections_delete_message), section.getName()), Snackbar.LENGTH_LONG).setAction(R.string.sections_delete_undo_message, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.undoDelete(section);
            }
        }).show();
        if (section.getSectionId() == playingSection) {
            mainActivity.getMainMetronome();
        }
        presenter.deletePreset(section);
    }
}
