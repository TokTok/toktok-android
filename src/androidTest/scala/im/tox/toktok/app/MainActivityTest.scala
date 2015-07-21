package im.tox.toktok.app

import android.test.ActivityInstrumentationTestCase2
import im.tox.toktok.app.MainActivity.MainActivityFragment

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class im.tox.toktok.MainTest \
 * im.tox.toktok.tests/android.test.InstrumentationTestRunner
 */
class MainActivityTest extends ActivityInstrumentationTestCase2[MainActivityHolder](classOf[MainActivityHolder])
