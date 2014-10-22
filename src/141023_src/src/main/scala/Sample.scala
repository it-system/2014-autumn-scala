object Sample extends App {
  // オーソドックスな関数
  val add:(Int, Int) => Int = (x:Int, y:Int) => {
    x + y
  }

  println(add(1, 2))
}
