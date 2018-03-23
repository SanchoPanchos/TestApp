package alex.com.testapp.ui

import alex.com.testapp.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gjiazhe.panoramaimageview.GyroscopeObserver
import kotlinx.android.synthetic.main.fragment_panorama.view.*


class FragmentPanorama : Fragment() {

    private lateinit var gyroscopeObserver: GyroscopeObserver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_panorama, container, false)
        gyroscopeObserver = GyroscopeObserver()
        gyroscopeObserver.setMaxRotateRadian(Math.PI / 2)
        view.panoramaView.setGyroscopeObserver(gyroscopeObserver)
        return view
    }


    override fun onResume() {
        super.onResume()
        gyroscopeObserver.register(activity)
    }

    override fun onPause() {
        super.onPause()
        gyroscopeObserver.unregister()
    }

}
