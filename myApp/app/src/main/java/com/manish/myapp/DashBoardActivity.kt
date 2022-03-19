package com.manish.myapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.manish.myapp.MainActivity
import com.manish.myapp.R
import kotlinx.android.synthetic.main.activity_dash_board.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder


const val BASE_URL = "https://jsonplaceholder.typicode.com/"
class DashBoardActivity : AppCompatActivity() {

    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        db = FirebaseFirestore.getInstance()

        val sharePref = this.getPreferences(Context.MODE_PRIVATE) ?:return
        val isLogin = sharePref.getString("email","1")

        logoutButtonId.setOnClickListener{
            sharePref.edit().remove("email").apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        restApi.setOnClickListener{
            val intent = Intent(this, RestApiDataActivity::class.java)
            startActivity(intent)
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
                    dmobile.text = tasks.get("phone").toString()
                    demail.text = tasks.get("email").toString()
                }
        }
    }
}