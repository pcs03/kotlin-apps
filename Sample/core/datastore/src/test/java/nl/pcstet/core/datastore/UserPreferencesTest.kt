package nl.pcstet.core.datastore

import androidx.datastore.core.DataStoreFactory
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import nl.pcstet.core.model.AppTheme
import nl.pcstet.core.model.UserPreferences
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import kotlin.test.Test

class UserPreferencesTest {
    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder()

    private lateinit var userPreferencesRepository: UserPreferencesRepository
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())

    @Before
    fun setupDataStore() {
        val dataStore = DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            scope = testScope,
            produceFile = { tmpFolder.newFile("user_preferences_test.json") }
        )
        userPreferencesRepository = DefaultUserPreferencesRepository(dataStore)
    }

    @Test
    fun `should return correct initial values when first initialized`() = runTest {
        userPreferencesRepository.userPreferences.first() shouldBe UserPreferences()
    }

    @Test
    fun `should return correct updated API URL when updateApiUrl method called`() = runTest {
        val updatedApiUrl = "https://example.com/api"
        userPreferencesRepository.updateApiUrl(updatedApiUrl)

        userPreferencesRepository.userPreferences.first().apiUrl shouldBe updatedApiUrl
    }

    @Test
    fun `should return correct onboardingComplete when updateOnboardingComplete method called`() = runTest {
        userPreferencesRepository.updateOnboardingComplete(true)

        userPreferencesRepository.userPreferences.first().onboardingComplete shouldBe true
    }

    @Test
    fun `should return correct app theme true when updateAppTheme method called`() = runTest {
        userPreferencesRepository.updateAppTheme(AppTheme.LIGHT)
        userPreferencesRepository.userPreferences.first().appTheme shouldBe AppTheme.LIGHT

        userPreferencesRepository.updateAppTheme(AppTheme.DARK)
        userPreferencesRepository.userPreferences.first().appTheme shouldBe AppTheme.DARK

        userPreferencesRepository.updateAppTheme(AppTheme.SYSTEM)
        userPreferencesRepository.userPreferences.first().appTheme shouldBe AppTheme.SYSTEM
    }

    @Test
    fun `should have accessToken and not have email when saveAuthData called with accessToken, email and rememberEmail false`() = runTest {
        val accessToken = "test"
        val email = "test@example.com"
        userPreferencesRepository.saveAuthData(
            accessToken = accessToken,
            email = email,
            rememberEmail = false
        )

        userPreferencesRepository.userPreferences.first().accessToken shouldBe accessToken
        userPreferencesRepository.userPreferences.first().rememberedEmail shouldBe null

        userPreferencesRepository.clearAuthData()

        userPreferencesRepository.userPreferences.first().accessToken shouldBe null
        userPreferencesRepository.userPreferences.first().rememberedEmail shouldBe null
    }

    @Test
    fun `should have accessToken and email when saveAuthData called with accessToken, email and rememberEmail true`() = runTest {
        val accessToken = "test"
        val email = "test@example.com"
        userPreferencesRepository.saveAuthData(
            accessToken = accessToken,
            email = email,
            rememberEmail = true
        )

        userPreferencesRepository.userPreferences.first().accessToken shouldBe accessToken
        userPreferencesRepository.userPreferences.first().rememberedEmail shouldBe email

        userPreferencesRepository.clearAuthData()

        userPreferencesRepository.userPreferences.first().accessToken shouldBe null
        userPreferencesRepository.userPreferences.first().rememberedEmail shouldBe email
    }
}