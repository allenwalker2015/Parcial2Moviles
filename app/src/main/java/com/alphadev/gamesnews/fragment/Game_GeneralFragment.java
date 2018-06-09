package com.alphadev.gamesnews.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alphadev.gamesnews.R;
import com.alphadev.gamesnews.adapter.MyNewsRecyclerViewAdapter;
import com.alphadev.gamesnews.room.model.New;
import com.alphadev.gamesnews.viewmodel.GamesNewsViewModel;

import java.util.List;


public class Game_GeneralFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count", TOKEN = "token", CATEGORY = "category";
    private int mColumnCount = 1;
    private NewsFragment.OnListFragmentInteractionListener mListener;
    private GamesNewsViewModel gamesNewsViewModel;
    private String token;
    private MyNewsRecyclerViewAdapter mAdapter;
    private String category;


    public Game_GeneralFragment() {
    }


    @SuppressWarnings("unused")
    public static Game_GeneralFragment newInstance(int columnCount, String token, String category) {
        Game_GeneralFragment fragment = new Game_GeneralFragment();
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
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            gamesNewsViewModel.updateNewsByCategory(token, category);
            final LiveData<List<New>> list = gamesNewsViewModel.getAllNewsByCategory(category);

            mAdapter = new MyNewsRecyclerViewAdapter(context) {
                @Override
                public void setAction(boolean isFavorite, String id_new) {

                }
            };
            list.observe(this, new Observer<List<New>>() {
                @Override
                public void onChanged(@Nullable List<New> news) {
                    mAdapter.setList(news);
                    mAdapter.notifyDataSetChanged();
                }
            });

//            AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
//                @Override
//                protected Void doInBackground(Void... voids) {
//                    mAdapter.setList(list.getValue());
//                    return null;
//                }
//            };
//            task.execute();

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                GridLayoutManager mLayoutManager = new GridLayoutManager(context, mColumnCount);
                mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        switch (mAdapter.getItemViewType(position)) {
                            case 1:
                                return 2;
                            default:
                                return 1;
                        }
                    }
                });
                recyclerView.setLayoutManager(mLayoutManager);
            }
            recyclerView.setAdapter(mAdapter);
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
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        // void onListFragmentInteraction(DummyItem item);
    }
}
