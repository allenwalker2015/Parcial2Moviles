package com.alphadev.gamesnews.adapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.alphadev.gamesnews.room.model.New;

import java.util.List;

public class MyDiffCallback extends DiffUtil.Callback {

    List<New> oldNews;
    List<New> newNews;

    public MyDiffCallback(List<New> newNews, List<New> oldNews) {
        this.newNews = newNews;
        this.oldNews = oldNews;
    }

    @Override
    public int getOldListSize() {
        return oldNews.size();
    }

    @Override
    public int getNewListSize() {
        return newNews.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldNews.get(oldItemPosition).getId() == newNews.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldNews.get(oldItemPosition).equals(newNews.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}