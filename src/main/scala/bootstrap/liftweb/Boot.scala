package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import code.model.Customer
import java.util.Calendar

//import code.model._
import code.config.AppMongo


object MenuGroups {
  val SettingsGroup = LocGroup("settings")
  val AdminGroup = LocGroup("admin")
  val TopBarGroup = LocGroup("topbar")
}

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {

    // init mongodb
    AppMongo.init()
    // where to search snippet
    LiftRules.addToPackages("code")

    def menus() =  {
      Customer.menus :::
    List(Menu.i("Home") / "index",
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), "Static Content"))
    )
    }


    // Build SiteMap
    def sitemap = SiteMap(menus:_*)


    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(sitemap)

    // Use jQuery 1.4
    LiftRules.jsArtifacts = net.liftweb.http.js.jquery.JQuery14Artifacts

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
   // LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    for (i <- (0 to 300)) {
      val r= Customer.createRecord
        .application(i)
        .model(i)
        .startup(Calendar.getInstance())
        .save

    }
  }
}
