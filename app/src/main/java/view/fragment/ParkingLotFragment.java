package view.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import info.example.abc.R;


public class ParkingLotFragment extends Fragment {

    Button entry, exit, configure, slotstatus;
    private Spinner spinner1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.parking_lot_fragment, container, false);
        entry = (Button) rootView.findViewById(R.id.entry);
        exit = (Button) rootView.findViewById(R.id.exit);
        configure = (Button) rootView.findViewById(R.id.configure);
        slotstatus = (Button) rootView.findViewById(R.id.slotstatus);
        entry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadFragment(new VehicleDetailsFragment());
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { loadFragment(new ExitFragment());
            }
        });
        configure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { loadFragment(new ConfigFragment());
            }
        });


        slotstatus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadFragment(new SlotFragment());
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().toString());
        fragmentTransaction.commit(); // save the changes
    }
}
