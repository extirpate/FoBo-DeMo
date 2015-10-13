package bootstrap.liftweb

import _root_.net.liftweb._
import http.ResourceServer._
import actor._
import util._
import Helpers._
import common._
import http._
import sitemap._
import Loc._
import java.util.Locale
import java.sql.DriverManager
import _root_.net.liftweb.util.{ Props }
import _root_.net.liftweb.http.provider.HTTPRequest
import _root_.net.liftweb.http.auth.{ HttpBasicAuthentication, AuthRole, userRoles }

//import code.model._


import net.liftmodules.{FoBo/*,FoBoFA,FoBoTB*/}

object localeOverride extends SessionVar[Box[Locale]](Empty)

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Loggable {
  def boot {
    
    logger.info("IN LIFT BOOT ! ")
    // where to search snippet
    LiftRules.addToPackages("code")
         
    LiftRules.uriNotFound.prepend(NamedPF("404handler"){
      case (req,failure) => 
        NotFoundAsTemplate(ParsePath(List("404"),"html",false,false))
    })

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    //LiftRules.setSiteMap(SiteMap(entries: _*))
    LiftRules.setSiteMap(Paths.sitemap)

    //FoBo init params 
    FoBo.InitParam.JQuery=FoBo.JQuery1111
    FoBo.InitParam.ToolKit=FoBo.PrettifyJun2011
    FoBo.InitParam.ToolKit=FoBo.Bootstrap335
    FoBo.InitParam.ToolKit=FoBo.FontAwesome430
    FoBo.InitParam.ToolKit=FoBo.Pace0415
    FoBo.init()      
    
    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

  }
}

object Paths {
  //import xml.NodeSeq
  import scala.xml._

//  val divider1         = Menu("divider1") / "divider1"
//  val divider2         = Menu("divider2") / "divider2"
//  val divider3         = Menu("divider3") / "divider3"
//  val hdivider1        = Menu("hdivider1") / "hdvidider1"
  
//  val navHeader1       = Menu.i("NavHeader1") / "navHeader1"
//  val navHeader2       = Menu.i("NavHeader2") / "navHeader2"
  
//  val content1DD       = Menu.i("Content1DD") / "ddlabel1"
//  val content11DD      = Menu.i("Content11DD") / "ddlabel11"  
//  val content2DD       = Menu.i("Content2DD") / "ddlabel2"
  
