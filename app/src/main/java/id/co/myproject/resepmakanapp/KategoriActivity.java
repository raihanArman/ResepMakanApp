package id.co.myproject.resepmakanapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import id.co.myproject.resepmakanapp.Fragment.Kategori.KategoriMoreFragment;
import id.co.myproject.resepmakanapp.Fragment.Kategori.KetegoriFragment;

public class KategoriActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        frameLayout = findViewById(R.id.frame_kategori);
        int i = getIntent().getIntExtra("code", 0);
        if(i == 1)
            setFragment(new KetegoriFragment());
        else if(i == 2)
            setFragment(new KategoriMoreFragment());
    }

    private void setFragment(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putString("key", "0");
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        transaction.commit();
    }
}
