package com.oz90.dynamicmetronome.metronome.ui;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oz90.dynamicmetronome.DynamicMetronomeApp;
import com.oz90.dynamicmetronome.MainActivity;
import com.oz90.dynamicmetronome.R;
import com.oz90.dynamicmetronome.entities.Section;
import com.oz90.dynamicmetronome.libs.audio.Metronome;
import com.oz90.dynamicmetronome.metronome.MetronomePresenter;
import com.oz90.dynamicmetronome.metronome.di.MetronomeComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MetronomeFragment extends Fragment implements MetronomeView {
    @Bind(R.id.btnPlusOne)
    Button btnPlusOne;
    @Bind(R.id.btnMinusOne)
    Button btnMinusOne;
    @Bind(R.id.textBeat)
    EditText textBeat;
    @Bind(R.id.btnMinusFive)
    Button btnMinusFive;
    @Bind(R.id.btnPlusFive)
    Button btnPlusFive;
    @Bind(R.id.imgPlay)
    ImageButton imgPlay;
    @Bind(R.id.main_content)
    FrameLayout container;
    @Bind(R.id.textTick)
    TextView textTick;
    @Bind(R.id.spinnerBeatsPerBar)
    Spinner spinnerBeatsPerBar;
    @Bind(R.id.seekBarBeat)
    SeekBar seekBarBeat;
    @Bind(R.id.textSectionName)
    EditText textSectionName;
    @Bind(R.id.imgEditName)
    ImageButton imgEditName;


    Section section;
    private boolean playing = false, editing = false;
    private MainActivity mainActivity;
    Metronome metronome;

    @Inject
    MetronomePresenter presenter;

    public MetronomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.metronome_fragment, container, false);
        ButterKnife.bind(this, view);
        setupInjection();
        presenter.subscribeEventbus();
        getMainMetronome();
        if (this.section != null) {
            setupSeekBarBeat();
            setupSpinnerBeatsPerBar();
        }
        mainActivity = (MainActivity) getActivity();
        return view;
    }

    private void setupSeekBarBeat() {
        seekBarBeat.setMax(Section.BEAT_MAX_DEFAULT);
        seekBarBeat.setProgress(this.section.getBeat());
        seekBarBeat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress > 0 ? progress : 1;
                textBeat.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                section.setBeat(seekBar.getProgress());
                section.setMiliseconds(60000 / section.getBeat());
                updateSection();
                checkPlaying();
            }
        });
    }

    private void setupSpinnerBeatsPerBar() {
        int topBeatsPerBar = Section.TOP_BEATS_PER_BAR;
        List<Integer> beatsPerBarList = new ArrayList<>();
        for (int i = 0; i < topBeatsPerBar; i++) {
            beatsPerBarList.add(i + 1);
        }
        final ArrayAdapter<Integer> beatsPerBarAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, beatsPerBarList);
        spinnerBeatsPerBar.setAdapter(beatsPerBarAdapter);
        spinnerBeatsPerBar.setSelection(this.section.getBeatsPerBar() - 1);
        spinnerBeatsPerBar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedBeatsPerBar = (int) spinnerBeatsPerBar.getSelectedItem();
                section.setBeatsPerBar(selectedBeatsPerBar);
                updateSection();

                //checkPlaying();
                if (playing) {
                    metronome.setBeat(section.getBeatsPerBar());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void checkPlaying() {
        if (playing) {
            stopTimer();
            startTimer();
        }
    }

    private void setupInjection() {
        DynamicMetronomeApp app = (DynamicMetronomeApp) getActivity().getApplication();
        MetronomeComponent metronomeComponent = app.getMetronomeComponent(this, this);
        metronomeComponent.inject(this);
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
        //stopTimer();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        stopTimer();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setSection(Section section) {
        this.section = section;
        setupControllers();
    }

    @Override
    public void onError(String error) {
        //Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createNewSection() {
        createNewSection(Section.NEW_NAME_DEFAULT);
    }

    @Override
    public int getPlayingSectionId() {
        return this.section.getSectionId();
    }

    @Override
    public void getMainMetronome() {
        presenter.getMainMetronome();
    }

    @OnClick(R.id.btnMinusOne)
    public void onMinusOneClick() {
        setBeat(-1, true);
    }

    @OnClick(R.id.btnPlusOne)
    public void onPlusOneClick() {
        setBeat(1, true);
    }

    @OnClick(R.id.btnMinusFive)
    public void onMinusFiveClick() {
        setBeat(-5, true);
    }

    @OnClick(R.id.btnPlusFive)
    public void onPlusFiveClick() {
        setBeat(5, true);
    }

    @OnClick(R.id.imgPlay)
    public void onPlayClick() {
        int imgResource;
        setBeat(0, false);
        if (!playing && editing) {
            editName(false);
        }
        updateSection();

        if (playing) {
            imgResource = android.R.drawable.ic_media_play;
            textTick.setText("");
            stopTimer();
        } else {
            imgResource = android.R.drawable.ic_media_pause;
            startTimer();
        }
        textBeat.setEnabled(playing);
        playing = !playing;
        imgPlay.setImageResource(imgResource);
    }

    @OnClick(R.id.imgEditName)
    public void onEditNameClick() {
        editName(true);
    }

    private void startTimer() {
        metronome = new Metronome();
        metronome.setUpVariables(section.getBeat(), section.getBeatsPerBar(), 523.25, 880);
        metronome.playPublic();
    }

    private void stopTimer() {
        //timerHandler.removeCallbacks(timerRunnable);
        metronome.stop();
    }

    private void setBeat(int modifier, boolean update) {
        int beat;
        if (textBeat.getText().toString().trim().length() == 0) {
            beat = this.section.getBeat();
            //presenter.getMainMetronome();
        } else {
            beat = Integer.parseInt(textBeat.getText().toString());
            beat = Math.abs(beat);
        }
        beat += modifier;
        if (beat > Section.BEAT_MAX_DEFAULT) {
            beat = Section.BEAT_MAX_DEFAULT;
        }
        this.section.setBeat(beat);
        this.section.setMiliseconds(60000 / beat);
        if (update) {
            updateSection();
        }
    }

    private void editName(boolean update) {
        int imgResource;
        if (editing) {
            imgResource = android.R.drawable.ic_menu_edit;
            setName(update);
        } else {
            imgResource = android.R.drawable.ic_menu_save;
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(textSectionName, InputMethodManager.SHOW_IMPLICIT);
            textSectionName.selectAll();
        }
        editing = !editing;
        textSectionName.setEnabled(editing);
        imgEditName.setImageResource(imgResource);
    }

    private void setName(boolean update) {
        String newName = textSectionName.getText().toString().trim();
        if (newName.length() != 0) {
            if (!newName.equals(this.section.getName())) {
                this.section.setName(newName);
            }
        }
        if (update) {
            updateSection();
        }
    }

    private void updateSection() {
        presenter.updateSection(this.section);
        setupControllers();
        mainActivity.refreshPresets();
    }

    private void setupControllers() {
        textSectionName.setText(this.section.getName());
        textBeat.setText(Integer.toString(this.section.getBeat()));
        seekBarBeat.setProgress(this.section.getBeat());
        spinnerBeatsPerBar.setSelection(this.section.getBeatsPerBar() - 1);
        textSectionName.setEnabled(false);
        imgEditName.setImageResource(android.R.drawable.ic_menu_edit);
        editing = false;
    }

    private void createNewSection(String name) {
        presenter.createNewSection(name);
    }
}
