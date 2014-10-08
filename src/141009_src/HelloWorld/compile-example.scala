/**
 * コンパイルして実行するサンプルファイル
 */
object HelloWorld {
  def main(args:Array[String]) {
    // 文字列の出力
    println("Hello, Scala World!!")

    // 変数に代入してから出力
    val msg = "日本語もだいじょうぶ！"
    println(msg)

    // scalaっぽいことをしてみる
    val result = List(1, 2, 3).reverse
    println(result)

    // 変数の書き換え
    var changeable = "before"
    println(changeable)
    changeable = "after"
    println(changeable)
  }
}
