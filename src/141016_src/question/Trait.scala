/**
 * it-system
 * 2014/10/16 4限
 * taritってなに？
 * ⇛Javaのインターフェースのようなもの
 * ⇛メソッド定義と実装したメソッドも扱える
 * ⇛多重継承が可能
 */
package scala141016.question

//実行オブジェクト
object Trait {
	def main(args: Array[String]): Unit = {
	   //class1
	   println("---- traitAを実装したクラス ------");
	   var cls1:Class1 = new Class1()
	   cls1.f1
	   
	   //class2
	   println("---- traitAとBを実装したクラス ------");
	   var cls2:Class2 = new Class2()
	   cls2.f1; cls2.f2
	}
}

//trait A を実装したクラス
class Class1 extends A{
	def f1()={
		println("f1")
	}
}

//trait AとBを実装したクラス
class Class2 extends A with B{
   def f1() ={
     println("f1");
   }
}
//トレイトA
trait A{
	def f1() //メソッド宣言のみ  
}
//トレイトB
trait B{
	def f2():Unit = { //メソッド実装
			println("f2") 
	}
}