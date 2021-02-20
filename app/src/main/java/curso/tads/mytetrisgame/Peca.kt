package curso.tads.mytetrisgame

open class Peca(pontoA: Ponto,pontoB: Ponto, pontoC: Ponto, pontoD: Ponto) {
    private var pontos = arrayOf(
            Ponto(pontoA.x, pontoA.y),
            Ponto(pontoB.x, pontoB.y),
            Ponto(pontoC.x, pontoC.y),
            Ponto(pontoD.x, pontoD.y)
    )

    open fun moveDown(){
        pontos.forEach { it.moveDown() }
    }
    open fun moveRight(){
        pontos.forEach { it.moveRight() }

    }
    open fun moveLeft(){
        pontos.forEach { it.moveLeft() }
    }

    open fun rotacionar():Array<Ponto>{
        return arrayOf()
    }

    open fun setOrietacaPeca(o:Int){}

    fun getPontos(): Array<Ponto> {
        return pontos
    }

    fun setPontos(p:Array<Ponto>){
        pontos = p
    }
}