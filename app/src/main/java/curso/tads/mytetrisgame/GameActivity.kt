package curso.tads.mytetrisgame

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import curso.tads.mytetrisgame.databinding.ActivityGameBinding
import curso.tads.mytetrisgame.modeloPeca.*

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding


    private val iniciox = 1
    private val inicioy = 8
    private var jogando = true

    inner class Configuracoes (var velocidade:Long, var linha:Int, var coluna:Int)
    private var configAtual = Configuracoes(300,20,16)

    private lateinit var proximaPeca: Peca
    private lateinit var atualPeca: Peca

    private var board = Array(configAtual.linha) {
        Array(configAtual.coluna){0}
    }

    private var boardView = Array(configAtual.linha){
        arrayOfNulls<ImageView>(configAtual.coluna)
    }
    private var pecaView = Array(6){
        arrayOfNulls<ImageView>(12)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)
        binding.apply {
            gridboard.rowCount = configAtual.linha
            gridboard.columnCount = configAtual.coluna

            newPeca.rowCount = 4
            newPeca.columnCount = 6

            val inflater = LayoutInflater.from(applicationContext)

            for (i in 0 until configAtual.linha) {
                for (j in 0 until configAtual.coluna) {
                    boardView[i][j] = inflater.inflate(R.layout.inflate_image_view, gridboard, false) as ImageView
                    gridboard.addView(boardView[i][j])
                }
            }

            for (i in 0 until 4) {
                for (j in 0 until 6) {
                    pecaView[i][j] = inflater.inflate(R.layout.inflate_image_view, newPeca, false) as ImageView
                    newPeca.addView(pecaView[i][j])
                }
            }
        }
        gameRun()
    }

    private fun gameRun(){
        Thread{
            setConfigGame()
            atualPeca = tipoPeca()
            proximaPeca = tipoPeca()
            while(jogando){
                Thread.sleep(configAtual.velocidade)
                runOnUiThread {
                    //limpa tela
                    limpaTela()
                    binding.left.setOnClickListener {
                        if(toLeft(atualPeca.getPontos())){
                            atualPeca.moveLeft()
                        }
                    }
                    binding.right.setOnClickListener {
                        if(toRight(atualPeca.getPontos())){
                            atualPeca.moveRight()
                        }
                    }
                    binding.baixo.setOnClickListener {
                        configAtual.velocidade = 100
                    }
                    binding.girar.setOnClickListener {
                        girarPeca()
                        Thread.sleep(300)
                    }
                    //move peça atual
                    if(topo(atualPeca.getPontos())){
                        jogando = false

                        val i = Intent(this, GameOverActivity::class.java)
                        i.putExtra("pontuacao", binding.total.text.toString())

                        finish()
                        startActivity(i)
                    }else if(toDown(atualPeca.getPontos())){
                        atualPeca.moveDown()
                        pontuar()
                    }else{
                        armazenarPosicao(atualPeca.getPontos())
                        atualPeca = proximaPeca
                        proximaPeca = tipoPeca()
                        configAtual.velocidade = 300
                        pontuar()

                    }
                    //print peça
                    exibirPeca(atualPeca)
                    exibirProximaPeca()
                }
            }
            //Toast.makeText(applicationContext, "Saiu do laço!", Toast.LENGTH_SHORT).show()
        }.start()

    }

    override fun onStop() {
        super.onStop()
    }

    private fun exibirProximaPeca(){
        for (i in 0 until 4) {
            for (j in 0 until 6) {
                pecaView[i][j]!!.setImageResource(R.drawable.semfundo)
            }
        }
        try {
            proximaPeca.getPontos().forEach {
                pecaView[it.x][it.y-6]!!.setImageResource(R.drawable.white)
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Toast.makeText(this, "Saiu da area da matriz", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setConfigGame(){
        val configSalvas: SharedPreferences = getSharedPreferences("PREFS", MODE_PRIVATE)
        //Toast.makeText(this, dificuldade, Toast.LENGTH_SHORT).show()

        when (configSalvas.getString("dificuldade", "medio").toString()) {
            "facil" -> {
                configAtual.velocidade = 420
            }
            "dificil" -> {
                configAtual.velocidade = 250
            }
            else -> {
                configAtual.velocidade = 300
            }
        }
    }

    private fun tipoPeca():Peca{
        return when((Math.random() * 7).toInt()){
            0 -> Quadrado(iniciox, inicioy)
            1 -> Linha(iniciox, inicioy)
            2 -> Triangulo(iniciox, inicioy)
            4 -> LetraLEsquerda(iniciox, inicioy)
            5 -> LetraSDireita(iniciox, inicioy)
            6 -> LetraSDireita(iniciox, inicioy)
            else -> Quadrado(iniciox, inicioy)
        }
    }

    private fun armazenarPosicao(p:Array<Ponto>){
        p.forEach {
            board[it.x][it.y] = 1
        }
    }

    private fun borda(p: Ponto): Boolean {
        if(p.y < 1 || p.x >= configAtual.linha - 1 || p.y > configAtual.coluna - 2 || p.x < 1){
            return true
        }
        return false
    }

    private fun topo(p: Array<Ponto>):Boolean{
        p.forEach {
            if(it.x==1 && board[it.x+1][it.y] == 1){
                Toast.makeText(applicationContext, "Chegou no topo", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
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
        when(atualPeca){
            is Quadrado -> {
                //peca.rotacionar()
            }
            is Linha -> {
                val pontos = atualPeca.rotacionar()
                if(toLeft(pontos) && toRight(pontos)/* && toDown(pontos)*/) {
                    atualPeca.setPontos(pontos)
                    atualPeca.getPontos().forEach {
                        it.moveUp()
                    }
                    atualPeca.setOrietacaPeca((atualPeca as Linha).orientacao)
                }else{
                    Toast.makeText(this, "Bloqueou", Toast.LENGTH_SHORT).show()
                }
            }
            is Triangulo -> {
                val pontos = atualPeca.rotacionar()
                if(toLeft(pontos) && toRight(pontos)/* && toDown(pontos)*/){
                    atualPeca.setPontos(pontos)
                    atualPeca.getPontos().forEach {
                        it.moveUp()
                    }
                    atualPeca.setOrietacaPeca((atualPeca as Triangulo).orientacao)
                }else{
                    Toast.makeText(this, "Bloqueou", Toast.LENGTH_SHORT).show()
                }
            }
            is LetraSDireita -> {
                val pontos = atualPeca.rotacionar()
                if(toLeft(pontos) && toRight(pontos)/* && toDown(pontos)*/){
                    atualPeca.setPontos(pontos)
                    atualPeca.getPontos().forEach {
                        it.moveUp()
                    }
                    atualPeca.setOrietacaPeca((atualPeca as LetraSDireita).orientacao)
                }else{
                    Toast.makeText(this, "Bloqueou", Toast.LENGTH_SHORT).show()
                }
            }
            is LetraLEsquerda -> {
                val pontos = atualPeca.rotacionar()
                if (toLeft(pontos) && toRight(pontos)/* && toDown(pontos)*/) {
                    atualPeca.setPontos(pontos)
                    atualPeca.getPontos().forEach {
                        it.moveUp()
                    }
                    atualPeca.setOrietacaPeca((atualPeca as LetraLEsquerda).orientacao)
                } else {
                    Toast.makeText(this, "Bloqueou", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pontuar(){
        if(ordenarTabuleiro(checarLinhaCompleta())){
            val total = binding.total.text.toString()
            val somaTotal = total.toInt()+100
            binding.total.text = somaTotal.toString()
        }
    }

    private fun ordenarTabuleiro(linhaCompleta:Int):Boolean{
        if(linhaCompleta == 0){
            return false
        }
        var i = linhaCompleta
        while (i>0){
            for (j in 1 until configAtual.coluna-1){
                board[i][j] = board[i-1][j]
                board[i-1][j] = 0
            }
            i--
        }
        return true
    }

    private fun checarLinhaCompleta():Int{
        var countPeca = 0
        for (i in 1 until configAtual.linha-1) {
            for (j in 1 until configAtual.coluna-1) {
                if(board[i][j] == 1){
                    countPeca++
                }
            }
            if(countPeca == configAtual.coluna-2){
                return i
            }else{
                countPeca = 0
            }
        }
        return 0
    }

    private fun limpaTela(){
        for (i in 0 until configAtual.linha) {
            for (j in 0 until configAtual.coluna) {
                when {
                    borda(Ponto(i, j)) -> {
                        boardView[i][j]!!.setImageResource(R.drawable.gray)
                    }
                    board[i][j] == 1 -> {
                        boardView[i][j]!!.setImageResource(R.drawable.white)
                    }
                    else -> {
                        boardView[i][j]!!.setImageResource(R.drawable.black)
                    }
                }
            }
        }
    }

    private fun exibirPeca(p:Peca){
        try {
            p.getPontos().forEach {
                boardView[it.x][it.y]!!.setImageResource(R.drawable.white)
            }
        }catch (e:ArrayIndexOutOfBoundsException) {
            //se a peça passou das bordas eu vou parar o jogo
            jogando = false
        }
    }
}