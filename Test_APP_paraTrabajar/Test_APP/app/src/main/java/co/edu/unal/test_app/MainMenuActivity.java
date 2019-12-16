package co.edu.unal.test_app;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import co.edu.unal.test_app.R;
import co.edu.unal.test_app.fragments.Menu_de_Usuario;
import co.edu.unal.test_app.fragments.MostrarEventos;
import co.edu.unal.test_app.fragments.MostrarGrupos;
import co.edu.unal.test_app.fragments.frangment_buscador;
import co.edu.unal.test_app.ui.main.SectionsPagerAdapter;

public class MainMenuActivity extends AppCompatActivity implements Menu_de_Usuario.OnFragmentInteractionListener, MostrarEventos.OnFragmentInteractionListener, MostrarGrupos.OnFragmentInteractionListener, frangment_buscador.OnFragmentInteractionListener {
public static String userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

         userEmail = getIntent().getStringExtra("Email");
        System.out.println("estoy en el main"+ userEmail);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}