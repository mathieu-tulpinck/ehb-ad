package com.saxomoose.frontend

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saxomoose.frontend.ui.auth.register.RegisterFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterFragmentInstrumentedTest {

    @Test
    fun register() {
        val registerScenario = launchFragmentInContainer<RegisterFragment>()
        onView(withId(R.id.name)).perform(typeText("Mathieu Tulpinck"))
        onView(withId(R.id.username)).perform(typeText("admin@demo.backend.test"))
        onView(withId(R.id.password)).perform(typeText("f!g<3YP<"))
        onView(withId(R.id.register_button)).perform(click())

    }
}