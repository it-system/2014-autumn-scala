import scala.collection.mutable.ListBuffer;
import scala.util.control.Breaks.{break, breakable}

object Sample extends App {
  // 文字列内に a がいくつあるか数える
  def mCountLowerA(str:String):Int = {
    val regex = "a".r
    return regex.findAllIn(str).length
  }

  println(mCountLowerA("asdfgjhkjlloqwtafsbaj"))

  val fCountLowerA:(String) => Int = (str:String) => {
    val regex = "a".r
    regex.findAllIn(str).length
  }

  println(fCountLowerA("asdfgjhkjlloqwtafsbaj"))

  // ひたすらフィボナッチ数を計算する
  val calcFib:((BigInt) => Unit) => Unit = (callback:(BigInt) => Unit) => {
    var fibs = ListBuffer[BigInt](0,1,1)
    while(fibs.head >= 0) {
      callback(fibs.head)
      fibs = fibs.tail
      fibs += (fibs(0) + fibs(1))
    }
  }

  calcFib(n => println(n))
}
