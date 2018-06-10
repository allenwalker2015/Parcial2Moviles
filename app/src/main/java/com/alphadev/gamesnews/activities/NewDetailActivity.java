package com.alphadev.gamesnews.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphadev.gamesnews.R;
import com.alphadev.gamesnews.room.model.New;
import com.alphadev.gamesnews.viewmodel.GamesNewsViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class NewDetailActivity extends AppCompatActivity {
    TextView game, body;
    ImageView coverImage;
    private SharedPreferences sp;
    private String user;
    private static final String USER_ID = "userId";
    private String token;
    private New n;
    private GamesNewsViewModel gamesNewsViewModel;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViews();
        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        user = sp.getString(USER_ID, "");
        token = sp.getString("token", "");
        gamesNewsViewModel = ViewModelProviders.of(this).get(GamesNewsViewModel.class);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            n = (New) b.getSerializable("new");
            if (n != null) {
                Glide.with(this).load(n.getCoverImage()).apply(RequestOptions.centerCropTransform()).into(coverImage);
                body.setText(n.getBody());
                game.setText(n.getGame());
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle(n.getTitle());

                fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!n.isFavorite()) {
                            gamesNewsViewModel.addFavorite(token, user, n.getId());
                            fab.setImageResource(android.R.drawable.star_big_on);
                        } else {
                            gamesNewsViewModel.removeFavorite(token, user, n.getId());
                            fab.setImageResource(android.R.drawable.star_big_off);
                        }
                    }
                });
            }
        }


    }

    public void findViews() {
        body = findViewById(R.id.body);
        game = findViewById(R.id.game);
        coverImage = findViewById(R.id.coverImage);
    }
}
