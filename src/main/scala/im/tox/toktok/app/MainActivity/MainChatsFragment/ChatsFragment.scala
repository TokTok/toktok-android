package im.tox.toktok.app.MainActivity.MainChatsFragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.{AppBarLayout, FloatingActionButton}
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.{LinearLayoutManager, RecyclerView, Toolbar}
import android.view._
import android.view.animation.{AccelerateInterpolator, DecelerateInterpolator}
import im.tox.toktok.R
import im.tox.toktok.app.{ChatsMessageObject, Friend, MyRecyclerScroll}

import scala.collection.mutable.ListBuffer

class ChatsFragment extends Fragment with ChatItemClick {

  private var mChats_Recycler: RecyclerView = null
  private var mChats_Recycler_Adapter: ChatsRecyclerAdapter = null
  private var actionMode: ActionMode = null
  private var actionModeCallback = new ChatsActionModeCallback

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedState: Bundle): View = {

    val view: View = inflater.inflate(R.layout.fragment_home_chats, container, false)
    val activity: AppCompatActivity = getActivity.asInstanceOf[AppCompatActivity]
    val fab = activity.findViewById(R.id.home_fab).asInstanceOf[FloatingActionButton]

    // Recycler View

    mChats_Recycler = view.findViewById(R.id.home_chats_recycler).asInstanceOf[RecyclerView]

    val mLayoutManager: LinearLayoutManager = new LinearLayoutManager(activity.getBaseContext)
    mChats_Recycler.setLayoutManager(mLayoutManager)

    var a = new ListBuffer[ChatsMessageObject]()
    var i = 0

    for (i <- 0 to 100) {

      a += new ChatsMessageObject(Friend.lorem, "Hello, how are you?")

    }

    mChats_Recycler_Adapter = new ChatsRecyclerAdapter(a, this)


    mChats_Recycler.setAdapter(mChats_Recycler_Adapter)

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

    if (actionMode == null) {
      return false
    }

    mChats_Recycler_Adapter.toggleSelection(i)
    return true

  }


  def onLongClick(i: Int): Boolean = {

    if (actionMode == null) {
      actionMode = getActivity.asInstanceOf[AppCompatActivity].startSupportActionMode(actionModeCallback)
    }

    mChats_Recycler_Adapter.toggleSelection(i)
    return true

  }

  class ChatsActionModeCallback() extends ActionMode.Callback {

    private var appLayoutElevation: Float = 0
    private var appLayout: AppBarLayout = null
    private var toolbar: Toolbar = null
    private var appBarParams: AppBarLayout.LayoutParams = null
    private var appFlags: Int = 0
    private var fab: FloatingActionButton = null;

    override def onDestroyActionMode(mode: ActionMode): Unit = {

      appLayout.setBackgroundColor(getResources.getColor(R.color.homeColorToolbar))
      appLayout.setElevation(appLayoutElevation)

      appBarParams.setScrollFlags(appFlags)

      toolbar.setLayoutParams(appBarParams)

      fab.setImageResource(R.drawable.ic_content_add_home)
      fab.setBackgroundTintList(ColorStateList.valueOf(getResources.getColor(R.color.basicFABColor)))
      fab.setImageTintList(ColorStateList.valueOf(getResources.getColor(R.color.basicFABTint)))

      mChats_Recycler_Adapter.clearSelections()
      actionMode = null

    }

    override def onCreateActionMode(mode: ActionMode, menu: Menu): Boolean = {

      appLayout = getActivity.findViewById(R.id.appBarLayout).asInstanceOf[AppBarLayout]

      appLayout.setBackgroundColor(getResources.getColor(R.color.backgroundColor))
      appLayoutElevation = appLayout.getElevation
      appLayout.setElevation(0)

      toolbar = getActivity.findViewById(R.id.home_toolbar).asInstanceOf[Toolbar]

      appBarParams = toolbar.getLayoutParams.asInstanceOf[AppBarLayout.LayoutParams]
      appFlags = appBarParams.getScrollFlags
      appBarParams.setScrollFlags(0)
      toolbar.setLayoutParams(appBarParams)

      fab = getActivity.findViewById(R.id.home_fab).asInstanceOf[FloatingActionButton]
      fab.setBackgroundTintList(ColorStateList.valueOf(getResources.getColor(R.color.textDarkColor)))
      fab.setImageResource(R.drawable.ic_action_delete)
      fab.setImageTintList(ColorStateList.valueOf(getResources.getColor(R.color.textWhiteColor)))

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