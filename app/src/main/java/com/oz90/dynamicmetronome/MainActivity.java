package com.oz90.dynamicmetronome;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.oz90.dynamicmetronome.entities.Section;
import com.oz90.dynamicmetronome.main.MainSectionPagerAdapter;
import com.oz90.dynamicmetronome.metronome.ui.MetronomeFragment;
import com.oz90.dynamicmetronome.presets.ui.PresetsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupAdapter();
    }

    private void setupAdapter() {
        toolbar.setTitle(R.string.app_name);

        fragments = new Fragment[]{new MetronomeFragment(), new PresetsFragment()};
        String[] titles = new String[]{getString(R.string.main_header_metronome), getString(R.string.main_header_presets)};
        MainSectionPagerAdapter adapter = new MainSectionPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    fab.show();
                }
                else {
                    fab.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            //TODO
            //Mostrar un modal con la información del dev.
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.fab)
    public void createSection() {
        MetronomeFragment metronomeFragment = (MetronomeFragment) fragments[0];
        metronomeFragment.createNewSection();
        PresetsFragment presetsFragment = (PresetsFragment) fragments[1];
        presetsFragment.refreshSections();
    }

    @Override
    public void refreshPresets() {
        PresetsFragment presetsFragment = (PresetsFragment) fragments[1];
        presetsFragment.refreshSections();
        presetsFragment.refreshSections();
    }

    @Override
    public void getSelectedSection(Section section) {
        MetronomeFragment metronomeFragment = (MetronomeFragment) fragments[0];
        metronomeFragment.setSection(section);
        viewPager.setCurrentItem(0);
    }

    @Override
    public int getPlayingSection() {
        MetronomeFragment metronomeFragment = (MetronomeFragment) fragments[0];
        return metronomeFragment.getPlayingSectionId();
    }

    @Override
    public void getMainMetronome() {
        MetronomeFragment metronomeFragment = (MetronomeFragment) fragments[0];
        metronomeFragment.getMainMetronome();
    }
}
