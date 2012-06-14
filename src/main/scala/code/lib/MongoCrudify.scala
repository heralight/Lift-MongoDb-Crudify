package code.lib

import net.liftweb.proto.Crudify
import net.liftweb.record.{Field, Record}
import xml.NodeSeq
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoRecord}
import net.liftweb.common.Box
import com.foursquare.rogue.Rogue._
import net.liftweb.mongodb.record.field.{ObjectIdPk, MongoPk}

/**
 * Heirko project
 * User: Alexandre
 * Date: 14/06/12
 * Time: 16:22
 */



trait  MongoCRUDify[T <: MongoRecord[T] with ObjectIdPk[T]] extends   Crudify {
  self: T with MongoMetaRecord[T]  =>

  type TheCrudType = T

  type FieldPointerType = Field[_, TheCrudType]

  //def table: Table[TheCrudType]

  //def idFromString(in: String): K

  override def calcPrefix = collectionName :: Nil

  override def fieldsForDisplay: List[FieldPointerType] = metaFields.filter(_.shouldDisplay_?)

  override def computeFieldFromPointer(instance: TheCrudType, pointer: FieldPointerType): Box[FieldPointerType] = instance.fieldByName(pointer.name)

  override def findForParam(in: String): Box[TheCrudType] = {
   find(in)
  }

  override def findForList(start: Long, count: Int) = { this.paginate(count).setPage(((start/ count) + 1).toInt).fetch()
       // findAll
  }

  override def create = createRecord

  override def buildBridge(in: TheCrudType) = new SquerylBridge(in)

  protected class SquerylBridge(in: TheCrudType) extends CrudBridge {

    def delete_! =  { delete("_id", in.id.is); true}

    def save = {
     in.save
      true
    }

    def validate = in.validate

    def primaryKeyFieldAsString = in.id.toString
  }

  def buildFieldBridge(from: FieldPointerType): FieldPointerBridge = new SquerylFieldBridge(from)

  protected class SquerylFieldBridge(in: FieldPointerType) extends FieldPointerBridge {
    def displayHtml: NodeSeq = in.displayHtml
  }

}
