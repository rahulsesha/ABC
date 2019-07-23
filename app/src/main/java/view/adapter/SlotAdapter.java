package view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import info.example.abc.R;
import model.Slot;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.MyViewHolder> {

    private List<Slot> slot;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView details, entry, exit,price;

        public MyViewHolder(View view) {
            super(view);
            details = (TextView) view.findViewById(R.id.details);
            entry = (TextView) view.findViewById(R.id.entry);
            exit = (TextView) view.findViewById(R.id.exit);
            price = (TextView) view.findViewById(R.id.price);

        }
    }


    public SlotAdapter(List<Slot> slot) {
        this.slot = slot;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slot_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Slot slot1 = slot.get(position);
        holder.details.setText(slot1.getNumber()+" "+slot1.getVehicle_type()+" "+slot1.getFloor_type() );

        long date1 = Long.parseLong(slot1.getEntry_timestamp());
        long date2 = Long.parseLong(slot1.getExit_timestamp());
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        String dateStart = "" + sdf.format(date1);
        String dateStop = "" + sdf.format(date2);
        holder.entry.setText(dateStart);
        holder.exit.setText(dateStop);
        //
        SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy HH:mm");

        Date d1 = null;
        Date d2 = null;

        int price = 0;
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

            int hour = (int) diffHours;
            int min = (int) diffMinutes;
            if (slot1.getVehicle_type().equals("BIKE")) {

                if(hour==0){
                    price = min * (50 / 60);
                }else if(hour==1){
                    price = 50 + min * (25 / 60);
                }else{
                    price = 50 + ((hour--) * 25);
                    price = price + min * (25 / 60);
                }
            }
            if (slot1.getVehicle_type().equals("SC")) {
                if(hour==0){
                    price = min * (100 / 60);
                }else if(hour==1){
                    price = 100 + min * (45 / 60);
                }else{
                    price = 100 + ((hour--) * 45);
                    price = price + min * (45 / 60);
                }
            }
            if (slot1.getVehicle_type().equals("MC")) {
                if(hour==0){
                    price = min * (150 / 60);
                }else if(hour==1){
                    price = 150 + min * (75 / 60);
                }else{
                    price = 150 + ((hour--) * 75);
                    price = price + min * (75 / 60);
                }
            }
            if (slot1.getVehicle_type().equals("LC")) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        holder.price.setText(""+price+".00 Rs");
    }

    @Override
    public int getItemCount() {
        return slot.size();
    }
}