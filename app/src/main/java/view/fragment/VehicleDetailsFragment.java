package view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import db.DatabaseHelper;
import info.example.abc.R;
import info.hoang8f.android.segmented.SegmentedGroup;
import model.Slot;
import util.SharedPref;


public class VehicleDetailsFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    SegmentedGroup carSeg;
    LinearLayout floor;
    Button f1, f2, f3, f4, enter;
    EditText number;
    DatabaseHelper db;
    String vehicleType = "", floorType = "";
    SharedPref sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.vehicle_details_fragment, container, false);
        ImageView back = (ImageView) root.findViewById(R.id.back);
        carSeg = (SegmentedGroup) root.findViewById(R.id.car_type);
        carSeg.setOnCheckedChangeListener(this);
        floor = (LinearLayout) root.findViewById(R.id.floor);
        number = (EditText) root.findViewById(R.id.vehicle_number);
        f1 = (Button) root.findViewById(R.id.floor1);
        f2 = (Button) root.findViewById(R.id.floor2);
        f3 = (Button) root.findViewById(R.id.floor3);
        f4 = (Button) root.findViewById(R.id.floor4);
        f1.setOnClickListener(this);
        f2.setOnClickListener(this);
        f3.setOnClickListener(this);
        f4.setOnClickListener(this);
        clearAll();
        sp = new SharedPref();
        sp.init(getActivity());
        enter = (Button) root.findViewById(R.id.enter);
        db = new DatabaseHelper(getActivity());
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Slot slot = new Slot();
                slot.setNumber(number.getText().toString().trim());
                slot.setEntry_timestamp(DateFormat.getDateTimeInstance().format(new Date()));
                slot.setFloor_type(floorType);
                slot.setVehicle_type(vehicleType);
                slot.setSlot("");
                int i = vehicleEntryDetails(number.getText().toString().trim(), vehicleType, floorType, slot);
                switch (i) {
                    case 0:
                        Toast.makeText(getActivity(),"Added Successfully",Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                        break;
                    case 1:
                        Toast.makeText(getActivity(),"Please Enter vehicle Number",Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(),"Please Select vehicle type",Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(),"Please Select floor",Toast.LENGTH_LONG).show();
                        break;
                   /*case 4:
                        Toast.makeText(getActivity(),"NO SPACE",Toast.LENGTH_LONG).show();
                        break; */
                    default:
                        break;
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onClick(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        switch (view.getId() /*to get clicked view id**/) {
            case R.id.floor1:
                floorType = "FLOOR1";
                break;
            case R.id.floor2:
                floorType = "FLOOR2";
                break;
            case R.id.floor3:
                floorType = "FLOOR3";
                break;
            case R.id.floor4:
                floorType = "FLOOR4";
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(radioGroup.getWindowToken(), 0);
        clearAll();
        switch (i) {

            case R.id.bike:
                if (sp.read("FLOOR1", 0) <= 0) {
                    if(sp.read("FLOOR2",0)!=0) {
                        f2.setEnabled(true);
                        f2.setAlpha(1.0f);
                    }
                    if(sp.read("FLOOR3",0)!=0) {
                        f3.setEnabled(true);
                        f3.setAlpha(1.0f);
                    }
                    if(sp.read("FLOOR4",0)!=0) {
                        f4.setEnabled(true);
                        f4.setAlpha(1.0f);
                    }
                    if(sp.read("FLOOR2",0)<=0 && sp.read("FLOOR3",0)<=0 && sp.read("FLOOR4",0)<=0) {
                        Toast.makeText(getActivity(),"NO SPACE",Toast.LENGTH_LONG).show();
                    }
                } else {
                    f1.setEnabled(true);
                    f1.setAlpha(1.0f);
                }
                vehicleType = "BIKE";
                break;
            case R.id.small_car:
                if (sp.read("FLOOR2", 0) <= 0) {
                    f1.setEnabled(false);
                    f1.setAlpha(0.01f);

                    if(sp.read("FLOOR3",0)!=0) {
                        f3.setEnabled(true);
                        f3.setAlpha(1.0f);
                    }
                    if(sp.read("FLOOR4",0)!=0) {
                        f4.setEnabled(true);
                        f4.setAlpha(1.0f);
                    }
                    if(sp.read("FLOOR3",0)<=0 && sp.read("FLOOR4",0)<=0) {
                        Toast.makeText(getActivity(),"NO SPACE",Toast.LENGTH_LONG).show();
                    }
                } else {
                    f2.setEnabled(true);
                    f2.setAlpha(1.0f);
                }
                vehicleType = "SC";

                break;
            case R.id.medium_car:
                if (sp.read("FLOOR3", 0) <= 0) {
                    f1.setEnabled(false);
                    f2.setEnabled(false);
                    f1.setAlpha(0.01f);
                    f2.setAlpha(0.01f);

                    if(sp.read("FLOOR4",0)!=0) {
                        f4.setEnabled(true);
                        f4.setAlpha(1.0f);
                    }else{
                        Toast.makeText(getActivity(),"NO SPACE",Toast.LENGTH_LONG).show();
                    }
                } else {
                    f3.setEnabled(true);
                    f3.setAlpha(1.0f);
                }
                vehicleType = "MC";

                break;
            case R.id.large_car:
                if (sp.read("FLOOR4", 0) <= 0) {
                    f1.setEnabled(false);
                    f2.setEnabled(false);
                    f3.setEnabled(false);
                    f1.setAlpha(0.01f);
                    f2.setAlpha(0.01f);
                    f3.setAlpha(0.01f);
                    Toast.makeText(getActivity(),"NO SPACE",Toast.LENGTH_LONG).show();
                } else {
                    f4.setEnabled(true);
                    f4.setAlpha(1.0f);
                }
                vehicleType = "LC";
                break;


            default:
                // Nothing to do
        }
    }

    public int vehicleEntryDetails(String vehicle, String vehicleType, String floorType, Slot slot) {
        if (vehicle.toString().trim().isEmpty()) {
            return 1;
        }
        if (vehicleType.toString().trim().isEmpty()) {
            return 2;
        }
        if (floorType.toString().trim().isEmpty()) {
            return 3;
        }


        db.insertSlot(slot);

        int currentSlots = sp.read(slot.getFloor_type().toString().trim(), 0);
        sp.write(slot.getFloor_type().toString().trim(), currentSlots - 1);

        return 0;
    }

    void clearAll() {
        f1.setEnabled(false);
        f2.setEnabled(false);
        f3.setEnabled(false);
        f4.setEnabled(false);
        f1.setAlpha(0.01f);
        f2.setAlpha(0.01f);
        f3.setAlpha(0.01f);
        f4.setAlpha(0.01f);
    }
}
