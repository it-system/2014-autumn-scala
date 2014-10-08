/**
 * Appトレイトを使ったサンプルファイル
 *   mainメソッドの定義を省略できる
 *   トレイトは、とりあえずそういうものなんだという認識でOK
 */
object AppTraitSample extends App {
  require(args.length > 0, "引数が1つ以上必要")

  val name: String = args(0)
  println("こんにちは, %s さん".format(name))
}
