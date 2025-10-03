package com.example.reply.test

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.reply.data.local.LocalEmailsDataProvider
import com.example.reply.ui.ReplyApp
import org.junit.Rule
import org.junit.Test

class ReplyAppStateRestorationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @TestCompactWidth
    fun compactDevice_configChange_SelectedEmailRetained() {
        // Setup compact window
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Compact)
        }

        // Third email is displayed
        val thirdEmailSubject = composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body)
        val thirdEmailBody = composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].subject)
        composeTestRule.onNodeWithText(thirdEmailBody).assertIsDisplayed()

        // Open details page of third email
        composeTestRule.onNodeWithText(thirdEmailSubject).performClick()

        // Verify details screen is shown for correct email
        composeTestRule.onNodeWithContentDescriptionForStringId(com.example.reply.R.string.navigation_back).assertExists()

        // Simulate config change
        stateRestorationTester.emulateSavedInstanceStateRestore()

        // Verify details screen of third email is still shown
        composeTestRule.onNodeWithText(thirdEmailBody).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescriptionForStringId(com.example.reply.R.string.navigation_back).assertExists()
    }

    @Test
    @TestExpandedWidth
    fun expandedDevice_configChange_SelectedEmailRetained() {
        // Setup compact window
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Expanded)
        }

        // Third email is displayed
        val thirdEmailSubject = composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body)
        val thirdEmailBody = composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].subject)
        composeTestRule.onNodeWithText(thirdEmailBody).assertIsDisplayed()

        // Open details page of third email
        composeTestRule.onNodeWithText(thirdEmailSubject).performClick()

        // Verify details screen is shown for correct email
        composeTestRule.onNodeWithContentDescriptionForStringId(com.example.reply.R.string.navigation_back).assertExists()

        // Simulate config change
        stateRestorationTester.emulateSavedInstanceStateRestore()

        // Verify details screen of third email is still shown
        composeTestRule.onNodeWithText(thirdEmailBody).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescriptionForStringId(com.example.reply.R.string.navigation_back).assertExists()
    }
}