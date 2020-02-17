package pl.colorland.imageonwall.ui.main

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.core.Pose
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.*
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import pl.colorland.imageonwall.R
import pl.colorland.imageonwall.util.view.CustomARFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val MIN_OPENGL_VERSION = 3.0
    private var arFragment: ArFragment? = null
    private var rectangleRenderable: ModelRenderable? = null
    private val anchorPositions: MutableList<Vector3> = arrayListOf()
    var lastAnchorNode: AnchorNode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return
        }
        setContentView(R.layout.activity_main)
        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as CustomARFragment?

        prepareObjects()

        arFragment!!.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane?, motionEvent: MotionEvent? ->
            if (rectangleRenderable == null) {
                return@setOnTapArPlaneListener
            }
            addAnchor(hitResult)
        }
    }

    fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
            Timber.e("Sceneform requires Android N or later")
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG)
                .show()
            activity.finish()
            return false
        }
        val openGlVersionString =
            (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .deviceConfigurationInfo
                .glEsVersion
        if (openGlVersionString.toDouble() < MIN_OPENGL_VERSION) {
            Timber.e("Sceneform requires OpenGL ES 3.0 later")
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                .show()
            activity.finish()
            return false
        }
        return true
    }

    fun prepareObjects() {
        MaterialFactory.makeOpaqueWithColor(
            this,
            Color(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_blue_light
                )
            )
        ).thenAccept { renderable: Material? ->
            rectangleRenderable = ShapeFactory.makeCube(
                Vector3(0.2f / 2, 0.01f / 2, 0.2f / 2),
                Vector3(0.0f, 0.0f, 0.0f),
                renderable
            )
        }
    }

    fun addAnchor(hitResult: HitResult) {
        val anchor = hitResult.createAnchor()
        val anchorNode =
            AnchorNode(anchor)
        anchorNode.setParent(arFragment!!.getArSceneView().scene)
        val node = TransformableNode(arFragment!!.getTransformationSystem())

        node.setParent(anchorNode)
        node.renderable = rectangleRenderable
        node.select()
        node.localPosition.let {
            anchorPositions.add(it)
        }

        if (anchorPositions.size == 2) {
            addLineBetweenHits(hitResult)
        }

        if (lastAnchorNode == null) {
            lastAnchorNode = anchorNode
        }
    }

    private fun addLineBetweenHits(hitResult: HitResult) {
        val anchor = hitResult.createAnchor()
        val anchorNode = AnchorNode(anchor)

        if (lastAnchorNode != null) {
            anchorNode.setParent(arFragment!!.getArSceneView().getScene())
            val point1: Vector3
            val point2: Vector3
            point1 = lastAnchorNode!!.getWorldPosition()
            point2 = anchorNode.getWorldPosition()

            val colorOrange = Color(android.graphics.Color.parseColor("#ffa71c"))
            val difference = Vector3.subtract(point1, point2)
            val directionFromTopToBottom = difference.normalized()
            val rotationFromAToB =
                Quaternion.lookRotation(directionFromTopToBottom, Vector3.up())
            MaterialFactory.makeOpaqueWithColor(getApplicationContext(), colorOrange)
                .thenAccept { material: Material? ->
                    val model = ShapeFactory.makeCube(
                        Vector3(.01f, .01f, difference.length()),
                        Vector3.zero(), material
                    )
                    val node = Node()
                    node.setParent(anchorNode)
                    node.setRenderable(model)
                    node.setWorldPosition(Vector3.add(point1, point2).scaled(.5f))
                    node.setWorldRotation(rotationFromAToB)
                }
            lastAnchorNode = anchorNode
        }

    }
}

fun createMainIntent(context: Context) = Intent(context, MainActivity::class.java)

