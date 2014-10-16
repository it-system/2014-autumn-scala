/**
 * it-system
 * 2014/10/16 4限
 * 関数定義
 */

package scala141016.introduction

object Fucntion {
  var hello = () =>{
    println("Hello")
  }
  
  var add = (x:Int,y:Int) =>{
    x * y
    x + y
  }
  
  var execute = (f:(Int,Int)=>Int) =>{
    f(10,10)
  }
  
  def main(args: Array[String]): Unit = {
    println(add(3,5))
    hello()
  }
  
  //val1 + val2
  //def add2(val1:Int ,val2:Int):Int = val1 + val2
  
  //helloを出力
  //def hello():Unit = println("Hello!")

}