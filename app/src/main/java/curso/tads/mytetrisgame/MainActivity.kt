package curso.tads.mytetrisgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import curso.tads.mytetrisgame.databinding.ActivityGameBinding
import curso.tads.mytetrisgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)



        binding.novo.setOnClickListener {
            val i = Intent(this, GameActivity::class.java)
            startActivity(i)
        }
        binding.configurar.setOnClickListener {
            val i = Intent(this, ActivityConfig::class.java)
            startActivity(i)
        }

    }
}