package nl.pcstet.startupflow.ui.auth.di

import nl.pcstet.startupflow.ui.auth.feature.landing.LandingViewModel
import nl.pcstet.startupflow.ui.auth.feature.onboardingtest.OnboardingApiTestViewModel
import nl.pcstet.startupflow.ui.auth.feature.welcome.WelcomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authUiModule = module {
    viewModelOf(::LandingViewModel)
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::OnboardingApiTestViewModel)
}