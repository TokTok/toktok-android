package im.tox.toktok.app.MainActivity.MainChatsFragment

import android.content.Intent
import android.content.res.ColorStateList
import android.os.{Handler, Bundle}
import android.support.design.widget.{TabLayout, AppBarLayout, FloatingActionButton}
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.{DefaultItemAnimator, LinearLayoutManager, RecyclerView, Toolbar}
import android.view.View.OnClickListener
import android.view._
import android.view.animation.{AccelerateInterpolator, DecelerateInterpolator}
import im.tox.toktok.R
import im.tox.toktok.app.NewMessageActivity.NewMessageActivity
import im.tox.toktok.app.{CustomViewPager, ChatsMessageObject, Friend, MyRecyclerScroll}

import scala.collection.mutable.ListBuffer

class ChatsFragment extends Fragment with ChatItemClick {

  private var mChats_Recycler: RecyclerView = null
  private var mChats_Recycler_Adapter: ChatsRecyclerAdapter = null
  private var mActionMode: ActionMode = null
  private val actionModeCallback = new ChatsActionModeCallback

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): View = {

    val view: View = inflater.inflate(R.layout.fragment_home_chats, container, false)
    val activity: AppCompatActivity = getActivity.asInstanceOf[AppCompatActivity]
    val fab = activity.findViewById(R.id.home_fab).asInstanceOf[FloatingActionButton]

    // Recycler View

    mChats_Recycler = view.findViewById(R.id.home_chats_recycler).asInstanceOf[RecyclerView]

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(activity.getBaseContext)
    mChats_Recycler.setLayoutManager(mLayoutManager)

    val a = ListBuffer(ChatsMessageObject.loremMessage,ChatsMessageObject.johnMessage,ChatsMessageObject.groupMessage)

    mChats_Recycler_Adapter = new ChatsRecyclerAdapter(a, this)

    mChats_Recycler.setAdapter(mChats_Recycler_Adapter)
    mChats_Recycler.setItemAnimator(new DefaultItemAnimator)
    mChats_Recycler.addOnScrollListener(new MyRecyclerScroll {

      override def hide(): Unit = {
        fab.animate().translationY(fab.getHeight() + fab.getBottom).setInterpolator(new AccelerateInterpolator(2)).start();

      }

      override def show(): Unit = {
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
      }
    })

    return view

  }


  def onClick(i: Int): Boolean = {

    if (mActionMode == null) {

      return false
    }
    mChats_Recycler_Adapter.toggleSelection(i)

    val count = mChats_Recycler_Adapter.getSelectedItemCount()

    if (count == 0){
      mActionMode.finish()
    }
    else if(count == 1){
      mActionMode.setTitle(count+" "+getResources.getString(R.string.action_mode_selected_single))
    }
    else{
      mActionMode.setTitle(count+" "+getResources.getString(R.string.action_mode_selected_multi))
    }

    return true

  }


  def onLongClick(i: Int): Boolean = {

    if (mActionMode == null) {
      mActionMode = getActivity.asInstanceOf[AppCompatActivity].startSupportActionMode(actionModeCallback)
    }

    mChats_Recycler_Adapter.toggleSelection(i)

    val count = mChats_Recycler_Adapter.getSelectedItemCount()

    if (count == 0){
      mActionMode.finish()
    }
    else if(count == 1){
      mActionMode.setTitle(count+" "+getResources.getString(R.string.action_mode_selected_single))
    }
    else{
      mActionMode.setTitle(count+" "+getResources.getString(R.string.action_mode_selected_multi))
    }


    return true

  }

  class ChatsActionModeCallback() extends ActionMode.Callback {

    private var mAppLayout: AppBarLayout = null
    private var mFab: FloatingActionButton = null
    private var mTabs :TabLayout = null
    private var mCustomViewPager : CustomViewPager = null

    override def onDestroyActionMode(mode: ActionMode): Unit = {

      mAppLayout.setBackgroundColor(getResources.getColor(R.color.homeColorToolbar))

      mFab.setImageResource(R.drawable.ic_content_add_home)
      mFab.setBackgroundTintList(ColorStateList.valueOf(getResources.getColor(R.color.basicFABColor)))
      mFab.setImageTintList(ColorStateList.valueOf(getResources.getColor(R.color.basicFABTint)))

      mFab.setOnClickListener(new OnClickListener {
        override def onClick(view: View): Unit = {
          startActivity(new Intent(getActivity, classOf[NewMessageActivity]))
        }
      })

      mCustomViewPager.setSwipingEnabled(true)

      mChats_Recycler_Adapter.clearSelections()
      mActionMode = null
      mAppLayout = null
      mFab = null
      mCustomViewPager = null

    }

    override def onCreateActionMode(mode: ActionMode, menu: Menu): Boolean = {

      mAppLayout = getActivity.findViewById(R.id.appBarLayout).asInstanceOf[AppBarLayout]
      mAppLayout.setBackgroundColor(getResources.getColor(R.color.backgroundColor))

      mFab = getActivity.findViewById(R.id.home_fab).asInstanceOf[FloatingActionButton]
      mFab.setBackgroundTintList(ColorStateList.valueOf(getResources.getColor(R.color.textDarkColor)))
      mFab.setImageResource(R.drawable.ic_action_delete)
      mFab.setImageTintList(ColorStateList.valueOf(getResources.getColor(R.color.textWhiteColor)))

      mCustomViewPager = getActivity.findViewById(R.id.home_tab_holder).asInstanceOf[CustomViewPager]
      mCustomViewPager.setSwipingEnabled(false)


      mFab.setOnClickListener(new OnClickListener {
        override def onClick(v: View): Unit = {
          mChats_Recycler_Adapter.deleteSelected()

          new Handler().postDelayed(new Runnable {
            override def run(): Unit = {
              mode.finish()
            }
          },500)

        }
      })

      return true

    }

    override def onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean = {

      return true

    }

    override def onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean = {

      return false

    }


  }

}