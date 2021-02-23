package curso.tads.mytetrisgame.modeloPeca

class Triangulo(linha:Int, coluna:Int, var orientacao:Int = 1): Peca(
        Ponto(linha+1,coluna-1),
        Ponto(linha,coluna),
        Ponto(linha+1,coluna),
        Ponto(linha+1,coluna+1)
    ){

    override fun rotacionar():Array<Ponto> {
        val p = getPontos()
        when (orientacao){
            //Está na Horizontal Cima
            1 -> {
                return arrayOf(
                        Ponto(p[0].x+1, p[0].y+1),
                        Ponto(p[1].x, p[1].y),
                        Ponto(p[2].x, p[2].y),
                        Ponto(p[3].x, p[3].y))
            }
            //Está na vertical direita
            2 -> {
                return arrayOf(
                        Ponto(p[0].x, p[0].y),
                        Ponto(p[1].x+1, p[1].y-1),
                        Ponto(p[2].x, p[2].y),
                        Ponto(p[3].x, p[3].y))
            }
            //Está na horizontal baixo
            3 -> {
                return arrayOf(
                        Ponto(p[0].x, p[0].y),
                        Ponto(p[1].x-1, p[1].y+1),
                        Ponto(p[2].x, p[2].y),
                        Ponto(p[3].x, p[3].y-2))
            }
            4 -> {
                return arrayOf(
                        Ponto(p[0].x-1, p[0].y-1),
                        Ponto(p[1].x, p[1].y),
                        Ponto(p[2].x, p[2].y),
                        Ponto(p[3].x, p[3].y+2))
            }
        }
        return arrayOf()
    }
    override fun setOrietacaPeca(o:Int){
        when(o){
            1 -> {
                orientacao = 2
            }
            2 -> {
                orientacao = 3
            }
            3 -> {
                orientacao = 4
            }
            4 -> {
                orientacao = 1
            }
        }
    }
}