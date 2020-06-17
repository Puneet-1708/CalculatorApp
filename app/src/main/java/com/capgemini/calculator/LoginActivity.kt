package com.capgemini.calculator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.vvalidator.form
import kotlinx.android.synthetic.main.login_activity.*


class LoginActivity : AppCompatActivity() {


    private val sharedPrefFile: String="User_Data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        val userName=findViewById<EditText>(R.id.eT_email)
        val password=findViewById<EditText>(R.id.eT_password)
        val button=findViewById<Button>(R.id.button_login)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)

        form {
            input(userName, name = "Optional name") {
                isNotEmpty().description("Please Enter Email")
                isEmail()
            }
            input(password, name = "Optional Name") {
                isNotEmpty().description("Please Enter a valid Password")
                length().atLeast(8)
                matches("^[A-Za-z1-9]+$")
            }
            submitWith(button){
                val sharedIDValue = sharedPreferences.getString("emailID_key","defaultname")
                val sharedPassValue = sharedPreferences.getString("password_key","defaultpass")
                val email=userName.text.toString()
                val pass=password.text.toString()
                if (sharedIDValue==email&&sharedPassValue==pass){
                    Toast.makeText(this@LoginActivity,"Valid Credentials",Toast.LENGTH_SHORT).show()
                    sendUserToWelcomeActivity()

                }
            }
        }

        tV_register.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

    }
    private fun sendUserToWelcomeActivity() {
        val mainIntent = Intent(this@LoginActivity, CalculatorActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(mainIntent)
        finish()
    }

}
