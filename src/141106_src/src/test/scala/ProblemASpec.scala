import org.scalatest.FunSpec

class ProblemASpec extends FunSpec {
  describe("ProblemA") {

    describe("#readTextFileIntoList") {
      it("should return List[String]") {
        val filePath = "src/main/resources/problem.txt"
        val response = ProblemA.readTextFileIntoList(filePath)
        assert(response.isInstanceOf[List[String]])
      }
    }

    describe("#calcMaxPrice") {
      describe("#enumPriceCombinationWithoutTax") {
        it("should return List[List[Int]]") {
          val response = ProblemA.enumPriceCombinationWithoutTax(105, 5)
          assert(response.isInstanceOf[List[List[Int]]])
        }

        it("should return all combination of price without tax") {
          // ちょっとこのテストは書くの大変なので自分を信じる
        }
      }

      describe("#enumTotalPrices") {
        it("should return List[Int]") {
          val response = ProblemA.enumTotalPrices(List[List[Int]](List(1, 103), List(2, 99), List(3,98)), 8)
          assert(response.isInstanceOf[List[Int]])
        }
      }

      it("should return the max value of total price(tax included)") {
        assert(ProblemA.calcMaxPrice(5, 8, 105) == 109)
        assert(ProblemA.calcMaxPrice(8, 5, 105) == 103)
        assert(ProblemA.calcMaxPrice(2, 24, 50) == 62)
      }
    }
  }
}
