package curso.tads.mytetrisgame.modeloPeca

class LetraLDireita(linha:Int, coluna:Int): Peca(
        Ponto(linha+1,coluna+1),
        Ponto(linha+1,coluna),
        Ponto(linha+1,coluna-1),
        Ponto(linha,coluna-1)
    ){
    private var orientacao:Int = 1
    override fun rotacionar():Array<Ponto> {
        val p = getPontos()
        when (orientacao){
            1 -> {
                //Está na Horizontal Cima
                //orientacao = 2
                return arrayOf(
                        Ponto(p[0].x,p[0].y),
                        Ponto(p[1].x, p[1].y),
                        Ponto(p[2].x+1, p[2].y+1),
                        Ponto(p[3].x+3, p[3].y+1))
            }
            2 -> {
                //Está na Horizontal Cima
                //orientacao = 3
                return arrayOf(
                        Ponto(p[0].x,p[0].y),
                        Ponto(p[1].x, p[1].y),
                        Ponto(p[2].x-1, p[2].y-1),
                        Ponto(p[3].x-1, p[3].y-1))
            }
            3 -> {
                //Está na Vertical
                //orientacao = 4
                return arrayOf(
                        Ponto(p[0].x+1, p[0].y-1),
                        Ponto(p[1].x, p[1].y+1),
                        Ponto(p[2].x, p[2].y),
                        Ponto(p[3].x+1, p[3].y+1))
            }
            4 -> {
                //orientacao = 1
                return arrayOf(
                        Ponto(p[0].x-2, p[0].y+1),
                        Ponto(p[1].x, p[1].y),
                        Ponto(p[2].x, p[2].y),
                        Ponto(p[3].x-2, p[3].y-1))
            }
        }
        return arrayOf()
    }

    override fun setOrietacaoPeca() {
        when(orientacao){
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