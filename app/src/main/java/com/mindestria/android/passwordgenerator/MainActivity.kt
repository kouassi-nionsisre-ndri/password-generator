package com.mindestria.android.passwordgenerator

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    // Objects declarations
    private var passwordActivityButton: Button? = null
    private var anotherActivityButton: Button? = null
    private var exitButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Change Title name of the activity
        supportActionBar?.setTitle(R.string.app_name_full)
        supportActionBar?.setLogo(R.mipmap.icon);
        // Get view objects
        passwordActivityButton = findViewById<Button>(R.id.passwordActivityButton)
        anotherActivityButton = findViewById<Button>(R.id.anotherActivityButton)
        exitButton = findViewById<Button>(R.id.exitButton)
        // Settings events listener
        passwordActivityButton?.setOnClickListener {
            val passwordActivityIntent = Intent(
                this@MainActivity,
                PasswordActivity::class.java
            )
            startActivity(passwordActivityIntent)
        }
        anotherActivityButton?.setOnClickListener {
            Toast.makeText(this@MainActivity, "Coming soon !", Toast.LENGTH_SHORT).show()
        }
        exitButton?.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle(R.string.exit_dialog_title_text)
            dialog.setMessage(R.string.exit_dialog_message_text)
            dialog.setCancelable(false)
            dialog.setPositiveButton(R.string.exit_dialog_positive_answer_text, DialogInterface.OnClickListener { dialog, which ->
                finish()
            })
            dialog.setNegativeButton(R.string.exit_dialog_negative_answer_text, DialogInterface.OnClickListener{
                    dialog, which ->
                dialog.dismiss()
            })
            dialog.show()
        }
    }
}