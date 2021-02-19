package curso.tads.mytetrisgame

class LetraShorizontal(linha:Int, coluna:Int):Peca(
        Ponto(linha,coluna),
        Ponto(linha,coluna+1),
        Ponto(linha+1,coluna),
        Ponto(linha+1,coluna-1)
    ){



}