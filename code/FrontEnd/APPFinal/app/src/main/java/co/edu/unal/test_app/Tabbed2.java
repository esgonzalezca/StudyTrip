package co.edu.unal.test_app;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import co.edu.unal.test_app.fragments.buscar_evento;
import co.edu.unal.test_app.fragments.buscar_grupo;
import co.edu.unal.test_app.ui.main.SPA2;

public class Tabbed2 extends AppCompatActivity implements buscar_evento.OnFragmentInteractionListener, buscar_grupo.OnFragmentInteractionListener{

    public static String clave;
    public static String id;
    public static String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed2);
        SPA2 sectionsPagerAdapter = new SPA2(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        final FloatingActionButton backBtn= findViewById(R.id.BtnBuscadorTabAtras);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clave = getIntent().getStringExtra("clave");
        id = getIntent().getStringExtra("Id_Interest");
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onBackPressed() {}
}