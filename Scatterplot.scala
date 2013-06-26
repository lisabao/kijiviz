package org.kiji.kijiviz //TODO double-check this; add copyright top comment?

import org.kiji.schema.Kiji
import org.kiji.schema.KijiDataRequest
import org.kiji.schema.KijiRowData
import org.kiji.schema.KijiRowScanner
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

  private val myTableURI = "kiji://.env/default/" //for now, use this default table

  //basic testing
  def testing() {
    getKijiTableData(myTableURI, "tempo", "duration", 1, 50)
  }

  //Get the specified data out of a Kiji table and return it as a Breeze dataset.
  //TODO what is the needed type of a Breeze dataset?
  def getKijiTableData(tableURI, columnX, columnY, rowStart, rowEnd) = {
    Kiji kiji = Kiji.Factory.open(tableURI)


    kiji.release() //Finished with the Kiji table, yay!
  }
}
