package com.joyfullkiwi.converterlab.Activities;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.joyfullkiwi.converterlab.Adapters.OrganizationsAdapter;
import com.joyfullkiwi.converterlab.Fragment.Catalog.CatalogPresenter;
import com.joyfullkiwi.converterlab.Model.InfoModel;
import com.joyfullkiwi.converterlab.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private Toolbar mToolbar;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mOrganizatiinsList;
    private OrganizationsAdapter mAdapter;

    private List<InfoModel> mData;

    private MenuItem searchItem;
    private ProgressDialog mProgressDialog;
    private SwipeRefreshLayout mSwipeLayout;

    private CatalogPresenter mCatalogPresenter;

    //public static

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_main);
        setSupportActionBar(mToolbar);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.notification_line));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout_activity_main);
        mSwipeLayout.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(this);
        mOrganizatiinsList = (RecyclerView) findViewById(R.id.recycler_view_activity_main);
        mOrganizatiinsList.setLayoutManager(mLayoutManager);
        mAdapter = new OrganizationsAdapter(this);
        mOrganizatiinsList.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {

    }
}
