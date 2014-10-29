package scala141030

object SampleClassMain2 {

  def main(args: Array[String]): Unit = {
    //基本コンストラクタの呼び出し
    println("基本コンストラクタの呼び出し  new SampleClass2(3,4)")
    new SampleClass2(3,4)
    
    //補助コンストラクタ1の呼び出し
    println("補助コンストラクタの呼び出し  new SampleClass2(9)")
    new SampleClass2(9)
    
  }
}

class SampleClass2(x:Int,y:Int) {
  //基本コンストラクタ
  var sum = x + y;
  println(sum)
  
  //補助コンストラクタ
  def this(z:Int)={
    this(2,z)
  }
  
}