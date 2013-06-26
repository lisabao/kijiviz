package org.kiji.kijiviz //TODO double-check this; add copyright top comment?

import breeze.plot._

import org.kiji.schema.Kiji
import org.kiji.schema.KijiDataRequest
import org.kiji.schema.KijiRowData
import org.kiji.schema.KijiRowScanner
import org.kiji.schema.KijiScannerOptions
import org.kiji.schema.KijiTable
import org.kiji.schema.KijiTableReader
import org.kiji.schema.KijiURI

/**
 * A data visualization tool to display data from Kiji tables in a scatterplot.
 *
 * @param tableURI for the given Kiji table.
 * @param columnX is the user-defined name of the column to plot on the x-axis.
 * @param columnY is the user-defined name of the column to plot on the y-axis.
 * @param rowStart is the user-defined starting row of table data to plot.
 * @param rowEnd is the user-defined ending row of table data to plot.
 */
class Scatterplot (
    tableURI: KijiURI,
    columnX: String, columnY: String,
    rowStart: Int, rowEnd: Int) {

  //Get the specified data out of a Kiji table and return it as a Breeze dataset.
  //TODO what is the needed type of a Breeze dataset?
  def getKijiTableData(tableURI, columnX, columnY, rowStart, rowEnd) = {
    Kiji kiji = Kiji.Factory.open(tableURI)
    KijiTable kijiTable = kiji.openTable("tableName")

    // Define a KDR.
    KijiDataRequestBuilder builder = KijiDataRequest.builder
        .newColumnsDef
        .withMaxVersions(1)
        .add(columnX, columnY)
    KijiDataRequest request = builder.build

    // Get a scanner to iterate over rows.
    KijiTableReader reader = kijiTable.openTableReader
    KijiScannerOptions options = new KijiScannerOptions()
        .setStartRow(rowStart)
        .setEndRow(rowEnd)
    KijiRowScanner scanner = reader.getScanner(request, options)

    // Process KijiRowData into Breeze dataset.
    fromKijiToBreeze(scanner)

    reader.close
    kiji.release //Finished with the Kiji table, yay!
  }

  def fromKijiToBreeze(scanner: KijiRowScanner) = {
    try {
      scanner.foreach { row: KijiRowData =>
          //do stuff to each row
      }
    }
    finally {
      scanner.close
    }
  }

}
