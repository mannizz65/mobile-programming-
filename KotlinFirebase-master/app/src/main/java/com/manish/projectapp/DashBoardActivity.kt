package com.manish.projectapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_dash_board.*

class DashBoardActivity : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)


        db = FirebaseFirestore.getInstance()

        val sharePref = this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin = sharePref.getString("email","1")

        logout.setOnClickListener{
            sharePref.edit().remove("email").apply()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        if(isLogin == "1"){
            var getEmail = intent.getStringExtra("email")
            if(getEmail != null){
                setText(getEmail)
                with(sharePref.edit()){
                    putString("email", getEmail)
                    apply()
                }
            }
            else{
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        else{
            setText(isLogin)
        }
    }

    private fun setText(email: String?) {
        if (email != null) {
            db.collection("Users").document(email).get()
                .addOnSuccessListener {
                    tasks->
                    dname.text = tasks.get("name").toString()
                    dphone.text = tasks.get("phone").toString()
                    demail.text = tasks.get("email").toString()
                }
        }
    }
}