package edu.example.mymaterialdesapp.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.example.mymaterialdesapp.R;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_coordinator_container) CoordinatorLayout mCoordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(savedInstanceState == null){
            showSnackbar("активити запустилась впервые");
        } else {
            showSnackbar("повторный запуск активити");
        }
    }

    private void showSnackbar (String massage){
        Snackbar.make(mCoordinatorLayout, massage, Snackbar.LENGTH_LONG).show();
    }
}
