package es.usj.androidapps.alu95669.chatapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.lang.Thread.sleep

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //THIS IS ONLY IF WE DON'T NEED TO LOAD DATA. CONTACT IÑIGO IF WE NEED TO LOAD THINGS IN THE SPLASHSCREEN
        sleep(1000)

        val i = Intent(this, LoginScreen::class.java)
        finish()
        startActivity(i)


    }
}