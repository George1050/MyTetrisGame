package curso.tads.mytetrisgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import curso.tads.mytetrisgame.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private val LINHA = 36
    private val COLUNA = 26
    private val INICIOX = 1
    private val INICIOY = 14
    private var jogando = true
    private var velocidade:Long = 300

    lateinit var peca: Peca

    lateinit var binding: ActivityGameBinding

    var board = Array(LINHA) {
        Array(COLUNA){0}
    }

    private var boardView = Array(LINHA){
        arrayOfNulls<ImageView>(COLUNA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_game)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)
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
        }
        gameRun()
    }

    private fun gameRun(){
        Thread{
            //var atualPosition = INICIOX
            tipoPeca()
            while(jogando){
                Thread.sleep(velocidade)
                runOnUiThread {
                    //limpa tela
                    limpaTela()
                    binding.left.setOnClickListener {
                        if(toLeft(peca.getPontos())){
                            peca.moveLeft()
                        }
                    }
                    binding.right.setOnClickListener {
                        if(toRight(peca.getPontos())){
                            peca.moveRight()
                        }
                    }
                    binding.baixo.setOnClickListener {
                        velocidade = 100
                    }
                    //move peça atual
                    if(toDown(peca.getPontos())){
                        peca.moveDown()
                    }else{
                        armazenarPosicao(peca.getPontos())
                        tipoPeca()
                        velocidade = 300
                    }
                    //print peça
                    exibirPeca()

                }
            }
            //Toast.makeText(applicationContext, "Saiu do laço!", Toast.LENGTH_SHORT).show()
        }.start()

    }

    private fun borda(p: Ponto): Boolean {
        return p.y == 0 || p.x == LINHA - 1 || p.y == COLUNA - 1 || p.x == 0
    }

    private fun armazenarPosicao(p:Array<Ponto>){
        p.forEach {
            board[it.x][it.y] = 1
        }
    }

    private fun toLeft(p:Array<Ponto>): Boolean{
        p.forEach {
            if(board[it.x][it.y-1] == 1 || borda(Ponto(it.x, it.y-1))){
                return false
            }
        }
        return true
    }

    private fun toRight(p:Array<Ponto>): Boolean{
        p.forEach {
            if(board[it.x][it.y+1] == 1 || borda(Ponto(it.x, it.y+1))){
                return false
            }
        }
        return true
    }

    private fun posicaoValida(p:Ponto): Boolean {
        if(
                (board[p.x][p.y-1] == 1 && board[p.x+1][p.y] == 1) ||
                (board[p.x][p.y+1] == 1 && board[p.x+1][p.y] == 1) || (board[p.x+1][p.y] == 1)
        ){
            return true
        }
        return false
    }

    private fun toDown(p:Array<Ponto>): Boolean{
        p.forEach {
            if(borda(Ponto(it.x+1, it.y)) || posicaoValida(it)){
                return false
            }
        }
        return true
    }

    private fun tipoPeca(){
        when((Math.random() * 6).toInt()){
            0 -> peca = Quadrado(INICIOX, INICIOY)
            1 -> peca = Linha(INICIOX, INICIOY)
            2 -> peca = Triangulo(INICIOX, INICIOY)
            4 -> peca = LetraLhorizontal(INICIOX, INICIOY)
            5 -> peca = LetraShorizontal(INICIOX, INICIOY)
        }
    }

    private fun limpaTela(){
        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                if(borda(Ponto(i, j))){
                    boardView[i][j]!!.setImageResource(R.drawable.gray)
                } else if (board[i][j] == 1) {
                    boardView[i][j]!!.setImageResource(R.drawable.white)
                } else {
                    boardView[i][j]!!.setImageResource(R.drawable.black)
                }
            }
        }
    }

    private fun exibirPeca(){
        try {
            peca.getPontos().forEach {
                boardView[it.x][it.y]!!.setImageResource(R.drawable.white)
            }
        }catch (e:ArrayIndexOutOfBoundsException ) {
            //se a peça passou das bordas eu vou parar o jogo
            jogando = false
        }
    }
}