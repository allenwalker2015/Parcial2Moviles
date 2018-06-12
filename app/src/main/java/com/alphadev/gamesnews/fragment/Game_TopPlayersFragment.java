package com.alphadev.gamesnews.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alphadev.gamesnews.R;
import com.alphadev.gamesnews.adapter.MyTopPlayersRecyclerViewAdapter;
import com.alphadev.gamesnews.room.model.Player;
import com.alphadev.gamesnews.viewmodel.GamesNewsViewModel;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class Game_TopPlayersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TOKEN = "token";
    private static final String CATEGORY = "category";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private GamesNewsViewModel gamesNewsViewModel;
    SwipeRefreshLayout mySwipeRefreshLayout;
    private MyTopPlayersRecyclerViewAdapter mAdapter;
    private String token;
    private String category;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public Game_TopPlayersFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static Game_TopPlayersFragment newInstance(int columnCount, String token, String category) {
        Game_TopPlayersFragment fragment = new Game_TopPlayersFragment();
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
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        gamesNewsViewModel = ViewModelProviders.of(this).get(GamesNewsViewModel.class);
        final LiveData<List<Player>> list = gamesNewsViewModel.getPlayersByCategory(category);
        // Set the adapter

        doInBackGroundTask task = new doInBackGroundTask();
        task.execute();
            Context context = view.getContext();
        mySwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        mySwipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mAdapter = new MyTopPlayersRecyclerViewAdapter(getContext());
            recyclerView.setAdapter(mAdapter);


        list.observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable List<Player> players) {
                mAdapter.setList(players);
                mAdapter.notifyDataSetChanged();
            }
        });

//        AsyncTask<Void,Void,Void> setPlayersListTask = new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                mAdapter.setList(list.getValue());
//                return null;
//            }
//        };
//        setPlayersListTask.execute();
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
        mListener = null;
    }

    @Override
    public void onRefresh() {
        doInBackGroundTask task = new doInBackGroundTask();
        task.execute();
    }

    public interface OnListFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onListFragmentInteraction(DummyItem item);
    }


    private class doInBackGroundTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return gamesNewsViewModel.updateTopPlayersByCategory(token, category);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            mySwipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(integer);
        }
    }
}
