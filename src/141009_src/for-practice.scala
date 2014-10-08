/**
 * for文の書き方が特徴的なのでサンプル
 */

// これはエラー
// for (var i = 0; i < 5; i++) {
//   println(i)
// }
println("--- 1 to 5 ---")
for (i <- 1 to 5) {
  println(i)
}

println("--- 1 until 5 ---")
for (i <- 1 until 5) {
  println(i)
}

println("--- foreach ---")
(1 to 5) foreach { i =>
  println(i)
}

println("--- 逆順 ---")
for (i <- 5 to 1 by -1) {
  println(i)
}
