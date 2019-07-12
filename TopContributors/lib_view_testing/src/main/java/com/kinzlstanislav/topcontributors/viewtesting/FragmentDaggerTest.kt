package com.kinzlstanislav.topcontributors.viewtesting

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kinzlstanislav.topcontributors.viewtesting.helpers.InstrumentationTestsHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by Stanislav Kinzl, for easy fragment unit testing
 * experience while using Dagger 2 dependency injection framework.
 * 18th April 2019
 *
 * To use:
 *
 * 1. Add "android.enableUnitTestBinaryResources=true" in gradle.properties
 *
 * 2. Add the following into your gradle from module where the tests are running.
 *     testOptions {
 *        unitTests {
 *          includeAndroidResources = true
 *          returnDefaultValues = true
 *      }
 *    }
 *    plus testImplementation for the newest robolectric (tested on version 4.1).
 *
 * 3. Set TEST_ENVIRONMENT_THEME based on what is your app's theme parent theme.
 *    If it uses Theme_AppCompat then you should be fine.
 *
 * 4. Write tests, launch fragments with overridden injectors. Example of one:
 *     injector = {
 *        it.yourViewModel = mock(YourViewModel::class.java)
 *        given(it.yourViewModel.state).willReturn(subjectState)
 *     }
 *     and then launch fragment with: launchFragment(subject, injector)
 * */
@RunWith(AndroidJUnit4::class)
abstract class FragmentDaggerTest<FRAGMENT : Fragment> {

    private companion object {
        val TEST_ENVIRONMENT_THEME = R.style.Theme_AppCompat
    }

    private lateinit var activityScenario: ActivityScenario<FragmentDaggerTestActivity>

    private val targetContext: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    abstract var injector: (FRAGMENT) -> Unit

    abstract val subject: FRAGMENT

    @Mock
    protected lateinit var mockNavController: NavController

    @CallSuper
    @Before
    open fun setup() {
        Intents.init()
        InstrumentationRegistry.getInstrumentation().targetContext
                .applicationContext.setTheme(TEST_ENVIRONMENT_THEME)
        MockitoAnnotations.initMocks(this)
        launchFragment()
    }

    @CallSuper
    @After
    open fun tearDown() {
        Intents.release()
    }

    protected fun getResString(@StringRes id: Int, args: Any? = null): String {
        return targetContext.resources.getString(id, args)
    }

    protected fun getResInteger(@IntegerRes id: Int): Int {
        return targetContext.resources.getInteger(id)
    }

    protected fun getResColor(@ColorRes id: Int): Int {
        return targetContext.resources.getColor(id)
    }

    protected fun getResDrawable(@DrawableRes id: Int): Drawable {
        return targetContext.resources.getDrawable(id)
    }

    protected fun getActivity(): Activity? {
        var activity: Activity? = null
        activityScenario.onActivity { activity = it }
        return activity
    }

    protected fun launchFragment() {
        activityScenario = launch(FragmentDaggerTestActivity::class.java).also {
            it.onActivity { activity ->
                (activity as FragmentDaggerTestActivity).apply {
                    // First add injector to our container activity
                    setInjector(this@FragmentDaggerTest.injector)
                    // Launch the fragment
                    supportFragmentManager.beginTransaction()
                            .add(android.R.id.content, subject, "TAG")
                            .commit()
                }

                // Set mockNavController after fragment added and view is not null

                subject.view?.let { view -> Navigation.setViewNavController(view, mockNavController) }
                        ?: run { fail("Setting ") }
            }
        }
    }

    /**
     * Great for asserting whenever your view is in the hierarchy or not
     * */
    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    protected fun printViewHierarchy() {
        getActivity()?.let {
            InstrumentationTestsHelper.printViewHierarchy(it)
        } ?: run {
            fail("Activity is null, can't print view hiearchy")
        }
    }

    protected fun Int.isVisible() {
        getViewFromActivityById(this)?.let {
            assertThat(it.visibility).isEqualTo(View.VISIBLE)
        }
    }

    protected fun Int.isGone() {
        getViewFromActivityById(this)?.let {
            assertThat(it.visibility).isEqualTo(View.GONE)
        }
    }

    private fun getViewFromActivityById(id: Int): View? {
        val activity = getActivity()
        activity?.let {
            it.findViewById<View>(id)?.let { view ->
                return view
            } ?: run {
                fail("View is null and so no visibility check can take place on it")
            }
        } ?: run {
            fail("Activity is null and so no view can be found")
        }
        return null
    }

}