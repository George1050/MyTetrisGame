package curso.tads.mytetrisgame

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import curso.tads.mytetrisgame.databinding.ActivityGameBinding
import curso.tads.mytetrisgame.modeloPeca.*
import curso.tads.mytetrisgame.viewModel.GameViewModel

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    private val iniciox = 1
    private val inicioy = 8
    private var jogando = true
    private var requestCode = 1

    inner class Configuracoes (var velocidade:Long, var linha:Int, var coluna:Int)
    private var configAtual: Configuracoes = Configuracoes(300,20,16)

    private lateinit var gameViewModel: GameViewModel

    private var boardView = Array(configAtual.linha){
        arrayOfNulls<ImageView>(configAtual.coluna)
    }
    private var pecaView = Array(6){
        arrayOfNulls<ImageView>(12)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

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

            //
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
        if(gameViewModel.continuar){
            gameViewModel.continuar = false
        }else{
            tipoPeca().also { gameViewModel.peca = it }
            tipoPeca().also { gameViewModel.proximaPeca = it }
        }
        Thread{
            //Pega as configurações padrão ou a que o úsuario escolheu
            setConfigGame()
            while(jogando){
                Thread.sleep(configAtual.velocidade)
                runOnUiThread {
                    limpaTela()
                    binding.left.setOnClickListener {
                        if(toLeft(gameViewModel.peca.getPontos())){
                            gameViewModel.peca.moveLeft()
                        }
                    }

                    binding.right.setOnClickListener {
                        if(toRight(gameViewModel.peca.getPontos())){
                            gameViewModel.peca.moveRight()
                        }
                    }

                    binding.baixo.setOnClickListener {
                        configAtual.velocidade = 100
                    }

                    binding.girar.setOnClickListener {
                        girarPeca()
                        Thread.sleep(200)
                    }

                    binding.pausa.setOnClickListener {
                        pausarJogo()
                    }

                    //Verifica se a peça atual chegou no topo, chama a activity gameover
                    when {
                        topo(gameViewModel.peca.getPontos()) -> {
                            this.jogando = false
                            val i = Intent(this, GameOverActivity::class.java)
                            val pontos = binding.total.text.toString().toInt()
                            i.putExtra("pontuacao", pontos.toString())
                            verificarRecord(pontos)
                            Toast.makeText(this, "@string/game_over", Toast.LENGTH_SHORT).show()
                            startActivityForResult(i, 0)
                            finish()
                        }
                        //Se a peça pode descer
                        toDown(gameViewModel.peca.getPontos()) -> {
                            gameViewModel.peca.moveDown()
                            //verifica se alguma linha está completa
                            pontuar()
                        }
                        //Senão, armazena a posição atual e cria novas peças
                        else -> {
                            armazenarPosicao(gameViewModel.peca.getPontos())
                            gameViewModel.peca = gameViewModel.proximaPeca
                            gameViewModel.proximaPeca = tipoPeca()
                            configAtual.velocidade = 300
                            pontuar()
                        }
                    }
                    //print peça
                    exibirPeca()
                    exibirProximaPeca()
                }
            }
        }.start()
    }

    private fun pausarJogo() {
        gameViewModel.pontos = binding.total.text.toString()
        gameViewModel.continuar = true

        val i = Intent(this, MainActivity::class.java)
        i.putExtra("continuar", true)
        startActivityForResult(i, requestCode)
    }

    private fun verificarRecord(pontos: Int) {
        val pref: SharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        if (pref.getInt("record", 0) < pontos) {
            pref.edit().putInt("record", pontos).apply()
        }
    }

    private fun exibirProximaPeca(){
        for (i in 0 until 4) {
            for (j in 0 until 6) {
                pecaView[i][j]!!.setImageResource(R.drawable.semfundo)
            }
        }
        try {
            gameViewModel.proximaPeca.getPontos().forEach {
                pecaView[it.x][it.y-6]!!.setImageResource(R.drawable.white)
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Toast.makeText(this, "Saiu da area da matriz", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setConfigGame(){
        val pref: SharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        when (pref.getString("dificuldade", "medio").toString()) {
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
        return when((Math.random() * 8).toInt()){
            0 -> Quadrado(iniciox, inicioy)
            1 -> Linha(iniciox, inicioy)
            2 -> Triangulo(iniciox, inicioy)
            4 -> LetraLEsquerda(iniciox, inicioy)
            5 -> LetraLDireita(iniciox,inicioy)
            6 -> LetraSEsquerda(iniciox, inicioy)
            7 -> LetraSDireita(iniciox, inicioy)
            else -> Quadrado(iniciox, inicioy)
        }
    }

    private fun armazenarPosicao(p:Array<Ponto>){
        p.forEach {
            gameViewModel.tabuleiro[it.x][it.y] = 1
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
            if(it.x==1 && gameViewModel.tabuleiro[it.x+2][it.y] == 1){
                return true
            }
        }
        return false
    }

    private fun posicaoInvalida(p: Ponto): Boolean {
        if(
                (gameViewModel.tabuleiro[p.x][p.y-1] == 1 && gameViewModel.tabuleiro[p.x+1][p.y] == 1) ||
                (gameViewModel.tabuleiro[p.x][p.y+1] == 1 && gameViewModel.tabuleiro[p.x+1][p.y] == 1) || (gameViewModel.tabuleiro[p.x+1][p.y] == 1)
        ){
            return true
        }
        return false
    }

    private fun toLeft(p:Array<Ponto>): Boolean{
        p.forEach {
            if(gameViewModel.tabuleiro[it.x][it.y-1] == 1 || borda(Ponto(it.x, it.y-1))){
                return false
            }
        }
        return true
    }

    private fun toRight(p:Array<Ponto>): Boolean{
        p.forEach {
            if(gameViewModel.tabuleiro[it.x][it.y+1] == 1 || borda(Ponto(it.x, it.y+1))){
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
        when(gameViewModel.peca){
            is Quadrado -> {
                //peca.rotacionar()
            }
            is Linha -> {
                val pontos = gameViewModel.peca.rotacionar()
                if(toLeft(pontos) && toRight(pontos)/* && toDown(pontos)*/) {
                    gameViewModel.peca.setPontos(pontos)
                    gameViewModel.peca.setOrietacaoPeca()
                }
            }
            is Triangulo -> {
                val pontos = gameViewModel.peca.rotacionar()
                if(toLeft(pontos) && toRight(pontos)/* && toDown(pontos)*/){
                    gameViewModel.peca.setPontos(pontos)
                    gameViewModel.peca.setOrietacaoPeca()
                }
            }
            is LetraSEsquerda -> {
                val pontos = gameViewModel.peca.rotacionar()
                if(toLeft(pontos) && toRight(pontos)/* && toDown(pontos)*/){
                    gameViewModel.peca.setPontos(pontos)
                    gameViewModel.peca.setOrietacaoPeca()
                }
            }
            is LetraSDireita -> {
                val pontos = gameViewModel.peca.rotacionar()
                if(toLeft(pontos) && toRight(pontos)/* && toDown(pontos)*/){
                    gameViewModel.peca.setPontos(pontos)
                    gameViewModel.peca.setOrietacaoPeca()
                }
            }
            is LetraLEsquerda -> {
                val pontos = gameViewModel.peca.rotacionar()
                if (toLeft(pontos) && toRight(pontos)/* && toDown(pontos)*/) {
                    gameViewModel.peca.setPontos(pontos)
                    gameViewModel.peca.setOrietacaoPeca()
                }
            }
            is LetraLDireita -> {
                val pontos = gameViewModel.peca.rotacionar()
                if (toLeft(pontos) && toRight(pontos)/* && toDown(pontos)*/) {
                    gameViewModel.peca.setPontos(pontos)
                    gameViewModel.peca.setOrietacaoPeca()
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
                gameViewModel.tabuleiro[i][j] = gameViewModel.tabuleiro[i-1][j]
                gameViewModel.tabuleiro[i-1][j] = 0
            }
            i--
        }
        return true
    }

    private fun checarLinhaCompleta():Int{
        var countPeca = 0
        for (i in 1 until configAtual.linha-1) {
            for (j in 1 until configAtual.coluna-1) {
                if(gameViewModel.tabuleiro[i][j] == 1){
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
                    gameViewModel.tabuleiro[i][j] == 1 -> {
                        boardView[i][j]!!.setImageResource(R.drawable.white)
                    }
                    else -> {
                        boardView[i][j]!!.setImageResource(R.drawable.black)
                    }
                }
            }
        }
    }

    private fun exibirPeca(){
        try {
            gameViewModel.peca.getPontos().forEach {
                boardView[it.x][it.y]!!.setImageResource(R.drawable.white)
            }
        }catch (e:ArrayIndexOutOfBoundsException) {
            //se a peça passou das bordas eu vou parar o jogo
            jogando = false
        }
    }

    override fun onPause() {
        super.onPause()
        jogando = false
    }

    override fun onResume() {
        super.onResume()
        if(gameViewModel.continuar){
            Log.i("TESTE", "continuando")
            jogando = true
            gameRun()
        }
    }
}

