package com.alphadev.gamesnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alphadev.gamesnews.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public abstract class MyImagesRecyclerViewAdapter extends RecyclerView.Adapter<MyImagesRecyclerViewAdapter.ViewHolder> {

    private List<String> list;
    private final Context context;

    public MyImagesRecyclerViewAdapter(Context context) {
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_images, parent, false);
//        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
//        lp.height = parent.getMeasuredHeight() / 4;
//        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.mItem = list.get(position);
//        holder.mIdView.setText(list.get(position).id);
//        holder.mContentView.setText(list.get(position).content);
        if (list != null) {
            Glide.with(context).load(list.get(position)).apply(RequestOptions.centerCropTransform()).into(holder.imageView);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImageClick(list.get(position));
                }
            });

        }
    }

    public abstract void onImageClick(String s);

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else return 0;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageView;
        ///public final TextView mIdView;
        // public final TextView mContentView;
        // public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageView = view.findViewById(R.id.coverImage);

            //mIdView = (TextView) view.findViewById(R.id.coverImage);
            //mContentView = (TextView) view.findViewById(R.id.content);
        }

    }


}
