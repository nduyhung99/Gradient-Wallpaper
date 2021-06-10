package com.example.gradientwallpaper.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.example.gradientwallpaper.GradientSaved;
import com.example.gradientwallpaper.GradientSavedAdapter;
import com.example.gradientwallpaper.MainActivity;
import com.example.gradientwallpaper.R;
import com.example.gradientwallpaper.ViewSavedActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedFragment extends Fragment {
    private View view;
    ArrayList<String> f = new ArrayList<String>();// list of file paths
    File[] listFile;
    private RecyclerView rcvGradientSaved;
    private GradientSavedAdapter mGradientSavedAdapter;
    private List<GradientSaved> list1 = new ArrayList<GradientSaved>();
    SwipeRefreshLayout swipeRefreshLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SavedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedFragment newInstance(String param1, String param2) {
        SavedFragment fragment = new SavedFragment();
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
        view = inflater.inflate(R.layout.fragment_saved, container, false);
        rcvGradientSaved = view.findViewById(R.id.rcvGradientSaved);
        mGradientSavedAdapter = new GradientSavedAdapter(getActivity());
//        getFragmentManager().beginTransaction().detach(SavedFragment.this).attach(SavedFragment.this).commit();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcvGradientSaved.setLayoutManager(gridLayoutManager);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            mGradientSavedAdapter.setData(getListGradientSaved());
        }else {
            mGradientSavedAdapter.setData(list1);
        }
        rcvGradientSaved.setAdapter(mGradientSavedAdapter);
        addEvents();
        return view;
    }

    private void addEvents() {
        mGradientSavedAdapter.setOnItemClickListener(new GradientSavedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GradientSaved gradientSaved = list1.get(position);
                Intent intent = new Intent(getActivity(), ViewSavedActivity.class);
                intent.putExtra("GRADIENT_SAVED", gradientSaved);
                startActivity(intent);
            }
        });
    }

    private List<GradientSaved> getListGradientSaved() {
        getFromSdcard();
        return list1;
    }

    public void getFromSdcard() {
        File file = new File(android.os.Environment.getExternalStorageDirectory(), "Pictures/Gradient Wallpaper");

        if (file.isDirectory()) {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++) {

                list1.add(new GradientSaved(listFile[i].getAbsolutePath()));
                mGradientSavedAdapter.notifyDataSetChanged();
            }
        }
    }

}