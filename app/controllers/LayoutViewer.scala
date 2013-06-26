package controllers

import play.api._
import play.api.mvc._

object LayoutViewer extends Controller {

  /** GET /layoutviewer */
  def index = Action {
    Ok(views.html.layoutviewer_index())
  }

}
