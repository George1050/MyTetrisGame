package curso.tads.mytetrisgame

class Triangulo(linha:Int, coluna:Int, var orientacao:String = "horizontalCima"):Peca(
        Ponto(linha,coluna-1),
        Ponto(linha-1,coluna),
        Ponto(linha,coluna),
        Ponto(linha,coluna+1)
    ){

    override fun rotacionar() {
        var p = getPontos()
        when (orientacao){
            //Está na Horizontal Cima
            "horizontalCima" -> {
                p[0].moveDown()
                p[0].moveRight()
                p.forEach {
                    it.moveUp()
                }
                orientacao = "verticaDireita"
            }
            //Está na vertical direita
            "verticaDireita" -> {
                p[1].moveDown()
                p[1].moveLeft()
                p.forEach {
                    it.moveUp()
                }
                orientacao = "horizontalBaixo"
            }
            //Está na horizontal baixo
            "horizontalBaixo" -> {
                p[1].moveUp()
                p[1].moveRight()
                p[3].moveLeft()
                p[3].moveLeft()
                p.forEach {
                    it.moveUp()
                }
                orientacao = "verticalEsquerda"
            }
            "verticalEsquerda" -> {
                p[3].moveRight()
                p[3].moveRight()
                p[0].moveLeft()
                p[0].moveUp()
                p.forEach {
                    it.moveUp()
                }
                orientacao = "horizontalCima"
            }
        }
    }
}