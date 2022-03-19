package com.manish.projectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        rbtn.setOnClickListener{
            if( rname.text.toString().trim{it <= ' '}.isNotEmpty() && rphone.text.toString().trim{it <= ' '}.isNotEmpty() && remail.text.toString().trim{it <= ' '}.isNotEmpty() && rpassword.text.toString().trim{it <= ' '}.isNotEmpty() ){
                var name = rname.text.toString()
                var phone = rphone.text.toString()
                var email = remail.text.toString()
                var password = rpassword.text.toString()

                val user = hashMapOf(
                    "name" to name,
                    "phone" to phone,
                    "email" to email
                )

                db.collection("Users").whereEqualTo("email",remail.text.toString()).get()
                    .addOnSuccessListener {
                        it ->
                        if(it.isEmpty){
                            auth.createUserWithEmailAndPassword(remail.text.toString(),rpassword.text.toString())
                                .addOnCompleteListener(this){
                                        task->
                                    if(task.isSuccessful){
                                        db.collection("Users").document(remail.text.toString()).set(user)
                                        val intent = Intent(this, DashBoardActivity::class.java)
                                        intent.putExtra("email", remail.text.toString())
                                        startActivity(intent)
                                        finish()
                                    }else{
                                        Toast.makeText(this, "Register Failed", Toast.LENGTH_LONG).show()
                                    }
                                }
                        }else{
                            Toast.makeText(this, "You are already registered", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }

            }else{
                Toast.makeText(this, "Enter the details", Toast.LENGTH_LONG).show()
            }
        }
    }
}