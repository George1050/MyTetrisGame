package curso.tads.mytetrisgame

class LetraLEsquerda(linha:Int, coluna:Int, var orientacao:String = "horizontalCima"):Peca(
        Ponto(linha+1,coluna-1),
        Ponto(linha+1,coluna),
        Ponto(linha+1,coluna+1),
        Ponto(linha,coluna+1)
    ){

    override fun rotacionar() {
        var p = getPontos()
        when (orientacao){
            "horizontalCima" -> {
                //Está na Horizontal Cima
                p[1].moveDown()
                p[1].moveRight()
                p[0].moveDown()
                p[0].moveRight()
                p[0].moveRight()
                p[0].moveRight()
                p.forEach {
                    it.moveUp()
                }
                orientacao = "verticaDireita"

            }
            "verticaDireita" -> {
                //Está na Horizontal Cima
                p[3].moveDown()
                p[3].moveLeft()
                p[1].moveLeft()
                p[0].moveUp()
                p.forEach {
                    it.moveUp()
                }
                orientacao = "horizontalBaixo"
            }
            "horizontalBaixo" -> {
                p[1].moveRight()
                p[0].moveDown()
                p[0].moveDown()
                p[0].moveLeft()
                orientacao = "verticalEsquerda"
            }
            "verticalEsquerda" -> {
                p[3].moveUp()
                p[3].moveRight()
                p[1].moveLeft()
                p[1].moveUp()
                p[0].moveLeft()
                p[0].moveLeft()
                p[0].moveUp()
                p[0].moveUp()
                orientacao = "horizontalCima"
            }
        }
    }
}