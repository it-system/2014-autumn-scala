package scala141030

object SampleClassMain {

  def main(args: Array[String]): Unit = {
    //基本コンストラクタの呼び出し
    println("基本コンストラクタの呼び出し  new SampleClass")
    new SampleClass
    
    //補助コンストラクタ1の呼び出し
    println("補助コンストラクタ1の呼び出し  new SampleClass(\"Mac\")")
    new SampleClass("Mac")
    
    //補助コンストラクタ2の呼び出し
    println("補助コンストラクタ2の呼び出し  new SampleClass(\"Mac\",\"Windows\")");
    new SampleClass("Mac","Windows")
    
  }

}

class SampleClass {
  //基本コンストラクタ
  println("Linux")
  
  //補助コンストラクタ1
  def this(label1:String)={
    this()
    println(label1);
  }
  
  //補助コンストラクタ2
  def this(label1:String,label2:String)={
    this()
    println(label1)
    println(label2)
  }
}