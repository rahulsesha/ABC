package view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import db.DatabaseHelper;
import info.example.abc.R;
import model.Slot;
import util.SharedPref;

public class ExitFragment extends Fragment {
    EditText number;
    TextView totalprice;
    Button exit, ok;
    LinearLayout l1, l2;
    DatabaseHelper db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.exit_fragment, container, false);
        number = (EditText) root.findViewById(R.id.vehicle_number);
        totalprice = (TextView) root.findViewById(R.id.price);
        l1 = (LinearLayout) root.findViewById(R.id.layout);
        l2 = (LinearLayout) root.findViewById(R.id.layout1);
        exit = (Button) root.findViewById(R.id.exit);
        ok = (Button) root.findViewById(R.id.ok);
        db = new DatabaseHelper(getActivity());

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Slot slot = db.getNote(number.getText().toString().trim());
                if (slot == null) {
                    Toast.makeText(getActivity(), "Vehicle Number Not Found", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    //db.deleteNote(slot);
                    slot.setExit_timestamp(""+System.currentTimeMillis());
                    slot.setNumber(number.getText().toString().trim());
                    //db.updateNote(slot);
                    db.updateItem(slot);
                    Toast.makeText(getActivity(), "Vehicle Exit Successfully", Toast.LENGTH_SHORT).show();
                    SharedPref sp = new SharedPref();

                    sp.init(getActivity());
                    sp.write(slot.getFloor_type(), sp.read(slot.getFloor_type(), 0) + 1);
                    int price = calculatePrice(slot.getEntry_timestamp(), "" + System.currentTimeMillis(), slot.getVehicle_type());
                    l1.setVisibility(View.GONE);
                    l2.setVisibility(View.VISIBLE);
                    totalprice.setText("" + price + ".00 Rs");
                    //System.out.print(DateFormat.getDateTimeInstance().format(new Date()));
                }
                number.setText("");
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return root;
    }

    private int calculatePrice(String entry_timestamp, String exit_timestamp, String vehicletype) {
        long date1 = Long.parseLong(entry_timestamp);
        long date2 = Long.parseLong(exit_timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

        Log.d("hello", "date1" + sdf.format(date1) + " date2" + sdf.format(date2));

        String dateStart = "" + sdf.format(date1);
        String dateStop = "" + sdf.format(date2);

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy HH:mm");

        Date d1 = null;
        Date d2 = null;


        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            Log.d("hello", "hello" + diffDays + " " + diffHours + " " + diffMinutes);
            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

            int price = 0;
            int hour = (int) diffHours;
            int min = (int) diffMinutes;
            if (vehicletype.equals("BIKE")) {

                if(hour==0){
                    price = min * (50 / 60);
                }else if(hour==1){
                    price = 50 + min * (25 / 60);
                }else{
                    price = 50 + ((hour--) * 25);
                    price = price + min * (25 / 60);
                }
            }
            if (vehicletype.equals("SC")) {
                if(hour==0){
                    price = min * (100 / 60);
                }else if(hour==1){
                    price = 100 + min * (45 / 60);
                }else{
                    price = 100 + ((hour--) * 45);
                    price = price + min * (45 / 60);
                }
            }
            if (vehicletype.equals("MC")) {
                if(hour==0){
                    price = min * (150 / 60);
                }else if(hour==1){
                    price = 150 + min * (75 / 60);
                }else{
                    price = 150 + ((hour--) * 75);
                    price = price + min * (75 / 60);
                }
            }
            if (vehicletype.equals("LC")) {
                if(hour==0){
                    price = min * (200 / 60);
                }else if(hour==1){
                    price = 200 + min * (100 / 60);
                }else{
                    price = 200 + ((hour--) * 100);
                    price = price + min * (100 / 60);
                }
            }
            if((int)diffDays==5){
                price=price *(25/100);
            }
            return price;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
