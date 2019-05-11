package com.kinzlstanislav.topcontributors.viewtesting.matchers

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.assertj.core.api.Assertions.assertThat

/**Matchers that are not included with Barista*/

fun assertViewHolderOfItemAtPosition(
    recyclerView: RecyclerView, position: Int,
    type: Class<out RecyclerView.ViewHolder>) {
    assertThat(recyclerView.findViewHolderForAdapterPosition(position))
            .isExactlyInstanceOf(type)
}

fun assertToolbarTitle(title: String, toolbarId: Int) {
    Espresso.onView(ViewMatchers.withText(title))
            .check(ViewAssertions.matches(ViewMatchers.withParent(ViewMatchers.withId(toolbarId))))
}

// good for flipper checks since barista won't work for visibility checks on views inside flipper
fun ViewInteraction.isGone() = getViewAssertion(ViewMatchers.Visibility.GONE)

fun ViewInteraction.isVisible() = getViewAssertion(ViewMatchers.Visibility.VISIBLE)

fun ViewInteraction.isInvisible() = getViewAssertion(ViewMatchers.Visibility.INVISIBLE)

private fun getViewAssertion(visibility: ViewMatchers.Visibility): ViewAssertion? {
    return ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(visibility))
}