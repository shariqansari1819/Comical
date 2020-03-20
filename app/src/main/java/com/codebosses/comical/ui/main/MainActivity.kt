package com.codebosses.comical.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.codebosses.comical.R
import com.codebosses.comical.common.Constants.BackStackConstants.TAB_HOME
import com.codebosses.comical.common.Constants.BackStackConstants.TAB_PROFILE
import com.codebosses.comical.common.Constants.BackStackConstants.TAB_SEARCH
import com.codebosses.comical.utils.backstack.FragmentUtils.*
import com.codebosses.comical.utils.backstack.StackListManager.*
import com.codebosses.comical.ui.BaseActivity
import com.codebosses.comical.ui.main.base.BaseFragment
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.extensions.startActivity
import com.codebosses.comical.ui.main.comic.FragmentComics
import com.codebosses.comical.ui.main.profile.FragmentProfile
import com.codebosses.comical.ui.main.search.FragmentSearch
import com.codebosses.comical.ui.registration.login.LoginActivity
import com.codebosses.comical.utils.extensions.startActivityNewTask
import com.facebook.login.LoginManager
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_bottom_nav.*
import java.util.*
import kotlin.collections.ArrayList
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class MainActivity : BaseActivity(), BaseFragment.FragmentInteractionCallback {

    lateinit var context: Context

    //    Android fields....
    private lateinit var imageViews: Array<ImageView>
    private lateinit var toolbar: Toolbar

    //    Fragment fields....
    private var fragmentHome: FragmentComics? = null
    private var fragmentSearch: FragmentSearch? = null
    private var fragmentProfile: FragmentProfile? = null
    private var currentFragment: Fragment? = null

    //    TODO: Fragment manager fields....
    private lateinit var fragmentManager: FragmentManager

    //    TODO: Instance fields....
    private var index: Int = 0
    private var currentFragmentIndex: Int = 0
    private lateinit var mGoogleApiClient: GoogleApiClient

    //    TODO: Fragment stack fields....
    private var stacks = LinkedHashMap<String, Stack<Fragment>>()
    private var currentTab: String = ""
    private var stackList = ArrayList<String>()
    private var menuStacks = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        context = this

//        Getting device token
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            val token = it.token
            PrefUtils.deviceToken = token
        }

//        Setting custom action bar....
        setCustomActionBar()

