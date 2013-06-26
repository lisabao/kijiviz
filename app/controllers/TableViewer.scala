package controllers

import play.api._
import play.api.mvc._

object TableViewer extends Controller {

  def index = Action {
    Ok(views.html.tableviewer_index())
  }

}
