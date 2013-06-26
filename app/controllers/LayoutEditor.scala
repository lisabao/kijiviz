package controllers

import play.api._
import play.api.mvc._

object LayoutEditor extends Controller {

  /** Get /layouteditor */
  def index = Action {
    Ok(views.html.layouteditor_index())
  }

}
