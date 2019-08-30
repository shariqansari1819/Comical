package com.codebosses.comical.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codebosses.comical.R;
import com.codebosses.comical.databinding.ActivityMainBinding;
import com.codebosses.comical.fragments.FragmentHome;
import com.codebosses.comical.fragments.FragmentProfile;
import com.codebosses.comical.fragments.FragmentSearch;
import com.codebosses.comical.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static com.codebosses.comical.common.Constants.TAB_HOME;
import static com.codebosses.comical.common.Constants.TAB_PROFILE;
import static com.codebosses.comical.common.Constants.TAB_SEARCH;
import static com.codebosses.comical.custom.backstack.FragmentUtils.addAdditionalTabFragment;
import static com.codebosses.comical.custom.backstack.FragmentUtils.addInitialTabFragment;
import static com.codebosses.comical.custom.backstack.FragmentUtils.addShowHideFragment;
import static com.codebosses.comical.custom.backstack.FragmentUtils.removeFragment;
import static com.codebosses.comical.custom.backstack.FragmentUtils.showHideTabFragment;
import static com.codebosses.comical.custom.backstack.StackListManager.updateStackIndex;
import static com.codebosses.comical.custom.backstack.StackListManager.updateStackToIndexFirst;
import static com.codebosses.comical.custom.backstack.StackListManager.updateTabStackIndex;

public class MainActivity extends AppCompatActivity implements BaseFragment.FragmentInteractionCallback {

    //    Android fields....
    ActivityMainBinding mainBinding;
    private ImageView[] imageViews;
    private Toolbar toolbar;
    private TextView textViewToolbarTitle;

    //    Fragment fields....
    private FragmentHome fragmentHome;
    private FragmentSearch fragmentSearch;
    private FragmentProfile fragmentProfile;
    private Fragment currentFragment;

    //    TODO: Fragment manager fields....
    FragmentManager fragmentManager;

    //    TODO: Instance fields....
    private int index, currentFragmentIndex;

    //    TODO: Fragment stack fields....
    private Map<String, Stack<Fragment>> stacks;
    private String currentTab;
    private List<String> stackList;
    private List<String> menuStacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//        Setting custom action bar....
        setCustomActionBar();

//        Fragment manager fields initialization....
        fragmentManager = getSupportFragmentManager();
        initializeFragments();

