package com.alphadev.gamesnews.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alphadev.gamesnews.R;
import com.alphadev.gamesnews.adapter.ImageLayout;
import com.alphadev.gamesnews.adapter.MyImagesRecyclerViewAdapter;
import com.alphadev.gamesnews.viewmodel.GamesNewsViewModel;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class Game_ImagesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count", TOKEN = "token", CATEGORY = "category";
    // TODO: Customize parameters
    private int mColumnCount = 3;
    // private OnListFragmentInteractionListener mListener;
    private String token;
    private String category;
    private GamesNewsViewModel gamesNewsViewModel;
    private MyImagesRecyclerViewAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public Game_ImagesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static Game_ImagesFragment newInstance(int columnCount, String token, String category) {
        Game_ImagesFragment fragment = new Game_ImagesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(TOKEN, token);
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            token = getArguments().getString(TOKEN);
            category = getArguments().getString(CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images_list, container, false);
        gamesNewsViewModel = ViewModelProviders.of(this).get(GamesNewsViewModel.class);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new ImageLayout(context, mColumnCount));
            }
            final LiveData<List<String>> list = gamesNewsViewModel.getAllNewsImageByCategory(category);
            mAdapter = new MyImagesRecyclerViewAdapter(context);
            recyclerView.setAdapter(mAdapter);

            list.observe(this, new Observer<List<String>>() {
                @Override
                public void onChanged(@Nullable List<String> strings) {
                    mAdapter.setList(strings);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    public interface OnListFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onListFragmentInteraction(DummyItem item);
    }
}
