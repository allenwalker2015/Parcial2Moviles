package com.alphadev.gamesnews.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
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

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NewsFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count", TOKEN = "token";
    private static final String USER_ID = "userId";
    private int mColumnCount = 1;
    SharedPreferences sp;
    private OnListFragmentInteractionListener mListener;
    private GamesNewsViewModel gamesNewsViewModel;
    private String token, user;
    private MyNewsRecyclerViewAdapter mAdapter;


    public NewsFragment() {
    }


    @SuppressWarnings("unused")
    public static NewsFragment newInstance(int columnCount,String token) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(TOKEN, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            token = getArguments().getString(TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        gamesNewsViewModel =  ViewModelProviders.of(this).get(GamesNewsViewModel.class);
        sp = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            gamesNewsViewModel.updateNews(token);
            user = sp.getString(USER_ID, "");
            final LiveData<List<New>> list = gamesNewsViewModel.getAllNews();

            mAdapter = new MyNewsRecyclerViewAdapter(context) {
                @Override
                public void setAction(boolean isFavorite, String n_new) {
                    if (!isFavorite) {
                        gamesNewsViewModel.addFavorite(token, user, n_new);
                    } else gamesNewsViewModel.removeFavorite(token, user, n_new);
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
                        switch(mAdapter.getItemViewType(position)){
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
