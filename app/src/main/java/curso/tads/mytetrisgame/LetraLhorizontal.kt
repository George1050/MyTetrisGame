package curso.tads.mytetrisgame

class LetraLhorizontal(linha:Int, coluna:Int):Peca(
        Ponto(linha+1,coluna-1),
        Ponto(linha+1,coluna),
        Ponto(linha+1,coluna+1),
        Ponto(linha,coluna+1)
    ){

}