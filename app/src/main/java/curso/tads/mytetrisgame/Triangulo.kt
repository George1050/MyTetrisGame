package curso.tads.mytetrisgame

class Triangulo(linha:Int, coluna:Int):Peca(
        Ponto(linha,coluna-1),
        Ponto(linha-1,coluna),
        Ponto(linha,coluna+1),
        Ponto(linha,coluna)
    ){
}