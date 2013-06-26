package controllers

import play.api._
import play.api.mvc._

object DataViz extends Controller {

  /** GET /dataviz */
  def index = Action {
    Ok(views.html.dataviz_index())
  }

}
