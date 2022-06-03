package com.se.termproject.ui.customer.home

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

internal class HomeFragmentTest {
    private lateinit var homeFragment: HomeFragment

    @Before
    fun setUp() {
        homeFragment = HomeFragment()
    }

    /**
     * function: calcualteRatio (1)
     * motivation: There was a time when the calculation was weird due to a type conversion.
     * objective: To check if the calculation is good for the traffic-light(red/yellow/green) function.
     */
    @Test
    fun calculateRatio_firstTest() {
        val result = homeFragment.calculateRatio(5, 5)
        assertThat(result).isEqualTo(0.0)
    }

    /**
     * function: calcualteRatio (2, almost same as above)
     * motivation: There was a time when the calculation was weird due to a type conversion.
     * objective: To check if the calculation is good for the traffic-light(red/yellow/green) function.
     */
    @Test
    fun calculateRatio_secondTest() {
        val result = homeFragment.calculateRatio(5, 0)
        assertThat(result).isEqualTo(100.0)
    }

    /**
     * function: calcualteRatio (3, almost same as above)
     * motivation: There was a time when the calculation was weird due to a type conversion.
     * objective: To check if the calculation is good for the traffic-light(red/yellow/green) function.
     */
    @Test
    fun calculateRatio_thirdTest() {
        val result = homeFragment.calculateRatio(12, 6)
        assertThat(result).isEqualTo(50.0)
    }
}
