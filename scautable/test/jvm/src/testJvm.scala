package io.github.quafadas.scautable

import scalatags.Text.all.*
import java.time.LocalDate

import scala.annotation.experimental
import NamedTuple.*
import CSV.*

@experimental
class JVMSuite extends munit.FunSuite {

  import scautable.*
  import scautable.{given}

  test("extendable") {
    given dateT: HtmlTableRender[LocalDate] = new HtmlTableRender[LocalDate] {
      override def tableCell(a: LocalDate) = td(
        s"$a"
      )
    }
    case class Customize(t: LocalDate, i: Int)
    val custom = Seq(Customize(LocalDate.of(2025, 1, 1), 1))
    assertEquals(
      """<table id="scautable" class="display"><thead><tr><th>t</th><th>i</th></tr></thead><tbody><tr><td>2025-01-01</td><td>1</td></tr></tbody></table>""",
      scautable(custom).toString()
    )
  }

  test("csv") {

    val tupleExample: (col1: String, col2: String)  = (col1 = "a", col2 = "b")
    val tupleExample2: (col1: String, col2: String) = NamedTuple.build[("col1", "col2")]()(("a", "b"))

    val c = ("a", "b").withNames[("col1", "col2")]

    val itr = List(tupleExample2, tupleExample, c).iterator
    println(itr.map(_.col1).toArray.mkString(","))

    println(tupleExample.col1)
    println(tupleExample2.col2)
    println(c.col1)

    val csv = CSV.readCsvAsNamedTupleType("C:\\temp\\scautable\\simple.csv")
    // println(csv.drop(1).toArray.mkString("\n"))
    // csv.toList.map(x => x.col1)
    val values = csv.drop(1).toArray
    println(values.map(_.col1.toInt).mkString(","))

    // println(Seq(csv).pt)
    // println(csv.col1)
    // println(csv)
    // val a = NamedTuple.build[csv.type]()[Tuple2[String, String]]("a", "b")
    // val a = NamedTuple.build[csv.TYPE]()[Tuple2[String, String]]("a", "b")

  }
}
