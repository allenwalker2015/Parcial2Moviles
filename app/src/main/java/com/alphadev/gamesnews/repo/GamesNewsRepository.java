package com.alphadev.gamesnews.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.alphadev.gamesnews.api.GamesNewsAPIService;
import com.alphadev.gamesnews.api.data.remote.GamesNewsAPIUtils;
import com.alphadev.gamesnews.api.pojo.New;
import com.alphadev.gamesnews.room.GamesNewsDataBase;
import com.alphadev.gamesnews.room.dao.NewDao;
import com.alphadev.gamesnews.room.dao.PlayerDao;
import com.alphadev.gamesnews.room.dao.UserDao;
import com.alphadev.gamesnews.room.model.Player;
import com.alphadev.gamesnews.room.model.User;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GamesNewsRepository {
    private final LiveData<List<com.alphadev.gamesnews.room.model.New>> mAllNews;
    private final LiveData<List<com.alphadev.gamesnews.room.model.New>> mAllFavoriteNews;
    private GamesNewsAPIService service;
    private Application application;
    //    private FavoriteNewsDao favoriteNewsDao;
    private static NewDao newDao;
    private static PlayerDao playerDao;
    private static UserDao userDao;

    public GamesNewsRepository(Application application) {
        this.application = application;
        GamesNewsDataBase db = GamesNewsDataBase.getDatabase(application);
        // favoriteNewsDao = db.favoriteNewsDao();
        newDao = db.newDao();
        playerDao = db.playerDao();
        userDao = db.userDao();
        service = GamesNewsAPIUtils.getAPIService();

        mAllNews = newDao.getAllNews();
        mAllFavoriteNews = newDao.getFavoritesNews();
    }

//    public List<New> getAllNews(String token) {
//        AsyncTask<String, Void, List<New>> task = new AsyncTask<String, Void, List<New>>() {
//            @Override
//            protected List<New> doInBackground(String... strings) {
//                 List<New> list = null;
//                if (isOnline()) {
//
//                    try {
//                        list = service.getAllNews(strings[0]).execute().body();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                return list;
//            }
//
//        };
//
//        try {
//            return task.execute(token).get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public LiveData<List<com.alphadev.gamesnews.room.model.New>> getAllNews() {
        return mAllNews;
    }

    public LiveData<List<com.alphadev.gamesnews.room.model.New>> getNewsByCategory(String category) {
        return newDao.getNewsByCategory(category);
    }

    public LiveData<List<Player>> getPlayersByCategory(String category) {
        return playerDao.getPlayersByCategory(category);
    }

    public LiveData<List<com.alphadev.gamesnews.room.model.New>> getFavoriteNews() {
        return mAllFavoriteNews;
    }


    public com.alphadev.gamesnews.room.model.New getNewById(String id) {
        com.alphadev.gamesnews.room.model.New n = null;
        AsyncTask<String, Void, com.alphadev.gamesnews.room.model.New> task = new AsyncTask<String, Void, com.alphadev.gamesnews.room.model.New>() {
            @Override
            protected com.alphadev.gamesnews.room.model.New doInBackground(String... strings) {
                com.alphadev.gamesnews.room.model.New n = null;
                n = newDao.getNewByID(strings[0]);
                return n;
            }
        };
        try {
            n = task.execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return n;
    }

    public User getCurrentUserInfo() {
        return userDao.getUser();
    }

    public boolean insertUser(User user) {
        boolean b = false;
        AsyncTask<User, Void, Boolean> task = new AsyncTask<User, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(User... users) {
                userDao.deleteAll();
                userDao.insert(users[0]);
                return true;
            }
        };
        try {
            b = task.execute(user).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return b;
    }

    //Borra todas las news almacenadas y luego inserta sobre ellas.
    public boolean updateNews(String token) {
        UpdateNewsTask task = new UpdateNewsTask();
        try {
            boolean b = task.execute(token).get();
            return b;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public class UpdateNewsTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean b = false;

            List<New> list = null;
            try {
                list = service.getAllNews(strings[0]).execute().body();
                if (list != null) {
                    newDao.deleteAll();
                    for (New n : list) {
                        newDao.insert(new com.alphadev.gamesnews.room.model.New(n.getId(),
                                n.getTitle(), n.getBody(), n.getGame(), n.getCoverImage(),
                                n.getDescription(),n.getCreatedDate(), false));
                        b = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
