package com.alphadev.gamesnews.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphadev.gamesnews.api.pojo.New;
import com.alphadev.gamesnews.fragment.NewsFragment.OnListFragmentInteractionListener;
import com.alphadev.gamesnews.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public class MyNewsRecyclerViewAdapter extends RecyclerView.Adapter<MyNewsRecyclerViewAdapter.ViewHolder> {

    private final List<New> list;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public MyNewsRecyclerViewAdapter(List<New> items, OnListFragmentInteractionListener listener, Context context) {
        list = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news, parent, false);
        return new ViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(list!=null) {
            holder.title.setText(list.get(position).getTitle());
            holder.content.setText(list.get(position).getDescription());
            Glide.with(context).load(list.get(position).getCoverImage()).apply(RequestOptions.centerCropTransform()).into(holder.image);
        }
       // holder.image.setImageURI(Uri.parse(list.get(position).getCoverImage()));


//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if(list!=null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ImageView image;
        public TextView title,content;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            image = view.findViewById(R.id.image);
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0) return 1;
        else return 0;
    }
}
