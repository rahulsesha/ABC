package view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import info.example.abc.R;
import util.SharedPref;


public class ConfigFragment extends Fragment {
    private Spinner spinner1, spinner2;
    SharedPref sp;
    String floor="",slots="";
    Button add;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.config_fragment, container, false);
        spinner1 = (Spinner) root.findViewById(R.id.spinner1);
        spinner2 = (Spinner) root.findViewById(R.id.spinner2);
        add = (Button) root.findViewById(R.id.add);

        sp = new SharedPref();
        sp.init(getActivity());

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 floor = String.valueOf(spinner1.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 slots = String.valueOf(spinner2.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sp.write(floor,sp.read(floor,0)+Integer.parseInt(slots));
                Toast.makeText(getActivity(),"Slots Added Succcesfully",Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();
            }
        });
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}

