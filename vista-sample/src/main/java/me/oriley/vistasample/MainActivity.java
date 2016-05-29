package me.oriley.vistasample;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String KEY_CURRENT_ITEM = "currentItem";
    private static final int DEFAULT_ITEM = R.id.nav_recycler_view;

    @NonNull
    private FragmentManager mFragmentManager;

    @SuppressWarnings("FieldCanBeLocal")
    @NonNull
    private NavigationView mNavigationView;

    @NonNull
    private DrawerLayout mDrawerLayout;

    @NonNull
    private ActionBarDrawerToggle mToggle;

    @IdRes
    private int mCurrentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentManager = getSupportFragmentManager();

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (drawerLayout == null || navigationView == null) {
            throw new IllegalStateException("Required views not found");
        }

        mDrawerLayout = drawerLayout;
        mToggle = new NoSpinDrawerToggle(this, mDrawerLayout, toolbar);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mNavigationView = navigationView;
        mNavigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState == null) {
            onNavigationItemSelected(DEFAULT_ITEM);
        } else {
            mCurrentItem = savedInstanceState.getInt(KEY_CURRENT_ITEM, DEFAULT_ITEM);
            mNavigationView.setCheckedItem(mCurrentItem);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_CURRENT_ITEM, mCurrentItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return onNavigationItemSelected(item.getItemId());
    }

    private boolean onNavigationItemSelected(int id) {
        // Handle navigation view item clicks here.
        switch (id) {
            case R.id.nav_view_pager:
                openDrawerFragment(ViewPagerFragment.class, id);
                break;
            case R.id.nav_recycler_view:
                openDrawerFragment(RecyclerViewFragment.class, id);
                break;
            case R.id.nav_nested_scroll_view:
                openDrawerFragment(NestedScrollViewFragment.class, id);
                break;
            case R.id.nav_list_view:
                openDrawerFragment(ListViewFragment.class, id);
                break;
            case R.id.nav_grid_view:
                openDrawerFragment(GridViewFragment.class, id);
                break;
            case R.id.nav_horizontal_scroll_view:
                openDrawerFragment(HorizontalScrollViewFragment.class, id);
                break;
            case R.id.nav_scroll_view:
                openDrawerFragment(ScrollViewFragment.class, id);
                break;
            case R.id.nav_custom_values:
                openDrawerFragment(CustomValuesFragment.class, id);
                break;
        }

        closeDrawer();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDrawerLayout.removeDrawerListener(mToggle);
    }

    private void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }

    private void openDrawerFragment(@NonNull final Class<? extends Fragment> fragmentClass,
                                    @IdRes int menuId) {
        Fragment fragment = getCurrentFragment();
        if (fragment == null || !fragmentClass.isInstance(fragment)) {
            showFragment(Fragment.instantiate(this, fragmentClass.getName()));
        } else {
            closeDrawer();
        }

        mCurrentItem = menuId;
        mNavigationView.setCheckedItem(menuId);
    }

    @Nullable
    private Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentById(R.id.fragment);
    }

    private void showFragment(@NonNull Fragment fragment) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.fragment, fragment, defaultTag(fragment.getClass()));
        ft.commit();
    }

    @NonNull
    private static String defaultTag(@NonNull Class<? extends Fragment> fragmentClass) {
        return fragmentClass.getName();
    }
}
