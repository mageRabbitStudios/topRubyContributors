package com.kinzlstanislav.topcontributors.viewtesting

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector

/**
 * The container activity for tests using FragmentDaggerTest
 */
class FragmentDaggerTestActivity : AppCompatActivity(), HasSupportFragmentInjector {

    lateinit var injector: AndroidInjector<Fragment>

    override fun supportFragmentInjector() = injector

    fun <T> setInjector(injector: (T) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        this.injector = AndroidInjector {
            injector(it as T)
        }
    }
}