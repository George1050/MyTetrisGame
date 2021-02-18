package curso.tads.mytetrisgame

class Linha(linha:Int, coluna:Int):Peca(linha, coluna) {
    var pontos = arrayListOf<Peca>(
            Peca(linha,coluna-2),
            Peca(linha,coluna-1),
            Peca(linha,coluna),
            Peca(linha,coluna+1)
    )

    override fun moveDown(){
        pontos.forEach { it.x++ }
    }

    override fun moveLeft(){
        pontos.forEach { it.y-- }
    }

    override fun moveRight(){
        pontos.forEach { it.y++ }
    }
}