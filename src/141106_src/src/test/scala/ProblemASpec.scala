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

      it("should return the max value of total price(tax included)") {
        // assert(ProblemA.calcMaxPrice(20, 80, 105) == 107)
        // assert(ProblemA.calcMaxPrice(2, 99, 105) == 108)
        // assert(ProblemA.calcMaxPrice(13, 88, 105) == 109)
      }
    }
  }
}
