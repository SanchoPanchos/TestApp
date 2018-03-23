package alex.com.testapp.ui

import alex.com.testapp.R
import alex.com.testapp.ui.archive.FragmentArchive
import alex.com.testapp.ui.city.FragmentCities
import alex.com.testapp.util.AppConstants
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTabs()
    }

    private fun initTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentCities())
        adapter.addFragment(FragmentPanorama())
        adapter.addFragment(FragmentArchive())
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }


    private class ViewPagerAdapter(fragmentManager: FragmentManager) :
            FragmentStatePagerAdapter(fragmentManager){

        private val fragmentList = ArrayList<Fragment>()

        override fun getItem(position: Int) = fragmentList[position]

        override fun getCount() = AppConstants.FRAGMENTS_COUNT

        override fun getPageTitle(position: Int) = (position + 1).toString()

        fun addFragment(fragment: Fragment) = fragmentList.add(fragment)

    }

}
