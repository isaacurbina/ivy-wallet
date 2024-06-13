package com.ivy.core.domain.algorithm.calc

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNullOrEmpty
import com.ivy.core.persistence.algorithm.calc.CalcTrn
import com.ivy.data.transaction.TransactionType
import org.junit.jupiter.api.Test
import java.time.Instant

internal class RawStatsKtTest {

    @Test
    fun `Empty transaction list returns zero count and totals`() {
        val transactions = emptyList<CalcTrn>()

        val result = rawStats(transactions)

        assertThat(result.incomes).isNullOrEmpty()
        assertThat(result.incomesCount).isEqualTo(0)
        assertThat(result.expenses).isNullOrEmpty()
        assertThat(result.expensesCount).isEqualTo(0)
    }

    @Test
    fun `Simple transactions verify count and amount`() {
        val transactions = listOf(
            CalcTrn(
                amount = 100.0,
                currency = CURRENCY_USD,
                type = TransactionType.Expense,
                time = Instant.now()
            ),
            CalcTrn(
                amount = 25.0,
                currency = CURRENCY_USD,
                type = TransactionType.Expense,
                time = Instant.now()
            ),
            CalcTrn(
                amount = 50.0,
                currency = CURRENCY_USD,
                type = TransactionType.Income,
                time = Instant.now()
            )
        )

        val result = rawStats(transactions)

        assertThat(result.incomes.size).isEqualTo(1)
        assertThat(result.incomesCount).isEqualTo(1)
        assertThat(result.incomes[CURRENCY_USD]).isEqualTo(50.0)
        assertThat(result.expenses.size).isEqualTo(1)
        assertThat(result.expensesCount).isEqualTo(2)
        assertThat(result.expenses[CURRENCY_USD]).isEqualTo(125.0)
    }

    @Test
    fun `Simple transactions verify count and amount multiple currencies`() {
        val transactions = listOf(
            CalcTrn(
                amount = 100.0,
                currency = CURRENCY_EUR,
                type = TransactionType.Expense,
                time = Instant.now()
            ),
            CalcTrn(
                amount = 25.0,
                currency = CURRENCY_USD,
                type = TransactionType.Expense,
                time = Instant.now()
            ),
            CalcTrn(
                amount = 50.0,
                currency = CURRENCY_USD,
                type = TransactionType.Income,
                time = Instant.now()
            )
        )

        val result = rawStats(transactions)

        assertThat(result.incomes.size).isEqualTo(1)
        assertThat(result.incomesCount).isEqualTo(1)
        assertThat(result.incomes[CURRENCY_USD]).isEqualTo(50.0)
        assertThat(result.expenses.size).isEqualTo(2)
        assertThat(result.expensesCount).isEqualTo(2)
        assertThat(result.expenses[CURRENCY_USD]).isEqualTo(25.0)
        assertThat(result.expenses[CURRENCY_EUR]).isEqualTo(100.0)
    }

    companion object {
        private const val CURRENCY_USD = "USD"
        private const val CURRENCY_EUR = "EUR"
    }
}
