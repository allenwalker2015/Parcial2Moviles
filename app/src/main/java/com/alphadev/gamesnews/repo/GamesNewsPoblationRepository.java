package com.alphadev.gamesnews.repo;

import android.app.Application;
import android.os.AsyncTask;

import com.alphadev.gamesnews.R;
import com.alphadev.gamesnews.api.GamesNewsAPIService;
import com.alphadev.gamesnews.api.data.remote.GamesNewsAPIUtils;
import com.alphadev.gamesnews.api.pojo.MessageResultPOJO;
import com.alphadev.gamesnews.api.pojo.NewNewPOJO;
import com.alphadev.gamesnews.api.pojo.NewPOJO;
import com.alphadev.gamesnews.api.pojo.PlayerPOJO;
import com.alphadev.gamesnews.api.pojo.UserPOJO;
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

import retrofit2.Call;

public class GamesNewsPoblationRepository {
    private static FavoriteDao favoriteDao;
    private GamesNewsAPIService service;
    private static NewDao newDao;
    private static PlayerDao playerDao;
    private static UserDao userDao;

    public GamesNewsPoblationRepository(Application application) {
        GamesNewsDataBase db = GamesNewsDataBase.getDatabase(application);
        newDao = db.newDao();
        playerDao = db.playerDao();
        userDao = db.userDao();
        service = GamesNewsAPIUtils.getAPIService(application);
        favoriteDao = db.favoriteDao();
    }

    public boolean updatePassword(String token, String iduser, String new_password) {
        Call<UserPOJO> var = service.editUser(token, iduser, new_password);
        try {
            UserPOJO user = var.execute().body();
            if (user != null) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    //Borra la informacion de usuario solo si se obtuvo un resultado correcto la api,y la inserta la nueva informacion en la base.
    //Esta la cree porque si ejecutas una AsyncTask dentro de otra AsyncTask se loopea y nunca termina.
    // que es el caso de cuando compruebo las credenciales del usuario. en la actividad del login
    public boolean updateUserInfoNoAsync(String token) {
        UserPOJO user;
        boolean b = false;
        try {
            user = service.getUserDetail(token).execute().body();
            if (user != null) {
                userDao.deleteAll();
                favoriteDao.deleteAll();
                userDao.insert(new User(user.getId(), user.getUser(), ""));
                for (String n : user.getNews()) {
                    favoriteDao.insert(new Favorite(n, user.getId()));
                }
                b = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    //Borra todas las noticias solo si se obtuvo un nuevo listado desde la api, y las inserta en la base.
    public int updateNews(String token) {
        List<NewPOJO> list;
        try {
            list = service.getAllNews(token).execute().body();
            if (list != null) {
                newDao.deleteAll();
                List<String> listfav = favoriteDao.getAllNewsID();
                for (NewPOJO n : list) {
                    newDao.insert(new com.alphadev.gamesnews.room.model.New(n.getId(),
                            n.getTitle(), n.getBody(), n.getGame(), n.getCoverImage(),
                            n.getDescription(), n.getCreatedDate(), listfav.contains(n.getId())));
                }
            } else {
                return R.string.server_error_msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return R.string.internal_error_msg;
        }
        return R.string.sucess;

    }

    public class UpdateNewsTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean b = false;

            List<NewPOJO> list;
            try {
                list = service.getAllNews(strings[0]).execute().body();
                if (list != null) {
                    newDao.deleteAll();
                    List<String> listfav = favoriteDao.getAllNewsID();
                    for (NewPOJO n : list) {
                        newDao.insert(new com.alphadev.gamesnews.room.model.New(n.getId(),
                                n.getTitle(), n.getBody(), n.getGame(), n.getCoverImage(),
                                n.getDescription(), n.getCreatedDate(), listfav.contains(n.getId())));

                        b = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }
    }

    //Borra las noticas de una categoria solo si se obtuvo un nuevo listado desde la api,y las inserta en la base.
    public int updateNewsByCategory(String token, String category) {
        List<NewPOJO> list;
        try {
            list = service.getNewsByCategory(token, category).execute().body();
            if (list != null) {
                newDao.deleteByCategory(category);
                List<String> listfav = favoriteDao.getAllNewsID();
                for (NewPOJO n : list) {
                    newDao.insert(new com.alphadev.gamesnews.room.model.New(n.getId(),
                            n.getTitle(), n.getBody(), n.getGame(), n.getCoverImage(),
                            n.getDescription(), n.getCreatedDate(), listfav.contains(n.getId())));
                }
            } else {
                return R.string.server_error_msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return R.string.internal_error_msg;
        }
        return R.string.sucess;
    }


    //Borra la informacion de usuario solo si se obtuvo un resultado correcto la api,y la inserta la nueva informacion en la base.
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

    public class UpdateUserInfoTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean b = false;

            UserPOJO user;
            try {
                user = service.getUserDetail(strings[0]).execute().body();
                if (user != null) {
                    userDao.deleteAll();
                    favoriteDao.deleteAll();
                    userDao.insert(new User(user.getId(), user.getUser(), ""));
                    for (String n : user.getNews()) {
                        favoriteDao.insert(new Favorite(n, user.getId()));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }
    }

    //Borra los players que pertenecen a una categoria solo si se obtuvo un resultado correcto la api,y inserta la nueva informacion en la base.
    public int updatePlayersByCategory(String token, String category) {
        List<PlayerPOJO> list;
        try {
            list = service.getPlayersByCategory(token, category).execute().body();
            if (list != null) {
                playerDao.deleteAll();
                for (PlayerPOJO n : list) {
                    playerDao.insert(new Player(
                            n.getAvatar(), n.getId(), n.getName(), n.getBiografia(), n.getGame()));
                }
            } else return R.string.server_error_msg;
        } catch (IOException e) {
            e.printStackTrace();
            return R.string.internal_error_msg;
        }
        return R.string.sucess;
    }


    //Inserta un favorito, si se pudo almacenar en la api se procede a guardar como favorito en la db.
    public boolean addFavorite(String token, String user, String n_new) {
        addFavoriteTask task = new addFavoriteTask();
        try {
            boolean b = task.execute(token, user, n_new).get();
            return b;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public class addFavoriteTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean b = false;
            NewNewPOJO n;

            try {
                n = service.addUserFav(strings[0], strings[1], strings[2]).execute().body();
                if (n != null) {
                    favoriteDao.insert(new Favorite(strings[2], strings[1]));
                    newDao.setFavorite(strings[2]);
                    b = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }
    }

    //Elimina un favorito, si se pudo eliminar en la api se procede a borrar como favorito en la db.
    public boolean removeFavorite(String token, String user, String n_new) {
        removeFavoriteTask task = new removeFavoriteTask();
        try {
            boolean b = task.execute(token, user, n_new).get();
            return b;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public class removeFavoriteTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean b = false;
            MessageResultPOJO n;

            try {
                n = service.deleteUserFav(strings[0], strings[1], strings[2]).execute().body();
                if (n != null) {
                    favoriteDao.delete(strings[2], strings[1]);
                    newDao.unsetFavorite(strings[2]);
                    com.alphadev.gamesnews.room.model.New n_30 = newDao.getNewByID(strings[2]);
                    b = true;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }
    }


}
