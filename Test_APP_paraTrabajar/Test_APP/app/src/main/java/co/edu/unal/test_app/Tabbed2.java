package co.edu.unal.test_app;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import co.edu.unal.test_app.fragments.buscar_evento;
import co.edu.unal.test_app.fragments.buscar_grupo;
import co.edu.unal.test_app.ui.main.SPA2;

public class Tabbed2 extends AppCompatActivity implements buscar_evento.OnFragmentInteractionListener, buscar_grupo.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed2);
        SPA2 sectionsPagerAdapter = new SPA2(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}