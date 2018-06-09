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
import com.alphadev.gamesnews.api.pojo.UserWithFavs;
import com.alphadev.gamesnews.room.GamesNewsDataBase;
import com.alphadev.gamesnews.room.dao.FavoriteDao;
import com.alphadev.gamesnews.room.dao.NewDao;
import com.alphadev.gamesnews.room.dao.PlayerDao;
import com.alphadev.gamesnews.room.dao.UserDao;
import com.alphadev.gamesnews.room.model.Favorite;
import com.alphadev.gamesnews.room.model.Player;
import com.alphadev.gamesnews.room.model.User;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GamesNewsRepository {
    private final LiveData<List<com.alphadev.gamesnews.room.model.New>> mAllNews;
    private final LiveData<List<com.alphadev.gamesnews.room.model.New>> mAllFavoriteNews;
    private static FavoriteDao favoriteDao;
    private GamesNewsAPIService service;
    private Application application;
    //    private FavoriteNewsDao favoriteNewsDao;
    private static NewDao newDao;
    private static PlayerDao playerDao;
    private static UserDao userDao;

    public GamesNewsRepository(Application application) {
        this.application = application;
        GamesNewsDataBase db = GamesNewsDataBase.getDatabase(application);
        newDao = db.newDao();
        playerDao = db.playerDao();
        userDao = db.userDao();
        service = GamesNewsAPIUtils.getAPIService();
        favoriteDao = db.favoriteDao();
        mAllNews = newDao.getAllNews();
        mAllFavoriteNews = newDao.getFavoritesNews();
    }


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

    public boolean updateUserInfo(String token) {
        UpdateUserInfoTask task = new UpdateUserInfoTask();
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

    public boolean updateNewsByCategory(String token, String category) {
        UpdateNewsByCategoryTask task = new UpdateNewsByCategoryTask();
        try {
            boolean b = task.execute(token, category).get();
            return b;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updatePlayersByCategory(String token, String category) {
        UpdatePlayersTaskByCategory task = new UpdatePlayersTaskByCategory();
        try {
            boolean b = task.execute(token, category).get();
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

            List<New> list;
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

    public class UpdateNewsByCategoryTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean b = false;

            List<New> list;
            try {
                list = service.getNewsByCategory(strings[0], strings[1]).execute().body();
                if (list != null) {
                    newDao.deleteByCategory(strings[1]);
                    for (New n : list) {
                        newDao.insert(new com.alphadev.gamesnews.room.model.New(n.getId(),
                                n.getTitle(), n.getBody(), n.getGame(), n.getCoverImage(),
                                n.getDescription(), n.getCreatedDate(), false));
                        b = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }
    }

    public class UpdateUserInfoTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean b = false;

            UserWithFavs user;
            try {
                user = service.getUserDetail(strings[0]).execute().body();
                if (user != null) {
                    userDao.deleteAll();
                    favoriteDao.deleteAll();
                    userDao.insert(new User(user.getId(), user.getUser(), user.getAvatar()));
                    for (New n : user.getNews()) {
                        favoriteDao.insert(new Favorite(n.getId(), user.getId()));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }
    }

    public class UpdatePlayersTaskByCategory extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean b = false;

            List<com.alphadev.gamesnews.api.pojo.Player> list;
            try {
                list = service.getPlayersByCategory(strings[0], strings[1]).execute().body();
                if (list != null) {
                    newDao.deleteAll();
                    for (com.alphadev.gamesnews.api.pojo.Player n : list) {
                        playerDao.insert(new com.alphadev.gamesnews.room.model.Player(
                                n.getAvatar(), n.getId(), n.getName(), n.getBiografia(), n.getGame()));
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
