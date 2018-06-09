package com.alphadev.gamesnews.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alphadev.gamesnews.R;
import com.alphadev.gamesnews.adapter.SectionsPageAdapter;

public class CategoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TOKEN = "token";
    private static final String CATEGORY = "category";

    // TODO: Rename and change types of parameters
    private String token;
    private String category;

    private OnFragmentInteractionListener mListener;
    private ViewPager viewPager;

    public CategoryFragment() {
        // Required empty public constructor
    }


    public static CategoryFragment newInstance(String token, String category) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(TOKEN, token);
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token = getArguments().getString(TOKEN);
            category = getArguments().getString(CATEGORY);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        // Inflate the layout for this fragment
        viewPager = v.findViewById(R.id.viewPager);
        SectionsPageAdapter adapter = new SectionsPageAdapter(getFragmentManager(), token, category, getContext());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = v.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //  mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
