package view.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.List;

import db.DatabaseHelper;
import info.example.abc.R;
import model.Slot;
import util.SharedPref;
import view.adapter.SlotAdapter;

@SuppressLint("ValidFragment")
class ViewFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.view_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.recycler_view);

        DatabaseHelper db = new DatabaseHelper(getActivity());
        List<Slot> slotlist=db.getAllNotes();
        SlotAdapter mAdapter = new SlotAdapter(slotlist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        return root;
    }
}