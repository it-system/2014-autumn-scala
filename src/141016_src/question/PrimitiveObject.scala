/**
 * it-system
 * 2014/10/16 4限
 * Scalaは、すべてオブジェクト型でできている？
 * ⇛YES 
 * Javaでは、プリミティブ型として扱っていたInt、FloatなどもScalaでは
 * すべてオブジェクト型として扱う
 */

package scala141016.question

object PrimitiveObject {

  def main(args: Array[String]): Unit = {
    var value1:Int = 1
    value1.toString  //オブジェクトなのでメソッドを呼び出せる Int⇛String
    
    val value2:Byte = 10
    value2.toChar  //Byte ⇛ Char
    
  }

} 