package com.mangobits.fitco.about

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter


/**
 * Created by angela on 16/03/18.
 */
class PagerAdapter(fm: FragmentManager, internal var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {//, internal var idLocality: String


    override fun getItem(position: Int): Fragment? {

        when (position) {

            0 -> {
                return AboutBrazilFragment()
//                return DashboardFragmentBkp.newInstance(idPortalOperation)

//                return OrderTrackingFragment()
            }
            1 -> {
                return AboutInternationalFragment()
//                return OrderTrackingFragment.newInstance(idPortalOperation)

            }

            else -> return null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}