        createStacks();
    }

    //    Method to set custom action bar....
    private void setCustomActionBar() {
        toolbar = findViewById(R.id.appBarMain);
        textViewToolbarTitle = toolbar.findViewById(R.id.textViewToolbarTitleMain);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    //    TODO: Method initialize fragments
    private void initializeFragments() {
        fragmentHome = FragmentHome.getInstance();
        fragmentSearch = FragmentSearch.getInstance();
        fragmentProfile = FragmentProfile.getInstance();
    }

    private void createStacks() {

        imageViews = new ImageView[]{mainBinding.layoutBottomNavMain.imageViewBottomNavHome, mainBinding.layoutBottomNavMain.imageViewBottomNavSearch, mainBinding.layoutBottomNavMain.imageViewBottomNavProfile};

        stacks = new LinkedHashMap<>();
        stacks.put(TAB_HOME, new Stack<Fragment>());
        stacks.put(TAB_SEARCH, new Stack<Fragment>());
        stacks.put(TAB_PROFILE, new Stack<Fragment>());

        menuStacks = new ArrayList<>();
        menuStacks.add(TAB_HOME);

        stackList = new ArrayList<>();
        stackList.add(TAB_HOME);
        stackList.add(TAB_SEARCH);
        stackList.add(TAB_PROFILE);

        imageViews[0].setSelected(true);
        setAppBarTitle(getResources().getString(R.string.comics));
        selectedTab(TAB_HOME);
    }

    public void onBottomNavClick(View view) {
        switch (view.getId()) {
            case R.id.bottomNavItemHome:
                index = 0;
                setAppBarTitle(getResources().getString(R.string.comics));
                selectedTab(TAB_HOME);
                break;
            case R.id.bottomNavItemSearch:
                index = 1;
                setAppBarTitle(getResources().getString(R.string.search));
                selectedTab(TAB_SEARCH);
                break;
            case R.id.bottomNavItemProfile:
                index = 2;
                setAppBarTitle(getResources().getString(R.string.profile));
                selectedTab(TAB_PROFILE);
                break;
        }
        imageViews[currentFragmentIndex].setSelected(false);
        imageViews[index].setSelected(true);
        currentFragmentIndex = index;
    }

    private void selectedTab(String tabId) {

        currentTab = tabId;
        BaseFragment.setCurrentTab(currentTab);

        if (stacks.get(tabId).size() == 0) {
            /*
             * First time this tab is selected. So add first fragment of that tab.
             * We are adding a new fragment which is not present in stack. So add to stack is true.
             */
            switch (tabId) {
                case TAB_HOME:
                    addInitialTabFragment(getSupportFragmentManager(), stacks, TAB_HOME, fragmentHome, R.id.frameLayoutMainContainer, true);
                    resolveStackLists(tabId);
                    assignCurrentFragment(fragmentHome);
                    break;
                case TAB_SEARCH:
                    addAdditionalTabFragment(getSupportFragmentManager(), stacks, TAB_SEARCH, fragmentSearch, currentFragment, R.id.frameLayoutMainContainer, true);
                    resolveStackLists(tabId);
                    assignCurrentFragment(fragmentSearch);
                    break;
                case TAB_PROFILE:
                    addAdditionalTabFragment(getSupportFragmentManager(), stacks, TAB_PROFILE, fragmentProfile, currentFragment, R.id.frameLayoutMainContainer, true);
                    resolveStackLists(tabId);
                    assignCurrentFragment(fragmentProfile);
                    break;
            }
        } else {
            /*
             * We are switching tabs, and target tab already has at least one fragment.
             * Show the target fragment
             */
            showHideTabFragment(getSupportFragmentManager(), stacks.get(tabId).lastElement(), currentFragment);
            resolveStackLists(tabId);
            assignCurrentFragment(stacks.get(tabId).lastElement());
        }
    }

    private void popFragment() {
        /*
         * Select the second last fragment in current tab's stack,
         * which will be shown after the fragment transaction given below
         */
        Fragment fragment = stacks.get(currentTab).elementAt(stacks.get(currentTab).size() - 4);

        /*pop current fragment from stack */
        stacks.get(currentTab).pop();

        removeFragment(getSupportFragmentManager(), fragment, currentFragment);

        assignCurrentFragment(fragment);
    }

    private void resolveBackPressed() {
        int stackValue = 0;
        if (stacks.get(currentTab).size() == 1) {
            Stack<Fragment> value = stacks.get(stackList.get(1));
            if (value.size() > 1) {
                stackValue = value.size();
                popAndNavigateToPreviousMenu();
            }
            if (stackValue <= 1) {
                if (menuStacks.size() > 1) {
                    navigateToPreviousMenu();
                } else {
                    finish();
                }
            }
        } else {
            popFragment();
        }
    }

    /*Pops the last fragment inside particular tab and goes to the second tab in the stack*/
    private void popAndNavigateToPreviousMenu() {
        String tempCurrent = stackList.get(0);
        currentTab = stackList.get(1);
        BaseFragment.setCurrentTab(currentTab);
        imageViews[resolveTabPositions(currentTab)].setSelected(true);
        showHideTabFragment(getSupportFragmentManager(), stacks.get(currentTab).lastElement(), currentFragment);
        assignCurrentFragment(stacks.get(currentTab).lastElement());
        updateStackToIndexFirst(stackList, tempCurrent);
        menuStacks.remove(0);
    }

    private void navigateToPreviousMenu() {
        menuStacks.remove(0);
        currentTab = menuStacks.get(0);
        BaseFragment.setCurrentTab(currentTab);
        imageViews[resolveTabPositions(currentTab)].setSelected(true);
        showHideTabFragment(getSupportFragmentManager(), stacks.get(currentTab).lastElement(), currentFragment);
        assignCurrentFragment(stacks.get(currentTab).lastElement());
    }

    private int resolveTabPositions(String currentTab) {
        switch (currentTab) {
            case TAB_HOME:
                index = 0;
                setAppBarTitle(getResources().getString(R.string.comics));
                break;
            case TAB_SEARCH:
                index = 1;
                setAppBarTitle(getResources().getString(R.string.search));
                break;
            case TAB_PROFILE:
                index = 2;
                setAppBarTitle(getResources().getString(R.string.profile));
                break;
        }
        imageViews[currentFragmentIndex].setSelected(false);
        currentFragmentIndex = index;
        return index;
    }

    private void resolveStackLists(String tabId) {
        updateStackIndex(stackList, tabId);
        updateTabStackIndex(menuStacks, tabId);
    }

    private Fragment getCurrentFragmentFromShownStack() {
        return stacks.get(currentTab).elementAt(stacks.get(currentTab).size() - 1);
    }

    private void assignCurrentFragment(Fragment current) {
        currentFragment = current;
    }

    @Override
    public void onBackPressed() {
        resolveBackPressed();
    }

    @Override
    public void onFragmentInteractionCallback(Bundle bundle) {

    }

    //    TODO: Method to set app bar title....
    private void setAppBarTitle(String text) {
        textViewToolbarTitle.setText(text);
    }

}
