package pl.inpost.recruitmenttask.utils

import android.util.Log
import androidx.test.espresso.idling.CountingIdlingResource

class EspressoIdlingResource {
    private val TAG: String? = "EspressoIdlingResource"
    private val mCountingIdlingResource: CountingIdlingResource =
        CountingIdlingResource("Espresso_idling_resource")

    fun increment() {
        Log.e(TAG, "test : increment done")
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        if (!mCountingIdlingResource.isIdleNow) {
            Log.e(TAG, "test : decrement done")
            mCountingIdlingResource.decrement()
        }
    }

    fun getIdlingResource(): CountingIdlingResource? {
        return mCountingIdlingResource
    }
}