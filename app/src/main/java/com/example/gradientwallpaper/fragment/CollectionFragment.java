package com.example.gradientwallpaper.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gradientwallpaper.Gradient;
import com.example.gradientwallpaper.GradientAdapter;
import com.example.gradientwallpaper.MainActivity;
import com.example.gradientwallpaper.R;
import com.example.gradientwallpaper.ViewCollectionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionFragment extends Fragment {
    private View mView;
    private RecyclerView rcvWallpaper;
    private GradientAdapter gradientAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CollectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CollectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CollectionFragment newInstance(String param1, String param2) {
        CollectionFragment fragment = new CollectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_collection, container, false);
        rcvWallpaper=mView.findViewById(R.id.rcvWallpaper);
        gradientAdapter = new GradientAdapter(getActivity());

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        rcvWallpaper.setLayoutManager(gridLayoutManager);

        gradientAdapter.setData(getListGradient());
        rcvWallpaper.setAdapter(gradientAdapter);
        rcvWallpaper.setItemAnimator(new DefaultItemAnimator());
        addEvents();
        return mView;
    }

    private void addEvents() {
        List<Gradient> list=getListGradient();
        gradientAdapter.setOnItemClickListener(new GradientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Gradient itemSelected=list.get(position);
                Intent intent=new Intent(getActivity(), ViewCollectionActivity.class);
                intent.putExtra("GRADIENT",itemSelected);
                startActivity(intent);
            }
        });
    }

    private List<Gradient> getListGradient() {
        List<Gradient> list=new ArrayList<>();
        list.add(new Gradient(R.drawable.gradient1,"Gradient name 1"));
        list.add(new Gradient(R.drawable.gradient2,"Gradient name 2"));
        list.add(new Gradient(R.drawable.gradient3,"Gradient name 3"));
        list.add(new Gradient(R.drawable.gradient4,"Gradient name 4"));
        list.add(new Gradient(R.drawable.gradient5,"Gradient name 5"));
        list.add(new Gradient(R.drawable.gradient6,"Gradient name 6"));
        list.add(new Gradient(R.drawable.gradient7,"Gradient name 7"));
        list.add(new Gradient(R.drawable.gradient8,"Gradient name 8"));
        list.add(new Gradient(R.drawable.gradient9,"Gradient name 9"));
        list.add(new Gradient(R.drawable.gradient10,"Gradient name 10"));
        list.add(new Gradient(R.drawable.gradient11,"Gradient name 11"));
        list.add(new Gradient(R.drawable.gradient12,"Gradient name 12"));
        list.add(new Gradient(R.drawable.gradient13,"Gradient name 13"));
        list.add(new Gradient(R.drawable.gradient14,"Gradient name 14"));
        list.add(new Gradient(R.drawable.gradient15,"Gradient name 15"));
        list.add(new Gradient(R.drawable.gradient16,"Gradient name 16"));
        list.add(new Gradient(R.drawable.gradient17,"Gradient name 17"));
        list.add(new Gradient(R.drawable.gradient18,"Gradient name 18"));
        list.add(new Gradient(R.drawable.gradient19,"Gradient name 19"));
        list.add(new Gradient(R.drawable.gradient20,"Gradient name 20"));

        return list;
    }
}