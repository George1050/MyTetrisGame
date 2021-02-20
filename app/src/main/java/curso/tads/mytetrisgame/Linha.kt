package curso.tads.mytetrisgame

class Linha(linha:Int, coluna:Int, private var orientacao: String = "h"):Peca(
        Ponto(linha,coluna-2),
        Ponto(linha,coluna-1),
        Ponto(linha,coluna),
        Ponto(linha,coluna+1)
    ) {
    override fun rotacionar(){
        var p = getPontos()
        when(orientacao){
                "h" -> {
                    p[0] = Ponto(p[0].x+2, p[0].y+2)
                    p[1] = Ponto(p[1].x+1, p[1].y+1)
                    p[3] = Ponto(p[3].x-1, p[0].y)
                    orientacao = "v"
                    p.forEach {
                        it.moveUp()
                    }
                }
                "v" -> {

                    p[0] = Ponto(p[0].x-2, p[0].y-2)
                    p[1] = Ponto(p[1].x-1, p[1].y-1)
                    p[3] = Ponto(p[3].x+1, p[0].y)
                    orientacao ="h"
                    p.forEach {
                        it.moveUp()
                    }
                }
        }
    }
}