package scala141030

abstract class SampleAbstractClass {
   protected var name:String //メンバ変数
   
   def aaa(x:Int,y:Int):Int //メソッドの定義のみ、処理は書かない
   def bbb():String = "Hello!" //メソッドの定義と「Hello!」という文字列を返すメソッド

}