package curso.tads.mytetrisgame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import curso.tads.mytetrisgame.databinding.ActivityConfigBinding

class ConfigActivity : AppCompatActivity() {
    private lateinit var binding:ActivityConfigBinding
    private var dificuldade :String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_config)

        val config = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        dificuldade= config.getString("dificuldade","medio").toString()

        inserirDificuldadeSelecionada()

        binding.apply {
            confirmar.setOnClickListener {
                when (radioGroup.checkedRadioButtonId){
                    facil.id -> {
                        dificuldade = "facil"
                    }
                    medio.id -> {
                        dificuldade = "medio"
                    }
                    dificil.id -> {
                        dificuldade = "dificil"
                    }
                }
                finish()
            }
            cancelar.setOnClickListener {
                finish()
            }
        }
    }

    override fun onStop(){
        super.onStop()

        val config =getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val editor = config.edit()
        editor.putString("dificuldade",dificuldade).apply()
    }

    private  fun inserirDificuldadeSelecionada(){
        binding.apply {
            when (dificuldade) {
                "facil" -> {
                    facil.isChecked = true
                    medio.isChecked = false
                    dificil.isChecked = false
                }
                "dificil" -> {
                    facil.isChecked = false
                    medio.isChecked = false
                    dificil.isChecked = true
                }
                else -> {
                    facil.isChecked = false
                    medio.isChecked = true
                    dificil.isChecked = false

                }
            }
        }
    }

}