package curso.tads.mytetrisgame

class Ponto (var x:Int, var y:Int){
    fun moveDown(){
        x++
    }
    fun moveRight(){
        y++
    }
    fun moveLeft(){
        y--
    }

    fun moveUp(){
        x--
    }
}