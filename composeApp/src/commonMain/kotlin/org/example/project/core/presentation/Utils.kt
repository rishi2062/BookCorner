package org.example.project.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController
): T {

    // here confusion may arrive , maybe for a instance ,how string and a viewmodel right,
    // it is like "If we can’t get the route (String), then just return the ViewModel and skip everything below."
    println("SelectedBook : NavBackStackEntry.sharedViewModel ${destination.parent?.route}")
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )


    /* for clarity, this works as ,
    val parentRoute = destination.parent?.route
    if (parentRoute == null) {
        return koinViewModel<T>() // fallback
    }
    val parentEntry = remember(this) {
        navController.getBackStackEntry(parentRoute)
    }
    return koinViewModel(viewModelStoreOwner = parentEntry)

     */
}