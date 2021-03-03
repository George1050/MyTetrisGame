package curso.tads.mytetrisgame

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import curso.tads.mytetrisgame.databinding.ActivityGameOverBinding

class GameOverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameOverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pref = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val record = pref.getInt("record",0)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_over)
        binding.novoJogo.setOnClickListener {
            val i = Intent(this, GameActivity::class.java)
            i.putExtra("continuar", false)
            startActivity(i)
            finish()
        }

        binding.sair.setOnClickListener {
            finish()
        }

        binding.ultimoRecorde.text = record.toString()

        binding.novoRecord.text = intent.getStringExtra("avisoRecord")

        binding.pontos.text = intent.getStringExtra("pontuacao") ?: "0"

    }


}