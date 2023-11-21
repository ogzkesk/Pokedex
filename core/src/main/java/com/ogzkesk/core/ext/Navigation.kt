package com.ogzkesk.core.ext

import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.ogzkesk.core.R

data class NavArg<T>(val key:String,val value: T)

@JvmOverloads
fun NavController.navigateWithFade(@StringRes route: Int, arg: NavArg<*>? = null) {

    val navOptions = NavOptions.Builder()
        .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
        .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
        .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
        .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
        .build()

    if(arg != null){
        currentBackStackEntry?.savedStateHandle?.set(arg.key,arg.value)
    }

    navigate(context.getString(route), navOptions)
}

@JvmOverloads
fun NavController.navigateWithFade(
    @StringRes route: Int,
    @StringRes popupTo: Int,
    arg: NavArg<*>? = null
) {

    val navOptions = NavOptions.Builder()
        .setPopUpTo(context.getString(popupTo),true)
        .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
        .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
        .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
        .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
        .build()

    if(arg != null){
        currentBackStackEntry?.savedStateHandle?.set(arg.key,arg.value)
    }

    navigate(context.getString(route), navOptions)
}


fun <T> NavController.getArgument(key: String): T? {
    return previousBackStackEntry?.savedStateHandle?.get<T>(key)
}

@JvmOverloads
fun NavController.navigateWithSlide(@StringRes route: Int, arg: NavArg<*>? = null) {
    val navOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()

    if(arg != null){
        currentBackStackEntry?.savedStateHandle?.set(arg.key,arg.value)
    }

    navigate(context.getString(route), navOptions)
}

@JvmOverloads
fun NavController.navigateWithScale(@StringRes route: Int, arg: NavArg<*>? = null) {
    val navOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.scale_transition_enter)
        .setExitAnim(R.anim.scale_transition_exit)
        .setPopExitAnim(R.anim.scale_transition_exit)
        .build()

    if(arg != null){
        currentBackStackEntry?.savedStateHandle?.set(arg.key,arg.value)
    }

    navigate(context.getString(route), navOptions)
}