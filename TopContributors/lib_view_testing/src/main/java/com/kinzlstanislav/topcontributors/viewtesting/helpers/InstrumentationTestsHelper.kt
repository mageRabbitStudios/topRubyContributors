package com.kinzlstanislav.topcontributors.viewtesting.helpers

import android.R
import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.util.Pair
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import java.util.*

object InstrumentationTestsHelper {

    /** Prints into log activity's view hierarchy.
     *
     *  Example:
     *
    └── ContentFrameLayout id=16908290 / content
        └── ConstraintLayout id=2131296282 / activity_home_content
            └── BottomNavigationView id=2131296369 / navigation
            └── BottomNavigationMenuView id=-1
            ├── BottomNavigationItemView id=2131296370 / navigation_events
                ├── AppCompatImageView id=2131296344 / icon
                └── BaselineLayout id=-1
                    ├── AppCompatTextView id=2131296420 / smallLabel
                    └── AppCompatTextView id=2131296354 / largeLabel
     * */
    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    @NonNull
    fun printViewHierarchy(@NonNull activity: Activity) {
        printViewHierarchy(activity.findViewById<View>(R.id.content))
    }

    /** Print into log view hierarchy.  */
    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    @NonNull
    fun printViewHierarchy(@NonNull root: View) {
        val output = StringBuilder(8192).append("\n")
        val r = root.resources
        val stack = Stack<Pair<String, View>>()
        stack.push(Pair.create("", root))

        while (!stack.empty()) {
            val pairedStack: Pair<String, View>? = stack.pop()
            val v = pairedStack!!.second

            val isLastOnLevel = stack.empty() || pairedStack.first != stack.peek().first
            val graphics = "" + pairedStack.first + if (isLastOnLevel) "└── " else "├── "

            val className = v.javaClass.simpleName
            val line = graphics + className + " id=" + v.id + resolveIdToName(r, v)

            output.append(line).append("\n")

            if (v is ViewGroup) {
                for (i in v.childCount - 1 downTo 0) {
                    stack.push(Pair.create(pairedStack.first + if (isLastOnLevel) "    " +
                            "" else "│   ", v.getChildAt(i)))
                }
            }
        }
        println(output)
    }

    @NonNull
    internal fun resolveIdToName(@Nullable r: Resources?, @NonNull v: View): String {
        if (null == r) return ""

        return try {
            " / " + r.getResourceEntryName(v.id)
        } catch (ignored: Throwable) {
            ""
        }
    }
}