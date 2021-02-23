package curso.tads.mytetrisgame.modeloPeca

class Quadrado(linha:Int, coluna:Int, var orientacao:Int = 1): Peca(
        Ponto(linha,coluna),
        Ponto(linha,coluna+1),
        Ponto(linha+1,coluna),
        Ponto(linha+1,coluna+1)
    ){

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