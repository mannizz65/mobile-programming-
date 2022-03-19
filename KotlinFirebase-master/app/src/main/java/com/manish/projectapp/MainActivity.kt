package com.manish.projectapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()


        register.setOnClickListener{
            var intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
            finish()
        }

        login.setOnClickListener{
            if(email.text.toString().trim{it <= ' '}.isNotEmpty() && password.text.toString().trim{it <= ' '}.isNotEmpty()){
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this){ task ->
                        if(task.isSuccessful){
                            var intent = Intent(this, DashBoardActivity::class.java)
                            intent.putExtra("email",email.text.toString())
                            startActivity(intent)
                            finish()
                        }
                        else{
                            Toast.makeText(this, "Wrong Email or Password", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            else{
                Toast.makeText(this, "Enter the details", Toast.LENGTH_LONG).show()
            }
        }
    }
}