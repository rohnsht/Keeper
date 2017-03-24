package com.rohanshrestha.keeper;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rohanshrestha.keeper.adapter.PasswordAdapter;
import com.rohanshrestha.keeper.data.Credential;
import com.rohanshrestha.keeper.utils.DatabaseHandler;

import java.util.ArrayList;

public class SearchableActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private ArrayList<Credential> credentials;
    private PasswordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        credentials = new ArrayList<>();

        searchView = (SearchView) findViewById(R.id.search);
        searchView.onActionViewExpanded();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, new LinearLayoutManager(this).getOrientation()));
        //setUpItemTouchHelper();
        adapter = new PasswordAdapter(this, credentials);
        recyclerView.setAdapter(adapter);

    }

    /*@Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search

            doSearch(query);
        }
    }*/

    private void doSearch(String query) {
        //credentialses = db.searchCredentials(query);
        adapter.addAll(credentials);
    }

    /*private void setUpItemTouchHelper() {
        ItemTouchHelper.Callback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPosition = viewHolder.getAdapterPosition();
                Credential credential = adapter.getItem(swipedPosition);
                if (direction == ItemTouchHelper.RIGHT) {
                } else if (direction == ItemTouchHelper.LEFT) {

                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    if (dX > 0) { // right swipe
                        ((PasswordAdapter.MyViewHolder) viewHolder).setRightSwipeView();
                    } else if (dX < 0) {
                        ((PasswordAdapter.MyViewHolder) viewHolder).setLeftSwipeView();
                    }
                    getDefaultUIUtil().onDraw(c, recyclerView, itemView.findViewById(R.id.swipeable), dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                getDefaultUIUtil().clearView(viewHolder.itemView.findViewById(R.id.swipeable));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
