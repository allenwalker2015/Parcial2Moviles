package com.alphadev.gamesnews.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.alphadev.gamesnews.R;
import com.alphadev.gamesnews.adapter.DrawerListAdapter;
import com.alphadev.gamesnews.api.GamesNewsAPIService;
import com.alphadev.gamesnews.api.data.remote.GamesNewsAPIUtils;
import com.alphadev.gamesnews.fragment.CategoryFragment;
import com.alphadev.gamesnews.fragment.FavoriteFragment;
import com.alphadev.gamesnews.fragment.FilteredNewsFragment;
import com.alphadev.gamesnews.fragment.NewPasswordFragment;
import com.alphadev.gamesnews.fragment.NewsFragment;
import com.alphadev.gamesnews.menu.MenuModel;
import com.alphadev.gamesnews.viewmodel.GamesNewsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GamesNewsAPIService service;
    private GamesNewsViewModel gamesNewsViewModel;
    SharedPreferences sp;
    private String token = null;
    DrawerListAdapter drawerListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private String newsTitle, gamesTitle, settingsTitle, favoritesTitle, logouTitle;
    private LiveData<List<String>> categories;
    private ArrayList<MenuModel> childModelsList;
    //private Fragment fragment;
    private String changePasswordTitle;
    private SearchView searchView;
    private FilteredNewsFragment filter_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        service = GamesNewsAPIUtils.getAPIService(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        gamesNewsViewModel = ViewModelProviders.of(this).get(GamesNewsViewModel.class);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        TextView name = headerLayout.findViewById(R.id.name);
        name.setText(sp.getString("username", ""));
        expandableListView = findViewById(R.id.expandableListView);
        setupDrawerMenuList();
        setDrawerMenuListeners();
        token = sp.getString("token", "");
        if (token.equals("")) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, NewsFragment.newInstance(2, "Bearer " + token));
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                this.finish();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        searchView = (SearchView) searchItem.getActionView();
        MenuItem.OnActionExpandListener expandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when action item collapses
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container,fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
                if (getSupportFragmentManager().getBackStackEntryCount() > 1)
                    for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount() - 1; i++) {
                        getSupportFragmentManager().popBackStack();
                    }

                filter_fragment = null;
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                filter_fragment = FilteredNewsFragment.newInstance(2, "Bearer " + token, "");
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, filter_fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;  // Return true to expand action view
            }
        };
        searchItem.setOnActionExpandListener(expandListener);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = "%" + newText + "%";
                if (filter_fragment != null) {
                    filter_fragment.updateFilter(text);
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerMenuList() {
        setMenuTitles();
        MenuModel menuModel = new MenuModel(newsTitle, false, true); 
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(gamesTitle, true, true); 
        headerList.add(menuModel);
        categories = gamesNewsViewModel.getNewsCategories();
        childModelsList = new ArrayList<>();
        categories.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                childModelsList.clear();
                for (String category : strings) {

                    MenuModel childModel = new MenuModel(category, false, false);
                    childModelsList.add(childModel);
                }

            }
        });

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
        menuModel = new MenuModel(settingsTitle, true, true);
        headerList.add(menuModel);
        ArrayList<MenuModel> childModelsList2 = new ArrayList<>();
        childModelsList2.add(new MenuModel(logouTitle, false, true));
        childModelsList2.add(new MenuModel(changePasswordTitle, false, true));
        childList.put(menuModel, childModelsList2);

        menuModel = new MenuModel(favoritesTitle, false, true); 
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

    }


    private void setDrawerMenuListeners() {

        drawerListAdapter = new DrawerListAdapter(this, headerList, childList);
        expandableListView.setAdapter(drawerListAdapter);

        //Click listener for parent option
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setMenuTitles();
                Fragment fragment;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        if (headerList.get(groupPosition).menuName.equals(newsTitle)) {
                            fragment = NewsFragment.newInstance(2, "Bearer " + token);
                            transaction.addToBackStack(null);
                            transaction.replace(R.id.fragment_container, fragment).commit();
                        }

                        if (headerList.get(groupPosition).menuName.equals(settingsTitle)) {
                            fragment = new NewPasswordFragment();
                            transaction.addToBackStack(null);
                            transaction.replace(R.id.fragment_container, fragment).commit();
                        }
                        if (headerList.get(groupPosition).menuName.equals(favoritesTitle)) {
                            fragment = FavoriteFragment.newInstance(2, "Bearer " + token);
                            transaction.addToBackStack(null);
                            transaction.replace(R.id.fragment_container, fragment).commit();
                        }
                        if (headerList.get(groupPosition).menuName.equals(logouTitle)) {
                            sp.edit().remove("token").apply();
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        }


                        onBackPressed();
                    }
                }
                return false;
            }
        });
        //Click listener for any child option
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Fragment fragment;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    String category = childList.get(headerList.get(groupPosition)).get(childPosition).menuName;
                    if (categories.getValue().contains(category)) {
                        FragmentManager fm = getSupportFragmentManager();
                        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }
                        fragment = new CategoryFragment().newInstance("Bearer " + token, category);
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.fragment_container, fragment).commit();
                    }

                    if (category.equals(changePasswordTitle)) {
                        fragment = new NewPasswordFragment();
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.fragment_container, fragment).commit();
                    }
                    if (category.equals(logouTitle)) {
                        sp.edit().remove("token").apply();
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                    onBackPressed();
                }
                return false;
            }
        });
    }

    public void setMenuTitles() {
        newsTitle = getResources().getString(R.string.news);
        gamesTitle = getResources().getString(R.string.games);
        settingsTitle = getResources().getString(R.string.settings);
        favoritesTitle = getResources().getString(R.string.favorites);
        logouTitle = getResources().getString(R.string.logout);
        changePasswordTitle = getString(R.string.change_password);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Read values from the "savedInstanceState"
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the values you need into "outState"
        // outState.putSerializable("fragment",fragment);
        super.onSaveInstanceState(outState);
    }


}
