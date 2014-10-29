package scala141030

object SampleGenerics {

  def main(args: Array[String]): Unit = {
    var obj = new SampleGenericsClass[String]
    obj.setData("111")
    println(obj.getData)
    
    var obj2 = new SampleGenericsMethodClass
    obj2.getData[String]("aaa")
  }

}

//クラスに型パラメータを与えた例
class SampleGenericsClass[T]{
     protected var data:T = _  //メンバ変数の型は、型パラメータTで与えられたもの
     
     def getData():T = data //メソッドの戻り値の型は、型パラメータTで与えられたもの
     def setData(value:T):Unit = data = value 
}

//メソッドに型パラメータを与えた例
class SampleGenericsMethodClass{
  def getData[T](data:T):T={
    data
  }
}
