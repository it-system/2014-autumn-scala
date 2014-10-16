/**
 * it-system
 * 2014/10/16 4限
 * classとobjectの違いは？
 */


//実行用オブジェクト
package scala141016.question

object ObjectClass {
	def main(args: Array[String]): Unit = {
			//objectの使い方
			ObjectTest.f()

			//classの使い方
			new ClassTest().f()

	}
}

//object
object ObjectTest {
	def f()={
		println("object")
	}
}

//class
class ClassTest{
	def f()={
		println("class")	
	}
}
