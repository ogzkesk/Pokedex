package com.ogzkesk.common.ext

import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.ogzkesk.core.R

data class NavArg<T>(val key: String, val value: T)

fun <T> NavController.getArgument(key: String): T? {
    return previousBackStackEntry?.savedStateHandle?.get<T>(key)
}

fun NavController.navigateWithSlide(@StringRes route: Int, arg: NavArg<*>? = null) {
    val navOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()

    if (arg != null) {
        currentBackStackEntry?.savedStateHandle?.set(arg.key, arg.value)
    }

    navigate(context.getString(route), navOptions)
}