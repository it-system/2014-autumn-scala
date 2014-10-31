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
        assert(ProblemA.calcMaxPrice(5, 8, 105) == 109)
      }
    }
  }
}
