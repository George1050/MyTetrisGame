package curso.tads.mytetrisgame

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import curso.tads.mytetrisgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.novo.setOnClickListener {
            val i = Intent(this, GameActivity::class.java)
            i.putExtra("continuar", false)
            setResult(Activity.RESULT_CANCELED)
            startActivity(i)
        }

        binding.continuar.setOnClickListener {
            val continuar = intent.getBooleanExtra("continuar", false)
            if(continuar){
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
        binding.configurar.setOnClickListener {
            val i = Intent(this, ConfigActivity::class.java)
            startActivity(i)
        }

    }
}