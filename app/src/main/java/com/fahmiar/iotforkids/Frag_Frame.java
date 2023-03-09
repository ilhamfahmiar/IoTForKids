package com.fahmiar.iotforkids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Frag_Frame extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageButton logout_btn;

    private Home homefrag;
    private MyProject projectfrag;
    private Community communityfrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_frame);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs_layout);
        logout_btn = findViewById(R.id.logout_btn);

        homefrag = new Home();
        projectfrag = new MyProject();
        communityfrag = new Community();

        tabLayout.setupWithViewPager(viewPager);
        viewPagerAdapter viewPagerAdapter = new viewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(homefrag, "Home");
        viewPagerAdapter.addFragment(projectfrag, "My project");
        viewPagerAdapter.addFragment(communityfrag, "Community");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.planet_btn);
        tabLayout.getTabAt(1).setIcon(R.drawable.roket_btn);
        tabLayout.getTabAt(2).setIcon(R.drawable.astro_btn);

        // set icon color pre-selected
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        logout_btn.setOnClickListener(v -> {
            //kembali ke activity login
            Intent intent1 = new Intent(Frag_Frame.this, MainActivity.class);
            startActivity(intent1);
            finish();
        });

    }

    private static class viewPagerAdapter extends FragmentPagerAdapter{

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmenttitle = new ArrayList<>();

        public viewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmenttitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmenttitle.get(position);
        }
    }
}