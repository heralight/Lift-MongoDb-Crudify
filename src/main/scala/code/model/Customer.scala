package code.model

import net.liftweb.mongodb.record.field.ObjectIdPk
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoRecord}
import net.liftweb.record.field._
import code.lib.{MongoCRUDify, BaseService}
import java.text.SimpleDateFormat
import xml.{ Text, NodeSeq}
import java.util.Date
import net.liftweb.util.Helpers._
import bootstrap.liftweb.MenuGroups



class Customer private() extends MongoRecord[Customer] with ObjectIdPk[Customer] {
  def meta = Customer

  object startup extends DateTimeField(this) {
     override def asHtml: NodeSeq = Text(valueBox.map(s => toInternetDate(s.getTime)) openOr "")

    /** @return a SimpleDateFormat in the format "MM/dd/yyyy" **/
    val printDateFormatter = new SimpleDateFormat("MM/dd/yyyy")

    /** @return a date formatted with the  date format */
    def printDate(in: Date) : String = printDateFormatter.format(in)

    /** @return a date formatted with the date format (from a number of
millis) */
    def printDate(in: Long) : String = printDateFormatter.format(new Date
    (in))

    /** @return a date formatted with the date format (from a number of
millis) */
    def parseDate(dateString: String) : Date = printDateFormatter.parse(dateString)
  }
  object shutdown extends OptionalDateTimeField(this)
  object application extends IntField(this)
  object model extends IntField(this)
  object modelOverride extends BooleanField(this)
  object api extends IntField(this)
}

object Customer extends Customer with MongoMetaRecord[Customer]  with MongoCRUDify[Customer] {

  // crudify
//  override def pageWrapper(body: NodeSeq) =
//    <lift:surround with="admin" at="content">{body}</lift:surround>
  override def addlMenuLocParams = List(MenuGroups.TopBarGroup)
//  override def viewMenuLocParams = List(MenuGroups.TopBarGroup)
//  override def editMenuLocParams = List(MenuGroups.TopBarGroup)
//  override def deleteMenuLocParams = List(MenuGroups.TopBarGroup)




}