package controllers //TODO double-check

//import org.kiji.mapreduce.lib.bulkimport.CSVBulkImporter
import org.kiji.schema.EntityId
import org.kiji.schema.Kiji
import org.kiji.schema.KijiDataRequest
import org.kiji.schema.KijiRowData
import org.kiji.schema.KijiRowScanner
import org.kiji.schema.KijiTable
import org.kiji.schema.KijiTableReader
import org.kiji.schema.KijiTableWriter
import org.kiji.schema.KijiURI
//import org.kiji.schema.util.InstanceBuilder
import org.scalatest.FunSuite

class ScatterplotSuite extends FunSuite {
  val tableURI: KijiURI = KijiURI.newBuilder("kiji://.env/default/").build
  val rows: Int = 10
  val familyX: String = "info"
  val columnX: String = "xaxis"
  val familyY: String = "info"
  val columnY: String = "yaxis"

  def makeKijiTable(tableURI: KijiURI, familyX: String, columnX: String,
      familyY: String, columnX: String) {
    val kiji: Kiji = Kiji.Factory.open(tableURI)
    val kijiTable: KijiTable = kiji.openTable(tableURI.getTable)
    val writer: KijiTableWriter = kijiTable.openTableWriter()
    val id1: EntityId = new EntityId()
    val id2: EntityId = new EntityId()
    writer.put(id1, "info", "xaxis", "1")
    writer.put(id1, "info", "yaxis", "1")
    writer.put(id2, "info", "xaxis", "2")
    writer.put(id2, "info", "yaxis", "2")
    writer.close()
//    val instance: Kiji = new InstanceBuilder(kiji)
//      .withTable("table", layout)
//        .withRow(id1)
//          .withFamily("info")
//            .withQualifier("xaxis").withValue(1L, 1) //see InstanceBuilder in kiji-schema
  }

  test("Scatterplot initializes KijiTableReader") {
    Scatterplot.getKijiTableData(tableURI, rows, familyX, columnX, familyY, columnY)
  }
}
