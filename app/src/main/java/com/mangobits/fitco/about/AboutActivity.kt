package com.mangobits.fitco.about

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mangobits.fitco.R
import kotlinx.android.synthetic.main.app_bar_about.*

class AboutActivity : AppCompatActivity() {

    val mSectionsPagerAdapter: SectionsPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)

        val tab1 = tabLayout.newTab()
        val view1 = LayoutInflater.from(this).inflate(R.layout.tab_view, null)
        view1.findViewById<TextView>(R.id.lblTitulo).text = getText(R.string.tradecom_fitco)
        tab1.customView = view1
        tabLayout.addTab(tab1)


        val tab2 = tabLayout.newTab()
        val view2 = LayoutInflater.from(this).inflate(R.layout.tab_view, null)
        view2.findViewById<TextView>(R.id.lblTitulo).text = getText(R.string.international_fitco)
        tab2.customView = view2
        tabLayout.addTab(tab2)

        tabLayout.setOnTabSelectedListener(selecionarTab)
        mViewPager.adapter = mSectionsPagerAdapter

        loadData()


    }


    fun loadData() {
//        val adapter = PagerAdapter(supportFragmentManager, tabLayout.tabCount, idLocality)


        val adapter = PagerAdapter(supportFragmentManager, tabLayout.tabCount)
        mViewPager.adapter = adapter
        mViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        mViewPager.setCurrentItem(0)

    }


    inner class SectionsPagerAdapter(fm: android.support.v4.app.FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): android.support.v4.app.Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
//            return PlaceholderFragment.newInstance(position + 1)
            return PlaceholderFragment.newInstance(position)

        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "SECTION 1"
                1 -> return "SECTION 2"
            }
            return null
        }
    }


    class PlaceholderFragment : android.support.v4.app.Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_about_brazil, container, false)
//            section_label.text = ""
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }


    private val selecionarTab = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            mViewPager.setCurrentItem(tab.position)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {

        }

        override fun onTabReselected(tab: TabLayout.Tab) {

        }
    }


}
