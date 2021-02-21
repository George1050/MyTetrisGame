package curso.tads.mytetrisgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import curso.tads.mytetrisgame.databinding.ActivityGameBinding
import curso.tads.mytetrisgame.modeloPeca.*

class GameActivity : AppCompatActivity() {
    private val linha = 26
    private val coluna = 16
    private val iniciox = 1
    private val inicioy = 8
    private var jogando = true
    private var velocidade:Long = 300

    lateinit var peca: Peca

    lateinit var binding: ActivityGameBinding

    var board = Array(linha) {
        Array(coluna){0}
    }

    private var boardView = Array(linha){
        arrayOfNulls<ImageView>(coluna)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_game)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)
        binding.apply {
            gridboard.rowCount = linha
            gridboard.columnCount = coluna

            val inflater = LayoutInflater.from(applicationContext)

            for (i in 0 until linha) {
                for (j in 0 until coluna) {
                    boardView[i][j] = inflater.inflate(R.layout.inflate_image_view, gridboard, false) as ImageView
                    gridboard.addView( boardView[i][j])
                }
            }
        }
        gameRun()
    }

    private fun gameRun(){
        Thread{
            //var atualPosition = iniciox
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
                    binding.girar.setOnClickListener {
                        girarPeca()
                        Thread.sleep(300)
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

    private fun tipoPeca(){
        when((Math.random() * 6).toInt()){
            0 -> peca = Quadrado(iniciox, inicioy)
            1 -> peca = Linha(iniciox, inicioy)
            2 -> peca = Triangulo(iniciox, inicioy)
            4 -> peca = LetraLEsquerda(iniciox, inicioy)
            5 -> peca = LetraSDireita(iniciox, inicioy)
        }
    }

    private fun armazenarPosicao(p:Array<Ponto>){
        p.forEach {
            board[it.x][it.y] = 1
        }
    }

    private fun borda(p: Ponto): Boolean {
        return p.y == 0 || p.x == linha - 1 || p.y == coluna - 1 || p.x == 0
    }

    private fun posicaoInvalida(p: Ponto): Boolean {
        if(
                (board[p.x][p.y-1] == 1 && board[p.x+1][p.y] == 1) ||
                (board[p.x][p.y+1] == 1 && board[p.x+1][p.y] == 1) || (board[p.x+1][p.y] == 1)
        ){
            return true
        }
        return false
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

    private fun toDown(p:Array<Ponto>): Boolean{
        p.forEach {
            if(borda(Ponto(it.x+1, it.y)) || posicaoInvalida(it)){
                return false
            }
        }
        return true
    }

    private fun girarPeca(){
        when(peca){
            is Quadrado -> {
                //peca.rotacionar()
            }
            is Linha -> {
                val pontos = peca.rotacionar()
                if(toLeft(pontos) && toRight(pontos) && toDown(pontos)) {
                    peca.setPontos(pontos)
                    peca.getPontos().forEach {
                        it.moveUp()
                    }
                    peca.setOrietacaPeca((peca as Linha).orientacao)
                }
            }
            is Triangulo -> {
                val pontos = peca.rotacionar()
                if(toLeft(pontos) || toRight(pontos) || toDown(pontos)){
                    peca.setPontos(pontos)
                    peca.getPontos().forEach {
                        it.moveUp()
                    }
                    peca.setOrietacaPeca((peca as Triangulo).orientacao)
                }
            }
            is LetraSDireita -> {
                val pontos = peca.rotacionar()
                if(toLeft(pontos) || toRight(pontos) || toDown(pontos)){
                    peca.setPontos(pontos)
                    peca.getPontos().forEach {
                        it.moveUp()
                    }
                    peca.setOrietacaPeca((peca as LetraSDireita).orientacao)
                }
            }
            is LetraLEsquerda -> {
                val pontos = peca.rotacionar()
                if(toLeft(pontos) || toRight(pontos) || toDown(pontos)){
                    peca.setPontos(pontos)
                    peca.getPontos().forEach {
                        it.moveUp()
                    }
                    peca.setOrietacaPeca((peca as LetraLEsquerda).orientacao)
                }
            }
        }
    }

    private fun limpaTela(){
        for (i in 0 until linha) {
            for (j in 0 until coluna) {
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