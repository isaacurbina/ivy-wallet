package com.ivy.home

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.ivy.common.androidtest.IvyAndroidTest
import com.ivy.common.androidtest.test_data.saveAccountWithTransactions
import com.ivy.common.androidtest.test_data.transactionWithTime
import com.ivy.core.persistence.entity.trn.data.TrnTimeType
import com.ivy.data.transaction.TransactionType
import com.ivy.navigation.Navigator
import com.ivy.wallet.ui.RootActivity
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltAndroidTest
internal class HomeScreenKtTest : IvyAndroidTest() {

    val composeRule = createAndroidComposeRule<RootActivity>()

    @Inject
    lateinit var navigator: Navigator

    @Test
    fun testSelectingDateRange() = runBlocking<Unit> {
        val date = LocalDate.of(2023, 7, 23)
        setDate(date)

        val transaction1 = transactionWithTime(Instant.parse("2023-07-24T09:00:00Z"))
            .copy(title = "Transaction 1")
        val transaction2 = transactionWithTime(Instant.parse("2023-08-01T09:00:00Z"))
            .copy(title = "Transaction 2")
        val transaction3 = transactionWithTime(Instant.parse("2023-08-31T09:00:00Z"))
            .copy(title = "Transaction 3")

        db.saveAccountWithTransactions(
            transactions = listOf(transaction1, transaction2, transaction3)
        )

        HomeScreenRobot(composeRule)
            .navigateTo(navigator)
            .openDateRangeSheet(timeProvider)
            .selectMonth("August")
            .assertDateIsDisplayed(1, "August")
            .assertDateIsDisplayed(31, "August")
            .clickDone()
            .clickUpcoming()
            .assertTransactionDoesNotExist("Transaction 1")
            .assertTransactionIsDisplayed("Transaction 2")
            .assertTransactionIsDisplayed("Transaction 3")
    }

    @Test
    fun testGetOverdueTransaction_turnsIntoNormalTransaction() = runBlocking<Unit> {
        val date = LocalDate.of(2023, 7, 15)
        setDate(date)
        val dueTransaction = transactionWithTime(
            time = date
                .minusDays(1) // make due
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()
        ).copy(
            type = TransactionType.Income,
            timeType = TrnTimeType.Due,
            amount = 5.5
        )
        db.saveAccountWithTransactions(transactions = listOf(dueTransaction))

        HomeScreenRobot(composeRule)
            .navigateTo(navigator)
            .openOverdue()
            .clickGet()
            .assertTransactionIsDisplayed(dueTransaction.title!!)
            .assertBalanceIsDisplayed(dueTransaction.amount, dueTransaction.currency)
    }
}
