package pl.inpost.recruitmenttask.presentation.shipmentList

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.presentation.home.HomeActivity

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //should erase the data of the app before running this class

class ShipmentListFragmentTest{

    private var mLocatingActivity: HomeActivity? = null

    @get:Rule
    public var mLoginActivityRule: ActivityTestRule<HomeActivity> = ActivityTestRule(
        HomeActivity::class.java
    )

    @Before
    fun registerIdlingResource() {
        mLocatingActivity = mLoginActivityRule.activity;

    }

    @Test
    fun checkVisibility(){
        Espresso.onView(withId(R.id.expandable_list_view)).check(ViewAssertions.matches(isDisplayed()))
    }
}