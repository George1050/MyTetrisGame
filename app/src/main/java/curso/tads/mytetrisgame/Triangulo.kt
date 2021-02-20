package curso.tads.mytetrisgame

import java.security.cert.PolicyNode

class Triangulo(linha:Int, coluna:Int, var orientacao:Int = 1):Peca(
        Ponto(linha,coluna-1),
        Ponto(linha-1,coluna),
        Ponto(linha,coluna),
        Ponto(linha,coluna+1)
    ){

    override fun rotacionar():Array<Ponto> {
        var p = getPontos()
        when (orientacao){
            //Está na Horizontal Cima
            1 -> {
                orientacao = 2
                return arrayOf(Ponto(p[0].x+1, p[0].y+1),p[1],p[2],p[3])
            }
            //Está na vertical direita
            2 -> {
                orientacao = 3
                return arrayOf(p[0],Ponto(p[1].x+1, p[1].y-1),p[2],p[3])
            }
            //Está na horizontal baixo
            3 -> {
                orientacao = 4
                return arrayOf(p[0], Ponto(p[1].x-1, p[1].y+1), p[2], Ponto(p[3].x, p[3].y-2))
            }
            4 -> {
                orientacao = 1
                return arrayOf(Ponto(p[0].x-1, p[0].y-1),p[1],p[2], Ponto(p[3].x, p[3].y+2))
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