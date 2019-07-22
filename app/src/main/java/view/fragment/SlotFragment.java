package view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import info.example.abc.R;
import util.SharedPref;
import view.adapter.CustomGrid;

public class SlotFragment extends Fragment {

    private Spinner spinner1;
    SharedPref sp;
    GridView grid1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.slot_fragment, container, false);
         grid1 = (GridView) root.findViewById(R.id.grid1);

        sp=new SharedPref();
        sp.init(getActivity());

        spinner1 = (Spinner) root.findViewById(R.id.spinner);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            String firstItem = String.valueOf(spinner1.getSelectedItem());

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                if (firstItem.equals(String.valueOf(spinner1.getSelectedItem()))) {
                    // ToDo when first item is selected
                } else {
                    String floorType = "";
                    floorType = parent.getItemAtPosition(i).toString().trim();
                    int num=0;
                    switch (floorType) {
                        case "FLOOR1":
                            num=sp.read("FLOOR1",0);
                            break;
                        case "FLOOR2":
                            num=sp.read("FLOOR2",0);
                            break;
                        case "FLOOR3":
                            num=sp.read("FLOOR3",0);
                            break;
                        case "FLOOR4":
                            num=sp.read("FLOOR4",0);
                            break;
                        default:
                            break;
                    }
                    CustomGrid adapter = new CustomGrid(getActivity(),num);
                    grid1.setAdapter(adapter);
                    grid1.setVerticalScrollBarEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


  /*      LinearLayout linear=(LinearLayout)root.findViewById(R.id.linearlayout);
        for (int i = 1; i <= 20; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150,150);
                   *//* LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);*//*

         *//* LayoutInflater inf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inf.inflate(R.layout.field, null);
            linear.addView(rowView, linear.getChildCount() - 1);
*//*
            Button btn = new Button(getActivity());
            btn.setId(i);
            btn.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.round_shape));
            final int id_ = btn.getId();
            btn.setText(""+ id_);

               LinearLayout layout=new LinearLayout(getActivity());
               layout.addView(btn, params);
               linear.addView(layout,new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));




            //btn.setBackgroundColor(Color.rgb(70, 80, 90));


        }*/
        return root;
    }

}
