package com.saxomoose.frontend

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saxomoose.frontend.ui.home.MainActivity
import com.saxomoose.frontend.ui.home.catalogue.CatalogueFragment
import com.saxomoose.frontend.ui.home.transaction.TransactionFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TransactionFragmentInstrumentedTest {

    private var intent: Intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)

    init {
        intent.putExtra(MainActivity.USER_ID, 1)
    }

    @get:Rule
    val activity = ActivityScenarioRule<MainActivity>(intent)

    @Test
    fun addItemToTransaction() {
        // Create a TestNavHostController
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // Create a graphical FragmentScenario for the TitleScreen
        val fragmentArgs = bundleOf("eventId" to 1)
        val catalogueScenario = launchFragmentInContainer<CatalogueFragment>(fragmentArgs)

        catalogueScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
            onView(withId(R.id.button)).perform(click())

        }

        // Verify that performing a click changes the NavController’s state
//        onView(ViewMatchers.withId(R.id.play_btn)).perform(ViewActions.click())
//        assertThat(navController.currentDestination?.id).isEqualTo(R.id.in_game)


    }
}