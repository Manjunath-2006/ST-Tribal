package gov.mota.smpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import gov.mota.smpt.ui.navigation.SmptApp
import gov.mota.smpt.ui.theme.SmptTheme

/**
 * Main entry point for the SMPT Android application.
 *
 * SMPT — Scholarship Management Portal for Tribes
 * Ministry of Tribal Affairs, Government of India
 *
 * This is a prototype for the Smart India Hackathon 2026.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmptTheme {
                SmptApp()
            }
        }
    }
}
