package com.alphadev.gamesnews.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.alphadev.gamesnews.R;
import com.alphadev.gamesnews.adapter.MyNewsRecyclerViewAdapter;
import com.alphadev.gamesnews.room.model.New;
import com.alphadev.gamesnews.viewmodel.GamesNewsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private static final String ARG_COLUMN_COUNT = "column-count", TOKEN = "token";
    private static final String USER_ID = "userId";
    private int mColumnCount = 1;
    SharedPreferences sp;
    private OnListFragmentInteractionListener mListener;
    private GamesNewsViewModel gamesNewsViewModel;
    private String token, user;
    private MyNewsRecyclerViewAdapter mAdapter;
    private LiveData<List<New>> list;
    SwipeRefreshLayout mySwipeRefreshLayout;


    public NewsFragment() {
    }


    @SuppressWarnings("unused")
    public static NewsFragment newInstance(int columnCount, String token) {
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
        gamesNewsViewModel = ViewModelProviders.of(this).get(GamesNewsViewModel.class);
        sp = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        mySwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        mySwipeRefreshLayout.setOnRefreshListener(this);
        doInBackGroundTask task = new doInBackGroundTask();
        task.execute();
        user = sp.getString(USER_ID, "");
        list = gamesNewsViewModel.getAllNews();

        mAdapter = new MyNewsRecyclerViewAdapter(context) {
            @Override
            public void setAction(boolean isFavorite, String n_new) {
                if (!isFavorite) {
                    if (!gamesNewsViewModel.addFavorite(token, user, n_new)) {
                        Toast.makeText(getContext(), R.string.mensaje_error, Toast.LENGTH_SHORT).show();
                    }
                } else if (!gamesNewsViewModel.removeFavorite(token, user, n_new)) {
                    Toast.makeText(getContext(), R.string.mensaje_error, Toast.LENGTH_SHORT).show();
                }
            }
        };
        list.observe(this, new Observer<List<New>>() {
            @Override
            public void onChanged(@Nullable List<New> news) {
                List<New> new_list = new ArrayList<>();
                mAdapter.setList(new_list);
                mAdapter.notifyDataSetChanged();
                for (New n : news) {
                    new_list.add(n);
                    mAdapter.notifyItemInserted(new_list.size());
                }
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
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.grid_layout_animation_from_bottom);
        recyclerView.setLayoutAnimation(animation);
        recyclerView.setAdapter(mAdapter);

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

    private class doInBackGroundTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return gamesNewsViewModel.updateNews(token);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            mySwipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(integer);
        }
    }
}
