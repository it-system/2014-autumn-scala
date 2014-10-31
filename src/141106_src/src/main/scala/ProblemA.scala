/**
 * ACM2014国内予選のProblem Aを解くプログラム
 *
 * cf. http://icpc.iisf.or.jp/past-icpc/domestic2014/#section_A
 */

import scala.io._

object ProblemA {
  def main(args: Array[String]): Unit = {
    val inputs = readTextFileIntoList("src/main/resources/problem.txt")
    for (line <- inputs) {
      println(line)
    }
  }

  /**
   * ファイルを読み込む関数
   * @param filePath 入力例のサンプルファイルのパス
   * @return テキストファイルを1行ずつ格納した配列
   */
  def readTextFileIntoList(filePath: String): List[String] = {
    val list = Source.fromFile(filePath, "UTF-8").getLines.toList
    return list
  }

  /**
   * 税込合計価格の最大値を計算する関数
   * @param oldTaxRate 旧税率
   * @param newTaxRate 新税率
   * @param oldTotalPrice 税率変更前の税込み合計価格
   * @return 税率変更後の税込合計価格の最大値
   */
  def calcMaxPrice(oldTaxRate: Int, newTaxRate: Int, oldTotalPrice: Int): Int = {
    /**
     * 税込み価格の決定ルール
     *   1. 消費税率が x% のとき, 税抜価格が p 円である商品の税込価格は, p (100+x) / 100   円を小数点以下切り捨てたものである
     *   2. 複数の商品についてまとめて支払う際の税込合計価格は, 個々の商品の税込価格の合計額とする
     */

    // 税込み合計価格がoldTotalPriceになる価格の組み合わせを全通り求める

    // 全組み合わせに対して, 新税率での税込合計価格がいくらになるか求める

    // 最大値を返す

    return 0
  }

  /**
   * 税込み合計価格が引数になるような、税抜き価格の組み合わせを求める関数
   * @param oldTotalPrice 税率変更前の税込み合計価格
   * @return 税抜き価格の組み合わせの配列
   */
  def enumPriceCombination(oldTotalPrice: Int): List[(Int, Int)] = {
    // まず合計がoldTotalPriceになるような整数の組み合わせを列挙する
    // 次に、その組み合わせが実現できる税抜き価格の組み合わせを列挙する
    return List((1, 2), (3, 4))
  }
}
