package curso.tads.mytetrisgame

class Linha(linha:Int, coluna:Int):Peca(
        Ponto(linha,coluna-2),
        Ponto(linha,coluna-1),
        Ponto(linha,coluna),
        Ponto(linha,coluna+1)
    ) {

}