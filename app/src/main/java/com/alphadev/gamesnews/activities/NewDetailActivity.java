package com.alphadev.gamesnews.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphadev.gamesnews.R;
import com.alphadev.gamesnews.room.model.New;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class NewDetailActivity extends AppCompatActivity {
    TextView game, body;
    ImageView coverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViews();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Bundle b = getIntent().getExtras();
        if (b != null) {
            New n = (New) b.getSerializable("new");
            if (n != null) {
                Glide.with(this).load(n.getCoverImage()).apply(RequestOptions.centerCropTransform()).into(coverImage);
                body.setText(n.getBody());
                game.setText(n.getGame());
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle(n.getTitle());
            }
        }
    }

    public void findViews() {
        body = findViewById(R.id.body);
        game = findViewById(R.id.game);
        coverImage = findViewById(R.id.coverImage);
    }
}
