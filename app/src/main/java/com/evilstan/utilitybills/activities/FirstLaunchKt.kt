package com.evilstan.utilitybills.activities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.evilstan.utilitybills.R
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType

class FirstLaunchKt : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        isColorTransitionsEnabled = true
        setDoneText(R.string.done_button_text)

        setTransformer(
            AppIntroPageTransformerType.Parallax(
                titleParallaxFactor = 1.0,
                imageParallaxFactor = -1.0,
                descriptionParallaxFactor = 2.0
            )
        )



        val title1: String = getString(R.string.screen_1_title)
        val text1: String = getString(R.string.screen_1_text)
        addSlide(
            AppIntroFragment.newInstance(
                title1,
                text1,
                R.drawable.icons_screen_1,
                getColor(R.color.dark_blue)
            )
        )

        val title2: String = getString(R.string.screen_2_title)
        val text2: String = getString(R.string.screen_2_text)
        addSlide(
            AppIntroFragment.newInstance(
                title2,
                text2,
                R.drawable.icons_screen_2,
                getColor(R.color.dark_blue)
            )
        )

        val title3: String = getString(R.string.screen_3_title)
        val text3: String = getString(R.string.screen_3_text)
        addSlide(
            AppIntroFragment.newInstance(
                title3,
                text3,
                R.drawable.icons_screen_3,
                getColor(R.color.dark_blue)
            )
        )

        val title4: String = getString(R.string.screen_4_title)
        val text4: String = getString(R.string.screen_4_text)
        addSlide(
            AppIntroFragment.newInstance(
                title4,
                text4,
                R.drawable.icons_screen_4,
                getColor(R.color.dark_blue)
            )
        )

        val title5: String = getString(R.string.screen_5_title)
        val text5: String = getString(R.string.screen_5_text)
        addSlide(
            AppIntroFragment.newInstance(
                title5,
                text5,
                R.drawable.icons_screen_5,
                getColor(R.color.dark_blue)
            )
        )
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        val intent = Intent(MainActivity.context,
            FirstLaunchSettingActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        val intent = Intent(MainActivity.context,
            FirstLaunchSettingActivity::class.java)
        startActivity(intent)
        finish()
    }
}