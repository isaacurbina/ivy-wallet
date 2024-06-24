@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.ui.time.picker.date

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.ivy.common.androidtest.IvyAndroidTest
import com.ivy.common.androidtest.MainCoroutineRule
import com.ivy.core.ui.time.picker.date.DatePickerEvent
import com.ivy.core.ui.time.picker.date.DatePickerViewModel
import com.ivy.core.ui.time.picker.date.data.PickerDay
import com.ivy.core.ui.time.picker.date.data.PickerMonth
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@HiltAndroidTest
internal class DatePickerViewModelTest : IvyAndroidTest() {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: DatePickerViewModel

    override fun setUp() {
        super.setUp()
        viewModel = DatePickerViewModel(
            appContext = context,
            timeProvider = timeProvider
        )
    }

    @Test
    fun testSelectingDate() = runTest {
        // making sure the test runs with a month different than February
        setDate(LocalDate.of(2023, 1, 1))
        viewModel.uiState.test {
            awaitItem() // skip initial emission

            viewModel.onEvent(DatePickerEvent.DayChange(PickerDay("30", 30)))
            awaitItem() // skip day emission

            viewModel.onEvent(DatePickerEvent.MonthChange(PickerMonth("Feb", 2)))

            val finalEmission = awaitItem()

            val timeProviderDate = timeProvider.dateNow()
            assertThat(finalEmission.selected)
                .isEqualTo(
                    LocalDate.of(timeProviderDate.year, 2, 28)
                )
        }
    }
}
