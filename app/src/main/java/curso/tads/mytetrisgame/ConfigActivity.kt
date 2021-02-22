package curso.tads.mytetrisgame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import curso.tads.mytetrisgame.databinding.ActivityConfigBinding

class ConfigActivity : AppCompatActivity() {
    lateinit var binding:ActivityConfigBinding
    var dificuldade :String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_config)

        val config = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        dificuldade= config.getString("dificuldade","default").toString()

        inserirDificuldadeSelecionada()

        binding.apply {
            confirmar.setOnClickListener {
                when (radioGroup.checkedRadioButtonId){
                    facil.id -> {
                        dificuldade = "facil"
                    }
                    medio.id -> {
                        dificuldade = "Medio"
                    }
                    dificil.id -> {
                        dificuldade = "Dificil"
                    }
                }
            }
        }
    }

    override fun onStop(){
        super.onStop()

        val config =getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val editor = config.edit()
        editor.putString("dificuldade",dificuldade)
        editor.commit()
    }

    private  fun inserirDificuldadeSelecionada(){
        binding.apply {
            if(dificuldade == "facil"){
                facil.isChecked = true
                medio.isChecked = false
                dificil.isChecked = false
            }
            else if(dificuldade == "dificil"){
                facil.isChecked = false
                medio.isChecked = false
                dificil.isChecked = true
            }
            else {
                facil.isChecked = false
                medio.isChecked = true
                dificil.isChecked = false

            }
        }
    }

}