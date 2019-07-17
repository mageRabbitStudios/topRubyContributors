package com.kinzlstanislav.topcontributors.viewtesting

import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kinzlstanislav.topcontributors.viewtesting.helpers.InstrumentationTestsHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
abstract class FragmentKoinTest<FRAGMENT : Fragment> {

    private companion object {
        val TEST_ENVIRONMENT_THEME = R.style.Theme_AppCompat
    }

    private lateinit var fragmentScenario: FragmentScenario<Fragment>

    private val targetContext: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    abstract val fragmentInstance: FRAGMENT

    @Suppress("UNCHECKED_CAST")
    protected val subject: FRAGMENT
        get() {
            var fragment: Fragment? = null
            fragmentScenario.onFragment {
                fragment = it
            }
            return fragment as FRAGMENT
        }

    @Mock
    protected lateinit var mockNavController: NavController

    @CallSuper
    @Before
    open fun setup() {
        Intents.init()
        InstrumentationRegistry.getInstrumentation().targetContext
            .applicationContext.setTheme(TEST_ENVIRONMENT_THEME)
        MockitoAnnotations.initMocks(this)
    }

    @CallSuper
    @After
    open fun tearDown() {
        Intents.release()
    }

    protected fun getResString(@StringRes id: Int, args: Any? = null): String
            = targetContext.resources.getString(id, args)

    protected fun getResInteger(@IntegerRes id: Int) = targetContext.resources.getInteger(id)

    protected fun getResColor(@ColorRes id: Int) = ContextCompat.getColor(targetContext, id)

    protected fun getResDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(targetContext, id)

    protected fun launchFragment() {
        fragmentScenario = launchFragmentInContainer {
            fragmentInstance as Fragment
        }

        subject.view?.let { view ->
            Navigation.setViewNavController(view, mockNavController)
        }
    }

    /**
     * Great for asserting whenever your view is in the hierarchy or not
     * */
    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    protected fun printViewHierarchy() {
        InstrumentationTestsHelper.printViewHierarchy(subject.requireActivity())
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
        val activity = subject.requireActivity()
        activity.let {
            it.findViewById<View>(id)?.let { view ->
                return view
            } ?: run {
                fail("View is null and so no visibility check can take place on it")
            }
        }
        return null
    }

    protected fun mockKoinForFragment(mocks: Module.() -> Unit) {
        startKoin {
            modules(
                module {
                    mocks()
                }
            )
        }
    }
}