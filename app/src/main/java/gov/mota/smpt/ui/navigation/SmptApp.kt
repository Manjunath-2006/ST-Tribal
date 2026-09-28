package gov.mota.smpt.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import gov.mota.smpt.ui.screens.*
import gov.mota.smpt.ui.theme.*
import gov.mota.smpt.viewmodel.SmptViewModel

/**
 * Bottom navigation items for the main app shell.
 */
private data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val bottomNavItems = listOf(
    BottomNavItem(SmptRoutes.HOME, "Home", Icons.Filled.Home),
    BottomNavItem(SmptRoutes.SCHOLARSHIPS, "Scholarships", Icons.Filled.School),
    BottomNavItem(SmptRoutes.APPLICATIONS, "Applications", Icons.Filled.Assignment),
    BottomNavItem(SmptRoutes.PAYMENTS, "Payments", Icons.Filled.Payment),
    BottomNavItem(SmptRoutes.PROFILE, "Profile", Icons.Filled.Person),
)

private val mainRoutes = bottomNavItems.map { it.route }.toSet()

@Composable
fun SmptApp() {
    val viewModel: SmptViewModel = viewModel()
    val navController = rememberNavController()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val hasCompletedOnboarding by viewModel.hasCompletedOnboarding.collectAsState()
    val hasCompletedProfileSetup by viewModel.hasCompletedProfileSetup.collectAsState()

    val startDestination = when {
        !hasCompletedOnboarding -> SmptRoutes.SPLASH
        !isLoggedIn -> SmptRoutes.LOGIN
        !hasCompletedProfileSetup -> SmptRoutes.PROFILE_SETUP
        else -> SmptRoutes.HOME
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in mainRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = BottomNavBg,
                    contentColor = BottomNavSelected
                ) {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(item.icon, contentDescription = item.label)
                            },
                            label = {
                                Text(
                                    item.label,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = BottomNavSelected,
                                selectedTextColor = BottomNavSelected,
                                unselectedIconColor = BottomNavUnselected,
                                unselectedTextColor = BottomNavUnselected,
                                indicatorColor = StatusBlueBg
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            // ─── Pre-auth ────────────────────────────────────
            composable(SmptRoutes.SPLASH) {
                SplashScreen(
                    onNavigateNext = {
                        navController.navigate(SmptRoutes.ONBOARDING) {
                            popUpTo(SmptRoutes.SPLASH) { inclusive = true }
                        }
                    }
                )
            }

            composable(SmptRoutes.ONBOARDING) {
                OnboardingScreen(
                    onSkip = {
                        viewModel.completeOnboarding()
                        navController.navigate(SmptRoutes.LOGIN) {
                            popUpTo(SmptRoutes.ONBOARDING) { inclusive = true }
                        }
                    },
                    onComplete = {
                        viewModel.completeOnboarding()
                        navController.navigate(SmptRoutes.LOGIN) {
                            popUpTo(SmptRoutes.ONBOARDING) { inclusive = true }
                        }
                    }
                )
            }

            composable(SmptRoutes.LOGIN) {
                LoginScreen(
                    onLogin = { mobile, otp ->
                        val success = viewModel.login(mobile, otp)
                        if (success) {
                            navController.navigate(SmptRoutes.PROFILE_SETUP) {
                                popUpTo(SmptRoutes.LOGIN) { inclusive = true }
                            }
                        }
                        success
                    },
                    onDemoLogin = {
                        viewModel.demoLogin()
                        navController.navigate(SmptRoutes.HOME) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            composable(SmptRoutes.PROFILE_SETUP) {
                ProfileSetupScreen(
                    onComplete = {
                        viewModel.completeProfileSetup()
                        navController.navigate(SmptRoutes.HOME) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            // ─── Main ────────────────────────────────────────
            composable(SmptRoutes.HOME) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToScholarships = { navController.navigate(SmptRoutes.SCHOLARSHIPS) },
                    onNavigateToApplicationDetail = { navController.navigate(SmptRoutes.applicationDetail(it)) },
                    onNavigateToNotifications = { navController.navigate(SmptRoutes.NOTIFICATIONS) },
                    onNavigateToJago = { navController.navigate(SmptRoutes.JAGO) },
                    onNavigateToDocuments = { navController.navigate(SmptRoutes.DOCUMENTS) },
                    onNavigateToUnifiedVerification = { navController.navigate(SmptRoutes.UNIFIED_VERIFICATION) },
                    onNavigateToScholarshipAwareness = { navController.navigate(SmptRoutes.SCHOLARSHIP_AWARENESS) }
                )
            }

            composable(SmptRoutes.SCHOLARSHIPS) {
                ScholarshipsScreen(
                    viewModel = viewModel,
                    onSchemeClick = { navController.navigate(SmptRoutes.scholarshipDetail(it)) }
                )
            }

            composable(SmptRoutes.SCHOLARSHIP_DETAIL) { backStackEntry ->
                val schemeId = backStackEntry.arguments?.getString("schemeId") ?: return@composable
                ScholarshipDetailScreen(
                    schemeId = schemeId,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onCheckEligibility = { navController.navigate(SmptRoutes.eligibilityCheck(it)) },
                    onConflictCheck = { navController.navigate(SmptRoutes.conflictCheck(it)) },
                    onTrackApplication = { navController.navigate(SmptRoutes.applicationDetail(it)) }
                )
            }

            composable(SmptRoutes.ELIGIBILITY_CHECK) { backStackEntry ->
                val schemeId = backStackEntry.arguments?.getString("schemeId") ?: return@composable
                EligibilityCheckScreen(
                    schemeId = schemeId,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onConflictCheck = { navController.navigate(SmptRoutes.conflictCheck(it)) },
                    onApply = { navController.popBackStack() }
                )
            }

            composable(SmptRoutes.CONFLICT_CHECK) { backStackEntry ->
                val schemeId = backStackEntry.arguments?.getString("schemeId") ?: return@composable
                ConflictCheckScreen(
                    targetSchemeId = schemeId,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onProceedToReadiness = {
                        val appId = viewModel.applications.value.lastOrNull()?.id ?: "APP-2026-1156"
                        navController.navigate(SmptRoutes.applicationReadiness(appId))
                    }
                )
            }

            composable(SmptRoutes.APPLICATION_READINESS) { backStackEntry ->
                val appId = backStackEntry.arguments?.getString("applicationId") ?: return@composable
                ApplicationReadinessScreen(
                    applicationId = appId,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onSubmit = { navController.popBackStack() }
                )
            }

            composable(SmptRoutes.APPLICATIONS) {
                ApplicationsScreen(
                    viewModel = viewModel,
                    onApplicationClick = { navController.navigate(SmptRoutes.applicationDetail(it)) },
                    onExploreScholarships = { navController.navigate(SmptRoutes.SCHOLARSHIPS) }
                )
            }

            composable(SmptRoutes.APPLICATION_DETAIL) { backStackEntry ->
                val appId = backStackEntry.arguments?.getString("applicationId") ?: return@composable
                ApplicationDetailScreen(
                    applicationId = appId,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onNavigateToDocuments = { navController.navigate(SmptRoutes.DOCUMENTS) },
                    onNavigateToJago = { navController.navigate(SmptRoutes.JAGO) },
                    onNavigateToReadiness = { navController.navigate(SmptRoutes.applicationReadiness(it)) }
                )
            }

            composable(SmptRoutes.PAYMENTS) {
                PaymentsScreen(
                    viewModel = viewModel,
                    onPaymentClick = { navController.navigate(SmptRoutes.paymentDetail(it)) }
                )
            }

            composable(SmptRoutes.PAYMENT_DETAIL) { backStackEntry ->
                val payId = backStackEntry.arguments?.getString("paymentId") ?: return@composable
                PaymentDetailScreen(
                    paymentId = payId,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(SmptRoutes.PROFILE) {
                ProfileScreen(
                    viewModel = viewModel,
                    onNavigateToDocuments = { navController.navigate(SmptRoutes.DOCUMENTS) },
                    onNavigateToVerification = { navController.navigate(SmptRoutes.UNIFIED_VERIFICATION) },
                    onNavigateToExistingSystems = { navController.navigate(SmptRoutes.EXISTING_SYSTEMS) },
                    onNavigateToVerificationSources = { navController.navigate(SmptRoutes.VERIFICATION_SOURCES) },
                    onNavigateToMinistryInsights = { navController.navigate(SmptRoutes.MINISTRY_INSIGHTS) },
                    onNavigateToHelp = { navController.navigate(SmptRoutes.HELP) }
                )
            }

            // ─── Secondary ───────────────────────────────────
            composable(SmptRoutes.DOCUMENTS) {
                DocumentsScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(SmptRoutes.UNIFIED_VERIFICATION) {
                UnifiedVerificationScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(SmptRoutes.NOTIFICATIONS) {
                NotificationsScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onNotificationClick = { route ->
                        if (route != null) {
                            handleDeepRoute(navController, route)
                        }
                    }
                )
            }

            composable(SmptRoutes.JAGO) {
                JagoScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onNavigate = { route -> handleDeepRoute(navController, route) }
                )
            }

            composable(SmptRoutes.HELP) {
                HelpScreen(onBackClick = { navController.popBackStack() })
            }

            composable(SmptRoutes.MINISTRY_INSIGHTS) {
                MinistryInsightsScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(SmptRoutes.SCHOLARSHIP_AWARENESS) {
                ScholarshipAwarenessScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onCheckScholarships = { navController.navigate(SmptRoutes.SCHOLARSHIPS) }
                )
            }

            composable(SmptRoutes.EXISTING_SYSTEMS) {
                ExistingSystemsScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(SmptRoutes.VERIFICATION_SOURCES) {
                VerificationSourcesScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

/**
 * Handles deep navigation from notifications, JAGO action buttons, etc.
 */
private fun handleDeepRoute(navController: NavHostController, route: String) {
    try {
        navController.navigate(route)
    } catch (_: Exception) {
        // If route doesn't match exactly, try to navigate to base route
        val baseRoute = route.substringBefore("/")
        try {
            navController.navigate(baseRoute)
        } catch (_: Exception) {
            // Silently ignore unknown routes in prototype
        }
    }
}
