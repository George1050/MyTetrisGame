package curso.tads.mytetrisgame.viewModel

import androidx.lifecycle.ViewModel
import curso.tads.mytetrisgame.modeloPeca.Peca
import curso.tads.mytetrisgame.modeloPeca.Ponto

class GameViewModel: ViewModel() {
    private val linha = 20
    private val coluna = 16
    var tabuleiro = Array(linha){
        Array(coluna){0}
    }
    var ultimaPeca: Peca = Peca(Ponto(0,0),Ponto(0,0),Ponto(0,0),Ponto(0,0))
    var pontos = "0"
    var continuar = false
}