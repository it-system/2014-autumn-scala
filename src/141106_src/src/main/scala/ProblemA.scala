/**
 * ACM2014国内予選のProblem Aを解くプログラム
 *
 * cf. http://icpc.iisf.or.jp/past-icpc/domestic2014/#section_A
 */

import scala.io._
import math._

object ProblemA {
  def main(args: Array[String]): Unit = {
    // val inputs = readTextFileIntoList("src/main/resources/problem.txt")
    // for (line <- inputs) {
    //   println(line)
    // }

    calcMaxPrice(5, 8, 105)
  }

  /**
   * ファイルを読み込む関数
   * @param filePath 入力例のサンプルファイルのパス
   * @return テキストファイルを1行ずつ格納した配列
   */
  def readTextFileIntoList(filePath: String): List[String] = {
    Source.fromFile(filePath, "UTF-8").getLines.toList
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
    val priceCombinationWithoutTax = enumPriceCombinationWithoutTax(oldTotalPrice, oldTaxRate)
    println(priceCombinationWithoutTax)

    // 全組み合わせに対して, 新税率での税込合計価格がいくらになるか求める

    // 最大値を返す

    return 0
  }

  /**
   * 税込み合計価格が引数になるような、税抜き価格の組み合わせを求める関数
   * @param taxIncludedTotalPrice 税込み合計価格
   * @return 税抜き価格の組み合わせの配列
   */
  def enumPriceCombinationWithoutTax(taxIncludedTotalPrice: Int, oldTaxRate: Int): List[List[Int]] = {
    //TOOD: 別のメソッドに切り分ける
    val combTaxIncluded = {(n: Int) => {
      var combination = List.empty[List[Int]]
      (1 to (n-1)/2).foreach({ i =>
        // 逆順になるが、Listはデータを先頭に追加した方が処理が早い
        combination = List(i, n-i) :: combination
      })
      combination
    }: List[List[Int]] }.apply(taxIncludedTotalPrice)

    //TOOD: 別のメソッドに切り分ける
    val combPriceWithoutTax = {(list: List[List[Int]], taxRate: Int) => {
      // この計算は別のメソッドでも使うかもしれない、使う場合は外に出す
      def calcTaxIncludedPrice(p: Int): Int = {
        p * (100 + taxRate) / 100
      }

      def calcPriceWithoutTax(p: Int): Int = {
        ceil(p * 100.0 / (100 + taxRate)).toInt
      }

      var combination = List.empty[List[Int]]
      list.foreach({ c =>
        //TODO: cのlengthが2以外だった場合のエラー処理を入れる
        val item1 = calcPriceWithoutTax(c(0))
        val item2 = calcPriceWithoutTax(c(1))

        if (calcTaxIncludedPrice(item1) == c(0) && calcTaxIncludedPrice(item2) == c(1)) {
          combination = List(item1, item2) :: combination
        }
      })
      // ここでreturnを書くと即時関数の中じゃなくて enumPriceCombinationWithoutTax のreturnとして解釈される(要調査)
      combination
    }: List[List[Int]] }.apply(combTaxIncluded, oldTaxRate)

    combPriceWithoutTax
  }
}
