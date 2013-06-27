package controllers //TODO double-check this; add copyright top comment?

//import breeze.plot._
import play.api._
import play.api.mvc._

import org.kiji.schema.Kiji
import org.kiji.schema.KijiDataRequest
import org.kiji.schema.KijiDataRequestBuilder
import org.kiji.schema.KijiRowData
import org.kiji.schema.KijiRowScanner
import org.kiji.schema.KijiTable
import org.kiji.schema.KijiTableReader
import org.kiji.schema.KijiURI

/**
 * A data visualization tool to display data from Kiji tables in a scatterplot.
 * Currently the user must specify columns containing Integers only.
 *
 * @param tableURI for the given Kiji table.
 * @param rows is the total number of rows (scatterplot points) to process.
 * @param familyX is the family of the column to plot on the x-axis.
 * @param columnX is the qualifier of the column to plot on the x-axis.
 * @param familyY is the family of the column to plot on the y-axis.
 * @param columnY is the user-defined name of the column to plot on the y-axis.
 */
object Scatterplot extends Controller {

  //Get the specified data out of a Kiji table and return it as a Breeze dataset.
  def getKijiTableData(tableURI: KijiURI, rows: Int, familyX: String, columnX: String,
      familyY: String, columnY: String) = {
    // Open a Kiji table.
    val kiji: Kiji = Kiji.Factory.open(tableURI)
    val kijiTable: KijiTable = kiji.openTable(tableURI.getTable)

    // Define a KijiDataRequest (KDR).
    val builder: KijiDataRequestBuilder = KijiDataRequest.builder
    builder.newColumnsDef.withMaxVersions(1).add(familyX, columnX)
    builder.newColumnsDef.withMaxVersions(1).add(familyY, columnY)
    val request: KijiDataRequest = builder.build()

    // Get a scanner to iterate over rows.
    val reader: KijiTableReader = kijiTable.openTableReader()
    val scanner: KijiRowScanner = reader.getScanner(request)

    // Process KijiRowData into Breeze dataset.
    val arrays: Tuple2[Array[Int], Array[Int]] =
        fromKijiToBreeze(scanner, rows, familyX, columnX, familyY, columnY)

    reader.close()
    kijiTable.release() // Finished with the Kiji table, yay!

    printScatterplot(arrays._1, arrays._2)
  }

  def fromKijiToBreeze(scanner: KijiRowScanner, rows: Int, familyX: String,
      columnX: String, familyY: String, columnY: String): Tuple2[Array[Int], Array[Int]] = {
    val iter = scanner.iterator
    var arrayX: Array[Int] = Array(rows)
    var arrayY: Array[Int] = Array(rows)

    // Assumes that the user has requested an existent number of rows.
    for (i <- 0 until rows - 1) {
      val row: KijiRowData = iter.next
      arrayX(i) = row.getMostRecentValue(familyX, columnX)
      arrayY(i) = row.getMostRecentValue(familyY, columnY)
    }
    scanner.close()

    new Tuple2(arrayX, arrayY)
  }

  def printScatterplot(arrayX: Array[Int], arrayY: Array[Int]) {
    val size = 1
    Plot.scatter(arrayX, arrayY, size) //TODO figure out how to import Breeze correctly
    // See example: https://gist.github.com/dramage/1288473
    // See source code:
    // https://github.com/scalanlp/breeze/blob/master/viz/src/main/scala/breeze/plot/package.scala
  }

}
