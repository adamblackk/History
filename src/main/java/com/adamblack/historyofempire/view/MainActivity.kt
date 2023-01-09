package com.adamblack.historyofempire.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.adamblack.historyofempire.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth=Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser!=null){
            val intent=Intent(this, FeedActivity::class.java)
            startActivity(intent)
            finish()

        }



    }

    fun signInClicked(view:View){
        val email=binding.emailText.text.trim().toString()
        val password=binding.passwordText.text.trim().toString()
        println("oncesi okk")


        if (email.isNotEmpty()&&password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent=Intent(this@MainActivity, FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{

                    Toast.makeText(this,"error:${it.exception}",Toast.LENGTH_SHORT).show()

                }
            }
        }else{
            Toast.makeText(this,"textler bos yada null",Toast.LENGTH_SHORT).show()

        }



        }
    fun signUpClicked(view: View){

        val email=binding.emailText.text.trim().toString()
        val password=binding.passwordText.text.trim().toString()

        if (email.isNotEmpty()&&password.isNotEmpty()){
            println("okkk:${email + password}" )
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent=Intent(this@MainActivity, FeedActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                it.localizedMessage?.let {
                    Toast.makeText(this@MainActivity,"error:${it}",Toast.LENGTH_SHORT).show()

                }
            }
        }else{
            Toast.makeText(this@MainActivity,"biseyler ters gitti",Toast.LENGTH_SHORT).show()

        }






    }


    }

