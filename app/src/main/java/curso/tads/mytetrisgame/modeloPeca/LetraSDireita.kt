package curso.tads.mytetrisgame.modeloPeca

class LetraSDireita(linha:Int, coluna:Int, var orientacao:Int = 1): Peca(
        Ponto(linha,coluna+1),
        Ponto(linha,coluna),
        Ponto(linha+1,coluna),
        Ponto(linha+1,coluna-1)
    ){
    override fun rotacionar():Array<Ponto> {
        val p = getPontos()
        when (orientacao) {
            1 -> {
                //Está na Horizontal Cima -> vertical direita
                val tempPeca = arrayOf(
                        Ponto(p[0].x+1, p[0].y),
                        Ponto(p[1].x, p[1].y),
                        Ponto(p[2].x, p[2].y),
                        Ponto(p[3].x+1, p[3].y+2),
                )
                tempPeca.forEach {
                    it.moveLeft()
                }
                return tempPeca
            }
            2 -> {
                //Está na Vertical Direita -> Horizontal baixo
                val tempPeca = arrayOf(
                        Ponto(p[0].x-1, p[0].y),
                        Ponto(p[1].x, p[1].y),
                        Ponto(p[2].x, p[2].y),
                        Ponto(p[3].x-1, p[3].y-2),
                )
                return tempPeca
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
                orientacao = 1
            }
        }
    }
}