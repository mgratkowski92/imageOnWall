package pl.colorland.imageonwall.util.view

import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment

class CustomARFragment : ArFragment() {
  override fun getSessionConfiguration(session: Session?): Config {

    val config = super.getSessionConfiguration(session)
    config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
    //config.planeFindingMode = Config.PlaneFindingMode.VERTICAL
    return config
  }

}