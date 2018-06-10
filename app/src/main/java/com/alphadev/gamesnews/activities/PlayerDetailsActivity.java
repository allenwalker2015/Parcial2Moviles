package com.alphadev.gamesnews.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphadev.gamesnews.R;
import com.alphadev.gamesnews.room.model.Player;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class PlayerDetailsActivity extends AppCompatActivity {
    private static final String PLAYER = "player";
    Player player;
    ImageView avatar;
    TextView game, biografia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle b = getIntent().getExtras();
        if (b != null) {
            player = (Player) b.getSerializable(PLAYER);
            findViews();
            Glide.with(this).load(player.getAvatar()).apply(RequestOptions.centerCropTransform()).into(avatar);
            game.setText(player.getGame());
            biografia.setText(player.getBiografia());
            getSupportActionBar().setTitle(player.getName());
        }
    }

    public void findViews() {
        avatar = findViewById(R.id.avatar);
        game = findViewById(R.id.game);
        biografia = findViewById(R.id.biografia);
    }
}
