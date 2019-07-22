package view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    Button exit;
    DatabaseHelper db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.exit_fragment, container, false);
        number=(EditText)root.findViewById(R.id.vehicle_number);
        exit=(Button) root.findViewById(R.id.exit);
        db=new DatabaseHelper(getActivity());

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Slot slot=db.getNote(number.getText().toString().trim());
                if(slot==null){
                    Toast.makeText(getActivity(),"Vehicle Number Not Found",Toast.LENGTH_LONG).show();
                }else{
                    db.deleteNote(slot);
                    Toast.makeText(getActivity(),"Vehicle Exit Successfully"+slot.getNumber()+""+slot.getEntry_timestamp(),Toast.LENGTH_LONG).show();
                    SharedPref sp=new SharedPref();
                    sp.init(getActivity());
                    sp.write(slot.getFloor_type(),sp.read(slot.getFloor_type(),0)+1);
                    calculatePrice(slot.getEntry_timestamp(), DateFormat.getDateTimeInstance().format(new Date()).toString());
                    getActivity().onBackPressed();
                    System.out.print(DateFormat.getDateTimeInstance().format(new Date()));
                }
                number.setText("");
            }
        });


        return  root;
    }

    private void calculatePrice(String entry_timestamp,String exit_timestamp) {
        String dateStart = entry_timestamp;
        String dateStop = exit_timestamp;

        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                Locale.ENGLISH);

        try {
            Date entryDate = sdf.parse(dateStart);
            Date exitDate = sdf.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

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

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
