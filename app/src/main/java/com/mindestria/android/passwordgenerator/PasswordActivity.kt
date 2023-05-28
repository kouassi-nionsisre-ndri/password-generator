package com.mindestria.android.passwordgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.security.SecureRandom


class PasswordActivity : AppCompatActivity() {

    // Objects declarations
    var alphabeticSwitch: Switch? = null
    var alphabeticRadioGroup: RadioGroup? = null
    var numericSwitch: Switch? = null
    var symbolicSwitch: Switch? = null
    var passwordLengthTextView: TextView? = null
    var passwordLengthSeekBar: SeekBar? = null
    var generateButton: Button? = null
    var passwordScreen: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        // Change Title name of the activity
        supportActionBar?.setTitle(R.string.app_name_full)
        // Enable back home button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Getting objects from view
        alphabeticSwitch = findViewById<Switch>(R.id.alphabeticSwitch);
        alphabeticRadioGroup = findViewById<RadioGroup>(R.id.alphabeticRadioGroup);
        numericSwitch = findViewById<Switch>(R.id.numericSwitch);
        symbolicSwitch = findViewById<Switch>(R.id.symbolicSwitch);
        passwordLengthTextView = findViewById<TextView>(R.id.passwordLengthTextView);
        passwordLengthSeekBar = findViewById<SeekBar>(R.id.passwordLengthSeekBar);
        generateButton = findViewById<Button>(R.id.generateButton);
        passwordScreen = findViewById<TextView>(R.id.passwordScreen);
        // Settings events listener
        passwordLengthTextView?.text = getString(R.string.lbl_password_length) + " (" + passwordLengthSeekBar?.getProgress() + ")"
        alphabeticSwitch?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (alphabeticRadioGroup?.visibility == View.GONE) {
                alphabeticRadioGroup?.visibility = View.VISIBLE
            } else {
                alphabeticRadioGroup?.visibility = View.GONE
            }
        })
        passwordLengthSeekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                passwordLengthTextView?.text = getString(R.string.lbl_password_length) + " (" + passwordLengthSeekBar?.getProgress() + ")"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        generateButton?.setOnClickListener(View.OnClickListener {
            if (alphabeticSwitch?.isChecked == true || numericSwitch?.isChecked == true || symbolicSwitch?.isChecked == true) {
                // Variables
                val alphabeticLowerCase = "abcdefghijklmnopqrstuvwxyz"
                val alphabeticUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                val numericNumbers = "0123456789"
                val symbolicCharacters = "@#$&€éèàç=+£!?"
                val passwordCharactersList = StringBuilder()
                // Random objects
                val randomNumber = SecureRandom()
                val passwordGenerated = StringBuilder(10)
                // Baking password character list using enable options
                if (alphabeticSwitch?.isChecked == true && alphabeticRadioGroup?.checkedRadioButtonId == R.id.rad_lowerCase) passwordCharactersList.append(
                    alphabeticLowerCase
                )
                if (alphabeticSwitch?.isChecked == true && alphabeticRadioGroup?.checkedRadioButtonId == R.id.rad_upperCase) passwordCharactersList.append(
                    alphabeticUpperCase
                )
                if (alphabeticSwitch?.isChecked == true && alphabeticRadioGroup?.checkedRadioButtonId == R.id.rad_lowerUpperCase) passwordCharactersList.append(
                    alphabeticLowerCase + alphabeticUpperCase
                )
                if (numericSwitch?.isChecked == true) passwordCharactersList.append(numericNumbers)
                if (symbolicSwitch?.isChecked == true) passwordCharactersList.append(symbolicCharacters)
                // Generating random password
                for (i in 0 until passwordLengthSeekBar?.progress!!) {
                    passwordGenerated.append(
                        passwordCharactersList.toString()[randomNumber.nextInt(
                            passwordCharactersList.length
                        )]
                    )
                }
                // Display password
                passwordScreen?.text = passwordGenerated.toString()
            } else {
                Toast.makeText(
                    this@PasswordActivity,
                    R.string.dlg_error_check_one,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        passwordScreen?.setOnLongClickListener(View.OnLongClickListener {
            passwordScreen?.isCursorVisible = true
            val clipboard: ClipboardManager = this@PasswordActivity.getSystemService(
                CLIPBOARD_SERVICE
            ) as ClipboardManager
            val clip = ClipData.newPlainText("password", passwordScreen?.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                this@PasswordActivity,
                R.string.dlg_password_copied,
                Toast.LENGTH_SHORT
            )
                .show()
            false
        })

    }
}