  val index            = Menu.i("Home") / "index"
//  val liboIndex        = Menu.i("LiBo") / "libo"
  
//  val libogstarted     = Menu(Loc("LiBoGStarted"  , Link(List("libogstarted")  , true, "#gstarted")  , S.loc("LiBoGStarted" , Text("Getting started"))      , LocGroup("liboAffix")))
//  val libostarterproj  = Menu(Loc("LiBoStarterproj"  , Link(List("starterproj")  , true, "#starterproj")  , S.loc("LiBoStarterproj" , Text("Starter Project")) , LocGroup("liboAffix")))  
//  val libomodulesetup  = Menu(Loc("LiboModuleSetup"  , Link(List("modulesetup")  , true, "#modulesetup")  , S.loc("LiboModuleSetup" , Text("Module Setup")) , LocGroup("liboAffix")))  
//  val libomoduleupgr   = Menu(Loc("LiboModuleUpgrade"  , Link(List("moduleupgrade")  , true, "#moduleupgrade")  , S.loc("LiboModuleUpgrade" , Text("Module Upgrade")) , LocGroup("liboAffix")))  
//  val libocustom       = Menu(Loc("LiboCustomization"  , Link(List("customization")  , true, "#customization")  , S.loc("LiboCustomization" , Text("Bootstrap Customization")) , LocGroup("liboAffix")))  
//  val libofobosnip     = Menu(Loc("LiboFoBoSnippets"  , Link(List("fobosnippets")  , true, "#fobosnippets")  , S.loc("LiboFoBoSnippets" , Text("FoBo.Bootstrap Snippets")) , LocGroup("liboAffix")))  
//  val libofoboprotousr = Menu(Loc("LiboFoBoProtoUsr"  , Link(List("foboprotousr")  , true, "#foboprotousr")  , S.loc("LiboFoBoProtoUsr" , Text("BootstrapMegaMetaProtoUser")) , LocGroup("liboAffix")))  
//  
//  val libospyhome      = Menu(Loc("LiboSpyHome"  , Link(List("libospyhome")  , true, "#spyhome")  , S.loc("LiboSpyHome" , Text("Home"))      , LocGroup("liboSpyTop")))
//  val libospyabout     = Menu(Loc("LiboSpyAbout" , Link(List("libospyabout") , true, "#spyabout") , S.loc("LiboSpyAbout", Text("About"))     , LocGroup("liboSpyTop")))
//  val libospysetup     = Menu(Loc("LiboSpySetup" , Link(List("libospysetup") , true, "#spysetup") , S.loc("LiboSpySetup", Text("Setup"))     , LocGroup("liboSpyTop")))
//  val libospyfooter    = Menu(Loc("LiboSpyFooter", Link(List("libospyfooter"), true, "#spyfooter"), S.loc("LiboSpyRef"  , Text("Referenser")), LocGroup("liboSpyTop")))
//    
//  val bootstrap3xxDoc  = Menu(Loc("Bootstrap-3.x.x", ExtLink("http://getbootstrap.com/") , S.loc("Bootstrap-3.x.x", Text("Bootstrap-3.x.x")), LocGroup("nldemo1"),FoBo.TBLocInfo.LinkTargetBlank))
//  val foboApiDoc       = Menu(Loc("FoBoAPI"        , Link(List("foboapi")        , true, "/foboapi/index.html#net.liftmodules.FoBo.package"), S.loc("FoBoAPI"  , Text("FoBo API")), LocGroup("liboTop2","mdemo2","nldemo1"),FoBo.TBLocInfo.LinkTargetBlank ))
//  val nlHelp           = Menu.i("NLHelp") / "helpindex"
//    
//  val roundTripDemo = Menu(Loc("fobo-angular-lift-roundtrips", ExtLink("http://www.media4u101.se/fobo-angular-lift-roundtrips/"), S.loc("fobo-angular-lift-roundtrips", Text("Lift round trip tutorial")) ,FoBo.TBLocInfo.LinkTargetBlank  ) )
//  
//  val starterTemplateDemo = Menu(Loc("lift_advanced_bs3", ExtLink("http://www.media4u101.se/lift-advanced-bs3/"), S.loc("lift_advanced_bs3", Text("Lift TB3 templates")) ,FoBo.TBLocInfo.LinkTargetBlank  ) )
//  val starterTemplateGitHub = Menu(Loc("lift_advanced_bs3", ExtLink("http://www.media4u101.se/lift-advanced-bs3/"), S.loc("lift_advanced_bs3", Text("Lift TB3 templates")) ,FoBo.TBLocInfo.LinkTargetBlank  ) )
  
  def sitemap = SiteMap(
//      navHeader1 >> LocGroup("nldemo1") >> FoBo.TBLocInfo.NavHeader,
      index >> LocGroup("mdemo1","nldemo1")//,
//      navHeader2 >> LocGroup("nldemo1") >> FoBo.TBLocInfo.NavHeader,
//      liboIndex,
//      hdivider1 >> LocGroup("mdemo1") >> FoBo.TBLocInfo.DividerVertical,
//      libospyhome ,
//      libospyabout,
//      libospysetup,
//      libospyfooter,
//      content1DD >> LocGroup("liboDD1","mdemo1") >> PlaceHolder submenus ( 
//          bootstrap3xxDoc ,
//          divider1  >> FoBo.TBLocInfo.Divider,
//          foboApiDoc 
//      ),
//      content2DD >> LocGroup("liboDD2") >> PlaceHolder submenus ( 
//              starterTemplateDemo,
//              roundTripDemo
//      ), 
//      nlHelp >> LocGroup("nldemo1"),
//      libogstarted ,
//      libostarterproj,
//      libomodulesetup,
//      libomoduleupgr,
//      libocustom,
//      libofoboprotousr,
//      libofobosnip
      )
}
