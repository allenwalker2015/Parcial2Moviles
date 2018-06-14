package com.alphadev.gamesnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphadev.gamesnews.R;
import com.alphadev.gamesnews.activities.NewDetailActivity;
import com.alphadev.gamesnews.room.model.New;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public abstract class MyNewsRecyclerViewAdapter extends RecyclerView.Adapter<MyNewsRecyclerViewAdapter.ViewHolder> {

    private List<New> list;
    //    private int lastPosition = -1;
    private Context context;


    public MyNewsRecyclerViewAdapter( Context context) {

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(list!=null) {
            holder.title.setText(list.get(position).getTitle());
            holder.content.setText(list.get(position).getDescription());
            Glide.with(context).load(list.get(position).getCoverImage()).apply(RequestOptions.centerCropTransform()).into(holder.image);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, NewDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("new", list.get(position));
                    i.putExtras(bundle);
                    context.startActivity(i);
                }
            });
            if (position == 0) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                layoutParams.setFullSpan(true);
            }
            if (list.get(position).isFavorite())
                holder.star.setImageResource(android.R.drawable.star_big_on);
            else holder.star.setImageResource(android.R.drawable.star_big_off);
            holder.star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setAction(list.get(position).isFavorite(), list.get(position).getId());


                }
            });
//            setAnimation(holder.itemView, position);
        }


        // holder.image.setImageURI(Uri.parse(list.get(position).getCoverImage()));


    }


    public abstract void setAction(boolean isFavorite, String id_new);
    @Override
    public int getItemCount() {
        if(list!=null) {
            return list.size();
        }
        return 0;
    }

    public void setList(List<New> list) {
        this.list = list;
    }

    public List<New> getList() {
        return list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //        public final New mNew;
        public ImageView image, star;
        public TextView title,content;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            image = view.findViewById(R.id.image);
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
            star = view.findViewById(R.id.star);
//            mNew = list.get(getAdapterPosition());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0) return 1;
        else return 0;
    }

//    private void setAnimation(View viewToAnimate, int position)
//    {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition)
//        {
//            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
//            viewToAnimate.startAnimation(animation);
//            lastPosition = position;
//        }
//    }
}
