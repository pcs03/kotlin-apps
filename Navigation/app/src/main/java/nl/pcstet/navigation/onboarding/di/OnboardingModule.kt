package nl.pcstet.navigation.onboarding.di

import io.ktor.client.HttpClient
import nl.pcstet.navigation.onboarding.network.OnboardingApiService
import nl.pcstet.navigation.onboarding.presentation.OnboardingViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val onboardingModule = module {

//    single<HttpClient>(named("apiTest")) { params -> provideHttpClient(get(), params.get()) }
    singleOf(::OnboardingApiService)
    viewModelOf(::OnboardingViewModel)
}
