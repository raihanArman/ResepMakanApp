package id.co.myproject.resepmakanapp.Fragment.Makanan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.co.myproject.resepmakanapp.R;
import id.co.myproject.resepmakanapp.Util.Common;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResepFragment extends Fragment {

    TextView tv_resep;

    public ResepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resep, container, false);
        tv_resep = view.findViewById(R.id.tv_resep);
        tv_resep.setText(Common.common_makanan.getResep());
        return view;
    }

}
