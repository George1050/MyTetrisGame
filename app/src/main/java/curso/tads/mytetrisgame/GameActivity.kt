package curso.tads.mytetrisgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import curso.tads.mytetrisgame.databinding.ActivityGameBinding
import java.util.*

class GameActivity : AppCompatActivity() {
    val LINHA = 36
    val COLUNA = 26
    val INICIOX = 0
    val INICIOY = 14
    var jogando = true
    var velocidade:Long = 300

    lateinit var binding: ActivityGameBinding

    //val board = Array(LINHA, { IntArray(COLUNA) })

    var board = Array(LINHA) {
        Array(COLUNA){0}
    }

    var boardView = Array(LINHA){
        arrayOfNulls<ImageView>(COLUNA)
    }

    val random = Random()

    fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from // from(incluso) e to(excluso)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game);

        binding.apply {
            gridboard.rowCount = LINHA
            gridboard.columnCount = COLUNA

            val inflater = LayoutInflater.from(applicationContext)

            for (i in 0 until LINHA) {
                for (j in 0 until COLUNA) {
                    boardView[i][j] = inflater.inflate(R.layout.inflate_image_view, gridboard, false) as ImageView
                    gridboard.addView( boardView[i][j])
                }
            }

            for (i in 0 until LINHA) {
                for (j in 0 until COLUNA) {
                    boardView[i][j]!!.setImageResource(R.drawable.gray)
                }
            }

        }
        gameRun()
    }

    fun pecaTipo(tipo:Int): Peca {
        when(tipo){
            0 -> {
                return Quadrado(INICIOX, INICIOY)
            }

        }
    }

    fun gameRun(){
        Thread{
            var aleatorio = rand(1,8)
            var count = 0;
            var q = pecaTipo(count)
            (q as Quadrado)
            while(jogando){
                Thread.sleep(velocidade)
                runOnUiThread {
                    //limpa tela
                    for (i in 1 until LINHA - 1) {
                        for (j in 1 until COLUNA - 1) {
                            if (board[i][j] == 1) {
                                boardView[i][j]!!.setImageResource(R.drawable.white)
                            } else {
                                boardView[i][j]!!.setImageResource(R.drawable.black)
                            }
                        }
                    }

                    binding.left.setOnClickListener() {
                        q.moveLeft()
                        /*if(board[pt.x][pt.y-1] == 0){
                            pt.moveLeft()
                        }*/

                    }
                    binding.right.setOnClickListener() {
                        q.moveRight()
                        /*if(board[pt.x][pt.y+1] == 0){
                            pt.moveRight()
                        }*/
                    }
                    binding.baixo.setOnClickListener() {
                        velocidade = 100
                    }
                    //move peça atual
                    if((q as Quadrado).x < LINHA-2){
                        q.moveDown()
                    }else{
                        q = Linha(INICIOX, INICIOY)
                        count++
                    }
                    //print peça
                    try {
                        for (i in 0 until 4){
                            boardView[q.pontos.get(i).x][q.pontos.get(i).y]!!.setImageResource(R.drawable.white)
                        }
                    }catch (e:ArrayIndexOutOfBoundsException ) {
                        //se a peça passou das bordas eu vou parar o jogo
                        jogando = false
                    }

                }
            }
            //Toast.makeText(applicationContext, "Saiu do laço!", Toast.LENGTH_SHORT).show()
        }.start()

    }
}