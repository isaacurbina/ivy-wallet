package com.ivy.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ivy.common.time.provider.TimeProvider
import com.ivy.data.CurrencyCode
import com.ivy.navigation.DestinationRoute
import com.ivy.navigation.Navigator
import com.ivy.navigation.destinations.main.Home
import kotlinx.coroutines.runBlocking

class HomeScreenRobot(
    private val composeRule: IvyComposeRule
) {
    // region tests
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
        composeRule.onNodeWithText(transactionTitle).assertIsDisplayed()
        return this
    }

    fun assertTransactionIsDisplayed(
        transactionTitle: String,
        accountName: String,
        categoryName: String
    ): HomeScreenRobot {
        composeRule.onNodeWithText(transactionTitle).assertIsDisplayed()
        composeRule.onNodeWithText(accountName).assertIsDisplayed()
        composeRule.onNodeWithText(categoryName).assertIsDisplayed()
        return this
    }

    fun openOverdue(): HomeScreenRobot {
        return clickButton("Overdue")
    }

    fun clickGet(): HomeScreenRobot {
        return clickButton("Get")
    }

    fun assertBalanceIsDisplayed(amount: Double, currency: CurrencyCode): HomeScreenRobot {
        val formattedAmount = if (amount % 1.0 == 0.0) {
            amount.toInt().toString()
        } else amount.toString()

        composeRule
            .onAllNodes(
                matcher = hasText(formattedAmount) and hasAnySibling(hasText(currency)),
                useUnmergedTree = true
            )
            .onFirst()
            .assertIsDisplayed()
        return this
    }

    fun clickNewTransaction(): HomeScreenRobot {
        composeRule.onNodeWithContentDescription("Add new transaction").performClick()
        return this
    }

    fun clickExpense(): HomeScreenRobot {
        composeRule.onNodeWithContentDescription("Create new expense").performClick()
        return this
    }

    fun assertTotalExpensesIs(amount: Int): HomeScreenRobot {
        composeRule
            .onAllNodesWithTag("amount", useUnmergedTree = true)
            .onLast()
            .assertTextEquals(amount.toString())
        return this
    }
    // endregion

    // region private functions
    private fun clickButton(label: String): HomeScreenRobot {
        composeRule
            .onNodeWithText(label)
            .performClick()
        return this
    }
    // endregion
}
