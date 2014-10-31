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
      it("should return the max value of total price(tax included)") {
        assert(ProblemA.calcMaxPrice(20, 80, 105) == 107)
        assert(ProblemA.calcMaxPrice(2, 99, 105) == 108)
        assert(ProblemA.calcMaxPrice(13, 88, 105) == 109)
      }
    }
  }
}
