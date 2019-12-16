package co.edu.unal.tabs_hubert;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import co.edu.unal.tabs_hubert.fragments.buscar_evento;
import co.edu.unal.tabs_hubert.fragments.buscar_grupo;
import co.edu.unal.tabs_hubert.ui.main.SPA2;

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