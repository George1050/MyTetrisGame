package curso.tads.mytetrisgame

class LetraLEsquerda(linha:Int, coluna:Int, var orientacao:Int = 1):Peca(
        Ponto(linha+1,coluna-1),
        Ponto(linha+1,coluna),
        Ponto(linha+1,coluna+1),
        Ponto(linha,coluna+1)
    ){

    override fun rotacionar():Array<Ponto> {
        var p = getPontos()
        when (orientacao){
            1 -> {
                //Está na Horizontal Cima
                //orientacao = 2
                return arrayOf(Ponto(p[0].x+1,p[0].y+3), Ponto(p[1].x+1, p[1].y+1), p[2],p[3])
            }
            2 -> {
                //Está na Horizontal Cima
                //orientacao = 3
                return arrayOf(Ponto(p[0].x-1,p[0].y), Ponto(p[1].x, p[1].y-1),p[2], Ponto(p[3].x+1, p[3].y-1))
            }
            3 -> {
                //orientacao = 4
                return arrayOf(Ponto(p[0].x+2, p[0].y-1), Ponto(p[1].x, p[1].y+1), p[2],p[3])
            }
            4 -> {
                //orientacao = 1
                return arrayOf(Ponto(p[0].x-2, p[0].y-2), Ponto(p[1].x-1, p[1].y-1), p[2], Ponto(p[3].x-1, p[3].y+1))
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