package co.edu.unal.tabs_hubert.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import co.edu.unal.tabs_hubert.R;
import co.edu.unal.tabs_hubert.fragments.Menu_de_Usuario;
import co.edu.unal.tabs_hubert.fragments.MostrarEventos;
import co.edu.unal.tabs_hubert.fragments.MostrarGrupos;
import co.edu.unal.tabs_hubert.fragments.frangment_buscador;

public class MainActivity extends AppCompatActivity implements Menu_de_Usuario.OnFragmentInteractionListener, MostrarEventos.OnFragmentInteractionListener, MostrarGrupos.OnFragmentInteractionListener, frangment_buscador.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}