//        Fragment fields initialization....
        fragmentManager = supportFragmentManager
        initializeFragments()

        createStacks()
    }

    //    Method to set custom action bar....
    private fun setCustomActionBar() {
        toolbar = appBarMain as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
    }

    //    TODO: Method initialize fragments
    private fun initializeFragments() {
        fragmentHome = FragmentComics.getInstance()
        fragmentSearch = FragmentSearch.getInstance()
        fragmentProfile = FragmentProfile.getInstance()
    }

    private fun createStacks() {

        imageViews = arrayOf(
                imageViewBottomNavHome,
                imageViewBottomNavSearch,
                imageViewBottomNavProfile
        )

        stacks[TAB_HOME] = Stack<Fragment>()
        stacks[TAB_SEARCH] = Stack<Fragment>()
        stacks[TAB_PROFILE] = Stack<Fragment>()

        menuStacks = ArrayList()
        menuStacks.add(TAB_HOME)

        stackList = ArrayList()
        stackList.add(TAB_HOME)
        stackList.add(TAB_SEARCH)
        stackList.add(TAB_PROFILE)

        imageViews[0].isSelected = true
        setAppBarTitle(resources.getString(R.string.comics))
        selectedTab(TAB_HOME)
    }

    fun onBottomNavClick(view: View) {
        when (view.id) {
            R.id.bottomNavItemHome -> {
                index = 0
                setAppBarTitle(resources.getString(R.string.comics))
                selectedTab(TAB_HOME)
            }
            R.id.bottomNavItemSearch -> {
                index = 1
                setAppBarTitle(resources.getString(R.string.search))
                selectedTab(TAB_SEARCH)
            }
            R.id.bottomNavItemProfile -> {
                if (PrefUtils.isUserLoggedIn) {
                    index = 2
                    setAppBarTitle(resources.getString(R.string.profile))
                    selectedTab(TAB_PROFILE)
                } else {
                    startActivity(LoginActivity::class.java)
                }
            }
        }
        imageViews[currentFragmentIndex].isSelected = false
        imageViews[index].isSelected = true
        currentFragmentIndex = index
    }

    private fun selectedTab(tabId: String) {

        currentTab = tabId
        BaseFragment.setCurrentTab(currentTab)

        if (stacks[tabId]!!.size == 0) {
            /*
             * First time this tab is selected. So add first fragment of that tab.
             * We are adding a new fragment which is not present in stack. So add to stack is true.
             */
            when (tabId) {
                TAB_HOME -> {
                    addInitialTabFragment(
                            supportFragmentManager,
                            stacks,
                            TAB_HOME,
                            fragmentHome,
                            R.id.frameLayoutMainContainer,
                            true
                    )
                    resolveStackLists(tabId)
                    assignCurrentFragment(fragmentHome!!)
                }
                TAB_SEARCH -> {
                    addAdditionalTabFragment(
                            supportFragmentManager,
                            stacks,
                            TAB_SEARCH,
                            fragmentSearch,
                            currentFragment,
                            R.id.frameLayoutMainContainer,
                            true
                    )
                    resolveStackLists(tabId)
                    assignCurrentFragment(fragmentSearch!!)
                }
                TAB_PROFILE -> {
                    addAdditionalTabFragment(
                            supportFragmentManager,
                            stacks,
                            TAB_PROFILE,
                            fragmentProfile,
                            currentFragment,
                            R.id.frameLayoutMainContainer,
                            true
                    )
                    resolveStackLists(tabId)
                    assignCurrentFragment(fragmentProfile!!)
                }
            }
        } else {
            /*
             * We are switching tabs, and target tab already has at least one fragment.
             * Show the target fragment
             */
            showHideTabFragment(
                    supportFragmentManager,
                    stacks[tabId]!!.lastElement(),
                    currentFragment
            )
            resolveStackLists(tabId)
            assignCurrentFragment(stacks[tabId]!!.lastElement())
        }
    }

    private fun popFragment() {
        /*
         * Select the second last fragment in current tab's stack,
         * which will be shown after the fragment transaction given below
         */
        val fragment = stacks[currentTab]!!.elementAt(stacks[currentTab]!!.size - 4)

        /*pop current fragment from stack */
        stacks[currentTab]!!.pop()

        removeFragment(supportFragmentManager, fragment, currentFragment)

        assignCurrentFragment(fragment)
    }

    private fun resolveBackPressed() {
        var stackValue = 0
        if (stacks[currentTab]!!.size == 1) {
            val value = stacks[stackList[1]]
            if (value!!.size > 1) {
                stackValue = value.size
                popAndNavigateToPreviousMenu()
            }
            if (stackValue <= 1) {
                if (menuStacks.size > 1) {
                    navigateToPreviousMenu()
                } else {
                    finish()
                }
            }
        } else {
            popFragment()
        }
    }

    /*Pops the last fragment inside particular tab and goes to the second tab in the stack*/
    private fun popAndNavigateToPreviousMenu() {
        val tempCurrent = stackList[0]
        currentTab = stackList[1]
        BaseFragment.setCurrentTab(currentTab)
        imageViews[resolveTabPositions(currentTab)].isSelected = true
        showHideTabFragment(
                supportFragmentManager,
                stacks[currentTab]!!.lastElement(),
                currentFragment
        )
        assignCurrentFragment(stacks[currentTab]!!.lastElement())
        updateStackToIndexFirst(stackList, tempCurrent)
        menuStacks.removeAt(0)
    }

    private fun navigateToPreviousMenu() {
        menuStacks.removeAt(0)
        currentTab = menuStacks[0]
        BaseFragment.setCurrentTab(currentTab)
        imageViews[resolveTabPositions(currentTab)].isSelected = true
        showHideTabFragment(
                supportFragmentManager,
                stacks[currentTab]!!.lastElement(),
                currentFragment
        )
        assignCurrentFragment(stacks[currentTab]!!.lastElement())
    }

    private fun resolveTabPositions(currentTab: String): Int {
        when (currentTab) {
            TAB_HOME -> {
                index = 0
                setAppBarTitle(resources.getString(R.string.comics))
            }
            TAB_SEARCH -> {
                index = 1
                setAppBarTitle(resources.getString(R.string.search))
            }
            TAB_PROFILE -> {
                index = 2
                setAppBarTitle(resources.getString(R.string.profile))
            }
        }
        imageViews[currentFragmentIndex].isSelected = false
        currentFragmentIndex = index
        return index
    }

    private fun resolveStackLists(tabId: String) {
        updateStackIndex(stackList, tabId)
        updateTabStackIndex(menuStacks, tabId)
    }

    private fun getCurrentFragmentFromShownStack(): Fragment {
        return stacks[currentTab]!!.elementAt(stacks[currentTab]!!.size - 1)
    }

    private fun assignCurrentFragment(current: Fragment) {
        currentFragment = current
    }

    override fun onBackPressed() {
        resolveBackPressed()
    }

    //    TODO: Method to set app bar title....
    private fun setAppBarTitle(text: String) {
        textViewToolbarTitleMain.text = text
    }

    override fun onFragmentInteractionCallback(bundle: Bundle?) {

    }

    fun logOutUser() {
        PrefUtils.isUserLoggedIn = false
        PrefUtils.userId = 0
        PrefUtils.userName = ""
        PrefUtils.userEmail = ""
        PrefUtils.profileImageUrl = ""
        if (PrefUtils.isFacebookLogIn) {
            PrefUtils.isFacebookLogIn = false
            LoginManager.getInstance().logOut()
            startActivityNewTask(MainActivity::class.java)
        } else if (PrefUtils.isGoogleLogIn) {
            PrefUtils.isGoogleLogIn = false
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
                startActivityNewTask(MainActivity::class.java)
            }
        } else {
            startActivityNewTask(MainActivity::class.java)
        }
    }

    override fun onStart() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
        mGoogleApiClient.connect()
        super.onStart()
    }
}