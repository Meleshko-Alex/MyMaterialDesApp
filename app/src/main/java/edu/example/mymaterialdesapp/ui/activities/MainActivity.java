package edu.example.mymaterialdesapp.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.example.mymaterialdesapp.R;
import edu.example.mymaterialdesapp.data.managers.DataManager;
import edu.example.mymaterialdesapp.utils.ConstantManager;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_coordinator_container) CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.navigation_drawer) DrawerLayout mNavigationDrawer;
    @BindView(R.id.fab) FloatingActionButton mFab;
    @BindView(R.id.et_phone) EditText mUserPhone;
    @BindView(R.id.et_email) EditText mUserEmail;
    @BindView(R.id.et_vc_account) EditText mUserVc;
    @BindView(R.id.et_github_account) EditText mUserGit;
    @BindView(R.id.et_about_me) EditText mUserBio;
    @BindViews({R.id.et_phone, R.id.et_email, R.id.et_vc_account, R.id.et_github_account, R.id.et_about_me}) List<EditText> mUserInfo;
    private int mCurrentEditMode = 0;
    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mDataManager = DataManager.getInstance();

        setupToolbar();
        setupDrawer();
        loadUserInfoValue();


        if(savedInstanceState == null){
        } else {
            mCurrentEditMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY, 0);
            changeEditMode(mCurrentEditMode);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentEditMode);
    }

    private void showSnackbar (String massage){
        Snackbar.make(mCoordinatorLayout, massage, Snackbar.LENGTH_LONG).show();
    }

    private void setupToolbar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer(){
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            mNavigationDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onClick(){
        if(mCurrentEditMode == 0){
            changeEditMode(1);
            mCurrentEditMode = 1;
        } else {
            changeEditMode(0);
            mCurrentEditMode = 0;
        }
    }

    /**
     * переключение режимов редактирования
     * @param mode если 1 - режим редактирования, 0 - ежим просмотра
     */
    private void changeEditMode(int mode){
        if(mode == 1){
            mFab.setImageResource(R.drawable.ic_check_black_24dp);
            for(EditText userValue : mUserInfo){
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
            }
        } else {
            mFab.setImageResource(R.drawable.ic_create_black_24dp);
            for(EditText userValue : mUserInfo){
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);
                saveUserInfoValue();
            }
        }
    }

    private void loadUserInfoValue(){
        List<String> userData = mDataManager.getPreferenceManager().loadUserProfileData();
        for(int i = 0; i < userData.size(); i++){
            mUserInfo.get(i).setText(userData.get(i));
        }
    }

    private void saveUserInfoValue(){
        List<String> userData = new ArrayList<>();
        for(EditText userFields : mUserInfo){
            userData.add(userFields.getText().toString());
        }
        mDataManager.getPreferenceManager().saveUserProfileData(userData);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveUserInfoValue();
    }
}
