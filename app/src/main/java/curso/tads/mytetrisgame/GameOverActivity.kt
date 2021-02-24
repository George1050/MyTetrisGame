package curso.tads.mytetrisgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import curso.tads.mytetrisgame.databinding.ActivityGameOverBinding

class GameOverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameOverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_over)
        binding.novoJogo.setOnClickListener {
            val i = Intent(this, GameActivity::class.java)
            startActivity(i)
        }

        binding.sair.setOnClickListener {
            finish()
        }

        binding.pontos.text = intent.getStringExtra("pontuacao") ?: "0"

    }


}