package com.ivy.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ivy.common.time.provider.TimeProvider
import com.ivy.navigation.DestinationRoute
import com.ivy.navigation.Navigator
import com.ivy.navigation.destinations.main.Home
import kotlinx.coroutines.runBlocking

class HomeScreenRobot(
    private val composeRule: IvyComposeRule
) {
    fun navigateTo(
        navigator: Navigator,
        route: DestinationRoute = Home.route
    ): HomeScreenRobot {
        runBlocking {
            composeRule.awaitIdle()
            composeRule.runOnUiThread {
                navigator.navigate(route) {
                    popUpTo(route) {
                        inclusive = false
                    }
                }
            }
        }
        return this
    }

    fun openDateRangeSheet(timeProvider: TimeProvider): HomeScreenRobot {
        composeRule
            .onNodeWithText(timeProvider.dateNow().month.name, ignoreCase = true)
            .performClick()
        return this
    }

    fun selectMonth(monthName: String): HomeScreenRobot {
        composeRule
            .onNodeWithText(monthName)
            .assertIsDisplayed()
            .performClick()
        return this
    }

    fun assertDateIsDisplayed(day: Int, month: String): HomeScreenRobot {
        val paddedDay = day.toString().padStart(2, '0')
        composeRule
            .onNodeWithText("${month.take(3)}. $paddedDay")
            .assertIsDisplayed()
        return this
    }

    fun clickDone(): HomeScreenRobot {
        return clickButton("Done")
    }

    fun clickUpcoming(): HomeScreenRobot {
        return clickButton("Upcoming")
    }

    fun assertTransactionDoesNotExist(transactionTitle: String): HomeScreenRobot {
        composeRule
            .onNodeWithText(transactionTitle)
            .assertDoesNotExist()
        return this
    }

    fun assertTransactionIsDisplayed(transactionTitle: String): HomeScreenRobot {
        composeRule
            .onNodeWithText(transactionTitle)
            .assertIsDisplayed()
        return this
    }

    private fun clickButton(label: String): HomeScreenRobot {
        composeRule
            .onNodeWithText(label)
            .performClick()
        return this
    }
}
