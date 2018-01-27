package im.tox.toktok

import android.animation.{ Animator, AnimatorInflater }
import android.annotation.TargetApi
import android.app.{ Activity, Dialog }
import android.content.Context
import android.content.res.{ TypedArray, XmlResourceParser }
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.animation.{ Animation, AnimationUtils, Interpolator }
import android.view._

import scala.annotation.implicitNotFound

case class TypedResource[A](id: Int) extends AnyVal
case class TypedLayout[A](id: Int)
case class TypedRes[A](resid: Int) extends AnyVal {
  def value(implicit ev: TypedResource.TypedResValueOp[A], c: Context): ev.T = ev.resourceValue(resid)(c)
  def unapply[T](t: T)(implicit ex: TypedResource.Extractor[A, T]): Boolean = ex.matches(resid, t)
}

object TR {
  final val call_ongoing_contact_img = TypedResource[de.hdodenhof.circleimageview.CircleImageView](R.id.call_ongoing_contact_img)
  final val simple_dialog_input = TypedResource[android.widget.EditText](R.id.simple_dialog_input)
  final val message_recycler = TypedResource[android.support.v7.widget.RecyclerView](R.id.message_recycler)
  final val simple_dialog_color = TypedResource[android.widget.LinearLayout](R.id.simple_dialog_color)
  final val home_friends_img = TypedResource[de.hdodenhof.circleimageview.CircleImageView](R.id.home_friends_img)
  final val home_search_photo = TypedResource[android.widget.ImageView](R.id.home_search_photo)
  final val call_friend = TypedResource[android.widget.TextView](R.id.call_friend)
  final val new_message_recycler = TypedResource[android.support.v7.widget.RecyclerView](R.id.new_message_recycler)
  final val recall_recycler = TypedResource[android.support.v7.widget.RecyclerView](R.id.recall_recycler)
  final val call_bottom_panel = TypedResource[android.widget.FrameLayout](R.id.call_bottom_panel)
  final val simple_dialog_cardview = TypedResource[android.support.v7.widget.CardView](R.id.simple_dialog_cardview)
  final val profile_change_status = TypedResource[android.widget.TextView](R.id.profile_change_status)
  final val simple_dialog_text = TypedResource[android.widget.TextView](R.id.simple_dialog_text)
  final val home_drawer_settings = TypedResource[android.widget.LinearLayout](R.id.home_drawer_settings)
  final val home_friends_recycler = TypedResource[android.support.v7.widget.RecyclerView](R.id.home_friends_recycler)
  final val home_item_status = TypedResource[android.widget.TextView](R.id.home_item_status)
  final val home_search_input = TypedResource[android.widget.EditText](R.id.home_search_input)
  final val simple_color_dialog_recyclerview = TypedResource[android.support.v7.widget.RecyclerView](R.id.simple_color_dialog_recyclerview)
  final val home_friends_message = TypedResource[android.widget.TextView](R.id.home_friends_message)
  final val simple_dialog_img = TypedResource[android.widget.ImageView](R.id.simple_dialog_img)
  final val home_drawer_chats = TypedResource[android.widget.LinearLayout](R.id.home_drawer_chats)
  final val contacts_edit_alias = TypedResource[android.widget.RelativeLayout](R.id.contacts_edit_alias)
  final val contacts_save_photo = TypedResource[android.support.v7.widget.CardView](R.id.contacts_save_photo)
  final val coordinatorLayout = TypedResource[android.support.design.widget.CoordinatorLayout](R.id.coordinatorLayout)
  final val call_ongoing_friend = TypedResource[de.hdodenhof.circleimageview.CircleImageView](R.id.call_ongoing_friend)
  final val home_layout = TypedResource[android.support.v4.widget.DrawerLayout](R.id.home_layout)
  final val contact_subtitle = TypedResource[android.widget.TextView](R.id.contact_subtitle)
  final val appbar = TypedResource[android.support.design.widget.AppBarLayout](R.id.appbar)
  final val home_item_last_message_time = TypedResource[android.widget.TextView](R.id.home_item_last_message_time)
  final val home_drawer_profile = TypedResource[android.widget.LinearLayout](R.id.home_drawer_profile)
  final val sliding_layout = TypedResource[com.sothree.slidinguppanel.SlidingUpPanelLayout](R.id.sliding_layout)
  final val contacts_icon_lock = TypedResource[android.widget.ImageView](R.id.contacts_icon_lock)
  final val new_message_item_name = TypedResource[android.widget.TextView](R.id.new_message_item_name)
  final val call_ongoing_fab = TypedResource[android.support.design.widget.FloatingActionButton](R.id.call_ongoing_fab)
  final val home_item_color = TypedResource[android.view.View](R.id.home_item_color)
  final val home_friends_expanded_area = TypedResource[android.widget.LinearLayout](R.id.home_friends_expanded_area)
  final val home_frieds_requests_text = TypedResource[android.widget.TextView](R.id.home_frieds_requests_text)
  final val home_item_img = TypedResource[de.hdodenhof.circleimageview.CircleImageView](R.id.home_item_img)
  final val videocall_bar = TypedResource[android.widget.RelativeLayout](R.id.videocall_bar)
  final val message_input = TypedResource[android.widget.EditText](R.id.message_input)
  final val contacts_nested_scroll_view = TypedResource[android.widget.LinearLayout](R.id.contacts_nested_scroll_view)
  final val contacts_icon_edit = TypedResource[android.widget.ImageView](R.id.contacts_icon_edit)
  final val recall_fab = TypedResource[android.support.design.widget.FloatingActionButton](R.id.recall_fab)
  final val home_search_bar_recycler = TypedResource[android.support.v4.widget.NestedScrollView](R.id.home_search_bar_recycler)
  final val message_item_text = TypedResource[android.widget.TextView](R.id.message_item_text)
  final val message_attachments_button = TypedResource[android.widget.ImageButton](R.id.message_attachments_button)
  final val call_message_bottom_bar = TypedResource[android.widget.TextView](R.id.call_message_bottom_bar)
  final val message_toolbar = TypedResource[android.support.v7.widget.Toolbar](R.id.message_toolbar)
  final val call_background = TypedResource[android.widget.ImageView](R.id.call_background)
  final val home_item_last_message = TypedResource[android.widget.TextView](R.id.home_item_last_message)
  final val call_ongoing_contacts = TypedResource[android.support.v7.widget.RecyclerView](R.id.call_ongoing_contacts)
  final val profile_collapsing_toolbar = TypedResource[android.support.design.widget.CollapsingToolbarLayout](R.id.profile_collapsing_toolbar)
  final val simple_dialog_qrcode = TypedResource[android.widget.Button](R.id.simple_dialog_qrcode)
  final val home_drawer_img = TypedResource[de.hdodenhof.circleimageview.CircleImageView](R.id.home_drawer_img)
  final val home_friends_requests_text = TypedResource[android.widget.TextView](R.id.home_friends_requests_text)
  final val recall_header_text = TypedResource[android.widget.TextView](R.id.recall_header_text)
  final val home_search_friend_img = TypedResource[android.widget.ImageView](R.id.home_search_friend_img)
  final val home_friends_layout = TypedResource[android.widget.LinearLayout](R.id.home_friends_layout)
  final val contacts_other_title = TypedResource[android.widget.TextView](R.id.contacts_other_title)
  final val home_friends_status = TypedResource[android.widget.ImageView](R.id.home_friends_status)
  final val profile_FAB = TypedResource[android.support.design.widget.FloatingActionButton](R.id.profile_FAB)
  final val contacts_icon_call = TypedResource[android.widget.ImageView](R.id.contacts_icon_call)
  final val message_group_members_recycler = TypedResource[android.support.v7.widget.RecyclerView](R.id.message_group_members_recycler)
  final val profile_share_id = TypedResource[android.widget.RelativeLayout](R.id.profile_share_id)
  final val message_input_layout = TypedResource[android.widget.RelativeLayout](R.id.message_input_layout)
  final val message_group_members_fab = TypedResource[android.support.design.widget.FloatingActionButton](R.id.message_group_members_fab)
  final val new_message_search_icon = TypedResource[android.widget.ImageView](R.id.new_message_search_icon)
  final val home_search_layout = TypedResource[android.widget.LinearLayout](R.id.home_search_layout)
  final val new_message_toolbar_mini = TypedResource[android.widget.TextView](R.id.new_message_toolbar_mini)
  final val contact_image = TypedResource[android.widget.ImageView](R.id.contact_image)
  final val message_item_img = TypedResource[de.hdodenhof.circleimageview.CircleImageView](R.id.message_item_img)
  final val home_search_text = TypedResource[android.widget.TextView](R.id.home_search_text)
  final val home_item_selected = TypedResource[android.view.View](R.id.home_item_selected)
  final val contacts_file_download = TypedResource[android.support.v7.widget.CardView](R.id.contacts_file_download)
  final val appBarLayout = TypedResource[android.support.design.widget.AppBarLayout](R.id.appBarLayout)
  final val fragment_attachments = TypedResource[android.widget.LinearLayout](R.id.fragment_attachments)
  final val contacts_item_voice_call = TypedResource[android.widget.TextView](R.id.contacts_item_voice_call)
  final val profile_toolbar = TypedResource[android.support.v7.widget.Toolbar](R.id.profile_toolbar)
  final val files_send_item_icon = TypedResource[android.widget.ImageView](R.id.files_send_item_icon)
  final val home_friends_fragment = TypedResource[android.widget.FrameLayout](R.id.home_friends_fragment)
  final val home_item_view = TypedResource[android.support.v7.widget.CardView](R.id.home_item_view)
  final val call_top_panel = TypedResource[android.widget.RelativeLayout](R.id.call_top_panel)
  final val contacts_icon_message = TypedResource[android.widget.ImageView](R.id.contacts_icon_message)
  final val new_message_app_bar_layout = TypedResource[android.support.design.widget.AppBarLayout](R.id.new_message_app_bar_layout)
  final val home_item_name = TypedResource[android.widget.TextView](R.id.home_item_name)
  final val home_frame = TypedResource[android.widget.FrameLayout](R.id.home_frame)
  final val new_message_toolbar_selected_button = TypedResource[android.widget.ImageButton](R.id.new_message_toolbar_selected_button)
  final val home_toolbar = TypedResource[android.support.v7.widget.Toolbar](R.id.home_toolbar)
  final val new_message_selected_img = TypedResource[de.hdodenhof.circleimageview.CircleImageView](R.id.new_message_selected_img)
  final val contacts_FAB = TypedResource[android.support.design.widget.FloatingActionButton](R.id.contacts_FAB)
  final val new_message_toolbar_selected_text = TypedResource[android.widget.TextView](R.id.new_message_toolbar_selected_text)
  final val reject_item_move = TypedResource[android.widget.ImageView](R.id.reject_item_move)
  final val contacts_block_friend = TypedResource[android.widget.RelativeLayout](R.id.contacts_block_friend)
  final val new_message_selected_base = TypedResource[android.widget.LinearLayout](R.id.new_message_selected_base)
  final val simple_dialog_status_away = TypedResource[android.widget.LinearLayout](R.id.simple_dialog_status_away)
  final val message_header_title = TypedResource[android.widget.TextView](R.id.message_header_title)
  final val contacts_collapsing_toolbar = TypedResource[android.support.design.widget.CollapsingToolbarLayout](R.id.contacts_collapsing_toolbar)
  final val contacts_delete = TypedResource[android.widget.RelativeLayout](R.id.contacts_delete)
  final val new_message_friends_mini_text = TypedResource[android.widget.TextView](R.id.new_message_friends_mini_text)
  final val new_message_selected_list = TypedResource[android.support.v7.widget.CardView](R.id.new_message_selected_list)
  final val contacts_item_video_call = TypedResource[android.widget.TextView](R.id.contacts_item_video_call)
  final val recyclerview_header_text = TypedResource[android.widget.TextView](R.id.recyclerview_header_text)
  final val simple_dialog_cancel = TypedResource[android.widget.Button](R.id.simple_dialog_cancel)
  final val home_friends_call = TypedResource[android.widget.TextView](R.id.home_friends_call)
  final val reject_item_message = TypedResource[android.widget.TextView](R.id.reject_item_message)
  final val call_item_message = TypedResource[android.widget.TextView](R.id.call_item_message)
  final val new_message_selected_counter = TypedResource[android.widget.TextView](R.id.new_message_selected_counter)
  final val files_send_toolbar = TypedResource[android.support.v7.widget.Toolbar](R.id.files_send_toolbar)
  final val message_item_action = TypedResource[android.widget.LinearLayout](R.id.message_item_action)
  final val new_message_item_img = TypedResource[de.hdodenhof.circleimageview.CircleImageView](R.id.new_message_item_img)
  final val home_friends_base = TypedResource[android.widget.RelativeLayout](R.id.home_friends_base)
  final val contacts_toolbar = TypedResource[android.support.v7.widget.Toolbar](R.id.contacts_toolbar)
  final val simple_dialog_confirm = TypedResource[android.widget.Button](R.id.simple_dialog_confirm)
  final val contacts_icon_image = TypedResource[android.widget.ImageView](R.id.contacts_icon_image)
  final val newMessage_toolbar = TypedResource[android.support.v7.widget.Toolbar](R.id.newMessage_toolbar)
  final val call_ongoing_contact_calltime = TypedResource[android.widget.TextView](R.id.call_ongoing_contact_calltime)
  final val files_send_item_name = TypedResource[android.widget.TextView](R.id.files_send_item_name)
  final val home_chats_recycler = TypedResource[android.support.v7.widget.RecyclerView](R.id.home_chats_recycler)
  final val message_fab = TypedResource[android.support.design.widget.FloatingActionButton](R.id.message_fab)
  final val contacts_message = TypedResource[android.support.v7.widget.CardView](R.id.contacts_message)
  final val new_message_search_field = TypedResource[android.widget.EditText](R.id.new_message_search_field)
  final val call_img = TypedResource[de.hdodenhof.circleimageview.CircleImageView](R.id.call_img)
  final val home_tabs = TypedResource[android.support.design.widget.TabLayout](R.id.home_tabs)
  final val contact_status = TypedResource[android.widget.ImageView](R.id.contact_status)
  final val home_drawer_holder = TypedResource[android.widget.LinearLayout](R.id.home_drawer_holder)
  final val home_item_info = TypedResource[android.widget.RelativeLayout](R.id.home_item_info)
  final val reject_item_message_delete = TypedResource[android.widget.ImageView](R.id.reject_item_message_delete)
  final val main_content = TypedResource[android.support.design.widget.CoordinatorLayout](R.id.main_content)
  final val call_slider_img = TypedResource[android.widget.ImageView](R.id.call_slider_img)
  final val call_ongoing_contact_name = TypedResource[android.widget.TextView](R.id.call_ongoing_contact_name)
  final val profile_change_reject_call = TypedResource[android.widget.TextView](R.id.profile_change_reject_call)
  final val profile_share_id_title = TypedResource[android.widget.TextView](R.id.profile_share_id_title)
  final val files_send_recycler = TypedResource[android.support.v7.widget.RecyclerView](R.id.files_send_recycler)
  final val new_message_item_status = TypedResource[android.view.View](R.id.new_message_item_status)
  final val message_item_details = TypedResource[android.widget.TextView](R.id.message_item_details)
  final val contacts_status_bar_color = TypedResource[android.view.View](R.id.contacts_status_bar_color)
  final val contacts_nested = TypedResource[android.support.v4.widget.NestedScrollView](R.id.contacts_nested)
  final val simple_dialog_android_share = TypedResource[android.widget.Button](R.id.simple_dialog_android_share)
  final val call_slider_text = TypedResource[android.widget.TextView](R.id.call_slider_text)
  final val profile_change_nickname = TypedResource[android.widget.TextView](R.id.profile_change_nickname)
  final val reject_recycler = TypedResource[android.support.v7.widget.RecyclerView](R.id.reject_recycler)
  final val files_send_item_date = TypedResource[android.widget.TextView](R.id.files_send_item_date)
  final val home_search_bar = TypedResource[android.support.v7.widget.CardView](R.id.home_search_bar)
  final val recall_toolbar = TypedResource[android.support.v7.widget.Toolbar](R.id.recall_toolbar)
  final val contacts_icon_download = TypedResource[android.widget.ImageView](R.id.contacts_icon_download)
  final val home_friends_name = TypedResource[android.widget.TextView](R.id.home_friends_name)
  final val contacts_coordinator_layout = TypedResource[android.support.design.widget.CoordinatorLayout](R.id.contacts_coordinator_layout)
  final val contacts_edit_color = TypedResource[android.widget.RelativeLayout](R.id.contacts_edit_color)
  final val contact_title = TypedResource[android.widget.TextView](R.id.contact_title)
  final val message_group_members_toolbar = TypedResource[android.support.v7.widget.Toolbar](R.id.message_group_members_toolbar)
  final val home_friends_requests = TypedResource[android.support.v7.widget.CardView](R.id.home_friends_requests)
  final val home_fab = TypedResource[android.support.design.widget.FloatingActionButton](R.id.home_fab)
  final val call_message_input = TypedResource[android.widget.EditText](R.id.call_message_input)
  final val profile_change_status_text = TypedResource[android.widget.TextView](R.id.profile_change_status_text)
  final val home_search_bar_layout = TypedResource[android.widget.RelativeLayout](R.id.home_search_bar_layout)
  final val new_message_item = TypedResource[android.widget.RelativeLayout](R.id.new_message_item)
  final val simple_dialog_base = TypedResource[android.widget.RelativeLayout](R.id.simple_dialog_base)
  final val contacts_icon_trash = TypedResource[android.widget.ImageView](R.id.contacts_icon_trash)
  final val new_message_fab = TypedResource[android.support.design.widget.FloatingActionButton](R.id.new_message_fab)
  final val message_item_base = TypedResource[android.support.v7.widget.CardView](R.id.message_item_base)
  final val reject_call_toolbar = TypedResource[android.support.v7.widget.Toolbar](R.id.reject_call_toolbar)
  final val recall_header_base = TypedResource[android.support.v7.widget.CardView](R.id.recall_header_base)
  final val contacts_icon_palette = TypedResource[android.widget.ImageView](R.id.contacts_icon_palette)
  final val simple_dialog_status_busy = TypedResource[android.widget.LinearLayout](R.id.simple_dialog_status_busy)
  final val home_tabs_holder = TypedResource[android.support.v7.widget.CardView](R.id.home_tabs_holder)
  final val message_header_user_img = TypedResource[de.hdodenhof.circleimageview.CircleImageView](R.id.message_header_user_img)
  final val home_frieds_requests_decline = TypedResource[android.widget.ImageButton](R.id.home_frieds_requests_decline)
  final val message_header = TypedResource[android.widget.RelativeLayout](R.id.message_header)
  final val simple_color_dialog_item = TypedResource[android.view.View](R.id.simple_color_dialog_item)
  final val call_messages_recycler = TypedResource[android.support.v7.widget.RecyclerView](R.id.call_messages_recycler)
  final val simple_dialog_status_online = TypedResource[android.widget.LinearLayout](R.id.simple_dialog_status_online)
  final val message_input_cardview = TypedResource[android.support.v7.widget.CardView](R.id.message_input_cardview)
  final val home_search_img = TypedResource[de.hdodenhof.circleimageview.CircleImageView](R.id.home_search_img)

  object layout {
    final val fragment_home_chats = TypedLayout[android.widget.FrameLayout](R.layout.fragment_home_chats)
    final val activity_files_send = TypedLayout[android.support.design.widget.CoordinatorLayout](R.layout.activity_files_send)
    final val simple_status_chooser_dialog_design = TypedLayout[android.widget.RelativeLayout](R.layout.simple_status_chooser_dialog_design)
    final val fragment_home_chats_item_user = TypedLayout[android.support.v7.widget.CardView](R.layout.fragment_home_chats_item_user)
    final val activity_main_fragment = TypedLayout[android.widget.FrameLayout](R.layout.activity_main_fragment)
    final val files_send_item = TypedLayout[android.widget.RelativeLayout](R.layout.files_send_item)
    final val activity_video_call = TypedLayout[android.widget.RelativeLayout](R.layout.activity_video_call)
    final val home_search_item_message = TypedLayout[android.support.v7.widget.CardView](R.layout.home_search_item_message)
    final val fragment_home_friends = TypedLayout[android.widget.LinearLayout](R.layout.fragment_home_friends)
    final val fragment_home_friends_item = TypedLayout[android.widget.RelativeLayout](R.layout.fragment_home_friends_item)
    final val activity_message_group_members = TypedLayout[android.support.design.widget.CoordinatorLayout](R.layout.activity_message_group_members)
    final val activity_main = TypedLayout[android.support.v4.widget.DrawerLayout](R.layout.activity_main)
    final val rejected_call_item = TypedLayout[android.widget.RelativeLayout](R.layout.rejected_call_item)
    final val recall_header = TypedLayout[android.widget.LinearLayout](R.layout.recall_header)
    final val home_search_item_friend = TypedLayout[android.support.v7.widget.CardView](R.layout.home_search_item_friend)
    final val fragment_home_friends_request_small = TypedLayout[android.widget.TextView](R.layout.fragment_home_friends_request_small)
    final val new_message_toolbar_friend = TypedLayout[android.widget.LinearLayout](R.layout.new_message_toolbar_friend)
    final val call_top_on_going = TypedLayout[android.widget.LinearLayout](R.layout.call_top_on_going)
    final val new_message_toolbar_friend_mini = TypedLayout[android.widget.LinearLayout](R.layout.new_message_toolbar_friend_mini)
    final val simple_share_dialog_design = TypedLayout[android.widget.RelativeLayout](R.layout.simple_share_dialog_design)
    final val simple_dialog_design = TypedLayout[android.widget.RelativeLayout](R.layout.simple_dialog_design)
    final val contacts_file_download = TypedLayout[android.support.v7.widget.CardView](R.layout.contacts_file_download)
    final val contacts_other_options = TypedLayout[android.support.v7.widget.CardView](R.layout.contacts_other_options)
    final val overlay_attachments = TypedLayout[android.widget.LinearLayout](R.layout.overlay_attachments)
    final val simple_color_dialog_design = TypedLayout[android.widget.RelativeLayout](R.layout.simple_color_dialog_design)
    final val call_bottom_receive = TypedLayout[android.widget.LinearLayout](R.layout.call_bottom_receive)
    final val message_header_user = TypedLayout[android.widget.RelativeLayout](R.layout.message_header_user)
    final val home_search_item_file = TypedLayout[android.support.v7.widget.CardView](R.layout.home_search_item_file)
    final val recall_item = TypedLayout[android.widget.LinearLayout](R.layout.recall_item)
    final val message_item_action = TypedLayout[android.widget.RelativeLayout](R.layout.message_item_action)
    final val call_slider_decline = TypedLayout[android.widget.RelativeLayout](R.layout.call_slider_decline)
    final val message_header_group = TypedLayout[android.widget.RelativeLayout](R.layout.message_header_group)
    final val call_item = TypedLayout[android.widget.LinearLayout](R.layout.call_item)
    final val profile_options = TypedLayout[android.support.v7.widget.CardView](R.layout.profile_options)
    final val simple_input_dialog_design = TypedLayout[android.widget.RelativeLayout](R.layout.simple_input_dialog_design)
    final val message_item_cardview = TypedLayout[android.support.v7.widget.CardView](R.layout.message_item_cardview)
    final val home_search_item_group = TypedLayout[android.support.v7.widget.CardView](R.layout.home_search_item_group)
    final val contacts_save_profile_photo = TypedLayout[android.support.v7.widget.CardView](R.layout.contacts_save_profile_photo)
    final val call_ongoing_contact = TypedLayout[android.widget.RelativeLayout](R.layout.call_ongoing_contact)
    final val call_bottom_on_going = TypedLayout[android.widget.LinearLayout](R.layout.call_bottom_on_going)
    final val activity_reject_call = TypedLayout[android.support.design.widget.CoordinatorLayout](R.layout.activity_reject_call)
    final val call_slider_answer = TypedLayout[android.widget.RelativeLayout](R.layout.call_slider_answer)
    final val fragment_home_friends_request = TypedLayout[android.widget.RelativeLayout](R.layout.fragment_home_friends_request)
    final val contacts_call = TypedLayout[android.support.v7.widget.CardView](R.layout.contacts_call)
    final val home_drawer_header = TypedLayout[android.widget.RelativeLayout](R.layout.home_drawer_header)
    final val new_message_item = TypedLayout[android.widget.RelativeLayout](R.layout.new_message_item)
    final val fragment_home_chats_item_group = TypedLayout[android.support.v7.widget.CardView](R.layout.fragment_home_chats_item_group)
    final val activity_recall_message = TypedLayout[android.support.design.widget.CoordinatorLayout](R.layout.activity_recall_message)
    final val message_item_friend_simple = TypedLayout[android.widget.RelativeLayout](R.layout.message_item_friend_simple)
    final val activity_call_layout = TypedLayout[android.widget.FrameLayout](R.layout.activity_call_layout)
    final val simple_addfriend_dialog_design = TypedLayout[android.widget.RelativeLayout](R.layout.simple_addfriend_dialog_design)
    final val activity_new_message = TypedLayout[android.support.design.widget.CoordinatorLayout](R.layout.activity_new_message)
    final val simple_color_dialog_item = TypedLayout[android.support.v7.widget.CardView](R.layout.simple_color_dialog_item)
    final val call_top_receive = TypedLayout[android.widget.RelativeLayout](R.layout.call_top_receive)
    final val activity_profile = TypedLayout[android.support.design.widget.CoordinatorLayout](R.layout.activity_profile)
    final val recyclerview_header = TypedLayout[android.widget.LinearLayout](R.layout.recyclerview_header)
    final val contacts_new_message = TypedLayout[android.support.v7.widget.CardView](R.layout.contacts_new_message)
    final val activity_message = TypedLayout[android.widget.RelativeLayout](R.layout.activity_message)
    final val message_item_user_simple = TypedLayout[android.widget.RelativeLayout](R.layout.message_item_user_simple)
  }

  object drawable {
    final val ic_file_attachment = TypedRes[TypedResource.ResDrawable](R.drawable.ic_file_attachment)
    final val ripple = TypedRes[TypedResource.ResDrawable](R.drawable.ripple)
    final val ic_navigation_arrow_forward = TypedRes[TypedResource.ResDrawable](R.drawable.ic_navigation_arrow_forward)
    final val user = TypedRes[TypedResource.ResDrawable](R.drawable.user)
    final val jane = TypedRes[TypedResource.ResDrawable](R.drawable.jane)
    final val attach_rectangle_corners = TypedRes[TypedResource.ResDrawable](R.drawable.attach_rectangle_corners)
    final val ic_hardware_keyboard_arrow_up = TypedRes[TypedResource.ResDrawable](R.drawable.ic_hardware_keyboard_arrow_up)
    final val ic_person_black_48dp = TypedRes[TypedResource.ResDrawable](R.drawable.ic_person_black_48dp)
    final val ic_communication_call_end = TypedRes[TypedResource.ResDrawable](R.drawable.ic_communication_call_end)
    final val background_transparent = TypedRes[TypedResource.ResDrawable](R.drawable.background_transparent)
    final val transition_background = TypedRes[TypedResource.ResDrawable](R.drawable.transition_background)
    final val call_answer = TypedRes[TypedResource.ResDrawable](R.drawable.call_answer)
    final val rectangle_corners = TypedRes[TypedResource.ResDrawable](R.drawable.rectangle_corners)
    final val ic_photo_black_24dp = TypedRes[TypedResource.ResDrawable](R.drawable.ic_photo_black_24dp)
    final val ic_navigation_menu = TypedRes[TypedResource.ResDrawable](R.drawable.ic_navigation_menu)
    final val lorem = TypedRes[TypedResource.ResDrawable](R.drawable.lorem)
    final val attachments_camara = TypedRes[TypedResource.ResDrawable](R.drawable.attachments_camara)
    final val call_line = TypedRes[TypedResource.ResDrawable](R.drawable.call_line)
    final val ic_av_volume_up = TypedRes[TypedResource.ResDrawable](R.drawable.ic_av_volume_up)
    final val ic_done_black_24dp = TypedRes[TypedResource.ResDrawable](R.drawable.ic_done_black_24dp)
    final val ic_action_settings = TypedRes[TypedResource.ResDrawable](R.drawable.ic_action_settings)
    final val cardboard_ripple = TypedRes[TypedResource.ResDrawable](R.drawable.cardboard_ripple)
    final val ic_delete_black_48dp = TypedRes[TypedResource.ResDrawable](R.drawable.ic_delete_black_48dp)
    final val call_decline_button = TypedRes[TypedResource.ResDrawable](R.drawable.call_decline_button)
    final val home_notification = TypedRes[TypedResource.ResDrawable](R.drawable.home_notification)
    final val ic_communication_chat = TypedRes[TypedResource.ResDrawable](R.drawable.ic_communication_chat)
    final val ic_action_delete = TypedRes[TypedResource.ResDrawable](R.drawable.ic_action_delete)
    final val call_answer_button = TypedRes[TypedResource.ResDrawable](R.drawable.call_answer_button)
    final val ic_hardware_keyboard_arrow_down = TypedRes[TypedResource.ResDrawable](R.drawable.ic_hardware_keyboard_arrow_down)
    final val attachments_cam = TypedRes[TypedResource.ResDrawable](R.drawable.attachments_cam)
    final val ic_person_add_white_24dp = TypedRes[TypedResource.ResDrawable](R.drawable.ic_person_add_white_24dp)
    final val attachments_location = TypedRes[TypedResource.ResDrawable](R.drawable.attachments_location)
    final val ic_av_mic = TypedRes[TypedResource.ResDrawable](R.drawable.ic_av_mic)
    final val ic_editor_mode_edit_48 = TypedRes[TypedResource.ResDrawable](R.drawable.ic_editor_mode_edit_48)
    final val ic_action_lock = TypedRes[TypedResource.ResDrawable](R.drawable.ic_action_lock)
    final val background_black_opacity = TypedRes[TypedResource.ResDrawable](R.drawable.background_black_opacity)
    final val ic_editor_mode_edit = TypedRes[TypedResource.ResDrawable](R.drawable.ic_editor_mode_edit)
    final val gradient_contacts = TypedRes[TypedResource.ResDrawable](R.drawable.gradient_contacts)
    final val attachments_mic = TypedRes[TypedResource.ResDrawable](R.drawable.attachments_mic)
    final val ic_action_search = TypedRes[TypedResource.ResDrawable](R.drawable.ic_action_search)
    final val ic_social_person = TypedRes[TypedResource.ResDrawable](R.drawable.ic_social_person)
    final val ic_content_send = TypedRes[TypedResource.ResDrawable](R.drawable.ic_content_send)
    final val ic_navigation_arrow_back_white = TypedRes[TypedResource.ResDrawable](R.drawable.ic_navigation_arrow_back_white)
    final val ic_image_photo_camera = TypedRes[TypedResource.ResDrawable](R.drawable.ic_image_photo_camera)
    final val call_rect_lines = TypedRes[TypedResource.ResDrawable](R.drawable.call_rect_lines)
    final val drawer_item_selected_ripple = TypedRes[TypedResource.ResDrawable](R.drawable.drawer_item_selected_ripple)
    final val ic_image_color_lens = TypedRes[TypedResource.ResDrawable](R.drawable.ic_image_color_lens)
    final val ic_image_color_lens_24 = TypedRes[TypedResource.ResDrawable](R.drawable.ic_image_color_lens_24)
    final val call_decline = TypedRes[TypedResource.ResDrawable](R.drawable.call_decline)
    final val ic_file_download_black_24dp = TypedRes[TypedResource.ResDrawable](R.drawable.ic_file_download_black_24dp)
    final val ic_navigation_more_vert = TypedRes[TypedResource.ResDrawable](R.drawable.ic_navigation_more_vert)
    final val call_answer_hold = TypedRes[TypedResource.ResDrawable](R.drawable.call_answer_hold)
    final val status_circle = TypedRes[TypedResource.ResDrawable](R.drawable.status_circle)
    final val ic_device_network_cell = TypedRes[TypedResource.ResDrawable](R.drawable.ic_device_network_cell)
    final val files_movie = TypedRes[TypedResource.ResDrawable](R.drawable.files_movie)
    final val call_decline_hold = TypedRes[TypedResource.ResDrawable](R.drawable.call_decline_hold)
    final val background_ripple = TypedRes[TypedResource.ResDrawable](R.drawable.background_ripple)
    final val ic_action_navigation_arrow_back = TypedRes[TypedResource.ResDrawable](R.drawable.ic_action_navigation_arrow_back)
    final val status_background = TypedRes[TypedResource.ResDrawable](R.drawable.status_background)
    final val ic_content_add_home = TypedRes[TypedResource.ResDrawable](R.drawable.ic_content_add_home)
    final val ic_toggle_star = TypedRes[TypedResource.ResDrawable](R.drawable.ic_toggle_star)
    final val john = TypedRes[TypedResource.ResDrawable](R.drawable.john)
    final val ic_content_clear_mini = TypedRes[TypedResource.ResDrawable](R.drawable.ic_content_clear_mini)
    final val ic_social_share = TypedRes[TypedResource.ResDrawable](R.drawable.ic_social_share)
    final val ic_communication_message = TypedRes[TypedResource.ResDrawable](R.drawable.ic_communication_message)
    final val call_line_decline = TypedRes[TypedResource.ResDrawable](R.drawable.call_line_decline)
    final val ic_action_delete_black = TypedRes[TypedResource.ResDrawable](R.drawable.ic_action_delete_black)
    final val ic_content_clear = TypedRes[TypedResource.ResDrawable](R.drawable.ic_content_clear)
    final val ic_content_add = TypedRes[TypedResource.ResDrawable](R.drawable.ic_content_add)
    final val attachments_photo = TypedRes[TypedResource.ResDrawable](R.drawable.attachments_photo)
    final val ic_communication_call = TypedRes[TypedResource.ResDrawable](R.drawable.ic_communication_call)
    final val bart = TypedRes[TypedResource.ResDrawable](R.drawable.bart)
    final val simple_dialog_button = TypedRes[TypedResource.ResDrawable](R.drawable.simple_dialog_button)
  }
  object menu_item {
    final val action_group_members = TypedRes[TypedResource.ResMenuItem](R.id.action_group_members)
    final val action_mute_conversation_group = TypedRes[TypedResource.ResMenuItem](R.id.action_mute_conversation_group)
    final val action_delete_conversation = TypedRes[TypedResource.ResMenuItem](R.id.action_delete_conversation)
    final val action_rename_conversation = TypedRes[TypedResource.ResMenuItem](R.id.action_rename_conversation)
    final val action_message_delete = TypedRes[TypedResource.ResMenuItem](R.id.action_message_delete)
    final val action_mute_conversation_single = TypedRes[TypedResource.ResMenuItem](R.id.action_mute_conversation_single)
    final val action_add_friend = TypedRes[TypedResource.ResMenuItem](R.id.action_add_friend)
    final val action_leave_conversation = TypedRes[TypedResource.ResMenuItem](R.id.action_leave_conversation)
    final val action_search = TypedRes[TypedResource.ResMenuItem](R.id.action_search)
    final val action_recall_message = TypedRes[TypedResource.ResMenuItem](R.id.action_recall_message)
    final val action_see_files_list = TypedRes[TypedResource.ResMenuItem](R.id.action_see_files_list)
  }
  object color {
    final val md_red_600 = TypedRes[TypedResource.ResColor](R.color.md_red_600)
    final val md_purple_300 = TypedRes[TypedResource.ResColor](R.color.md_purple_300)
    final val statusOnline = TypedRes[TypedResource.ResColor](R.color.statusOnline)
    final val colorAccent = TypedRes[TypedResource.ResColor](R.color.colorAccent)
    final val md_blue_grey_400 = TypedRes[TypedResource.ResColor](R.color.md_blue_grey_400)
    final val md_cyan_900 = TypedRes[TypedResource.ResColor](R.color.md_cyan_900)
    final val md_yellow_800 = TypedRes[TypedResource.ResColor](R.color.md_yellow_800)
    final val md_orange_600 = TypedRes[TypedResource.ResColor](R.color.md_orange_600)
    final val md_purple_400 = TypedRes[TypedResource.ResColor](R.color.md_purple_400)
    final val md_yellow_500 = TypedRes[TypedResource.ResColor](R.color.md_yellow_500)
    final val md_light_green_600 = TypedRes[TypedResource.ResColor](R.color.md_light_green_600)
    final val md_deep_orange_300 = TypedRes[TypedResource.ResColor](R.color.md_deep_orange_300)
    final val md_green_400 = TypedRes[TypedResource.ResColor](R.color.md_green_400)
    final val dividerColor = TypedRes[TypedResource.ResColor](R.color.dividerColor)
    final val md_cyan_300 = TypedRes[TypedResource.ResColor](R.color.md_cyan_300)
    final val md_blue_grey_600 = TypedRes[TypedResource.ResColor](R.color.md_blue_grey_600)
    final val md_deep_purple_600 = TypedRes[TypedResource.ResColor](R.color.md_deep_purple_600)
    final val md_lime_500 = TypedRes[TypedResource.ResColor](R.color.md_lime_500)
    final val md_deep_purple_800 = TypedRes[TypedResource.ResColor](R.color.md_deep_purple_800)
    final val md_blue_grey_900 = TypedRes[TypedResource.ResColor](R.color.md_blue_grey_900)
    final val md_deep_orange_400 = TypedRes[TypedResource.ResColor](R.color.md_deep_orange_400)
    final val md_red_500 = TypedRes[TypedResource.ResColor](R.color.md_red_500)
    final val recyclerViewHeaderBackground = TypedRes[TypedResource.ResColor](R.color.recyclerViewHeaderBackground)
    final val md_brown_600 = TypedRes[TypedResource.ResColor](R.color.md_brown_600)
    final val simpleDialogBackground = TypedRes[TypedResource.ResColor](R.color.simpleDialogBackground)
    final val callButtonColor = TypedRes[TypedResource.ResColor](R.color.callButtonColor)
    final val md_deep_orange_700 = TypedRes[TypedResource.ResColor](R.color.md_deep_orange_700)
    final val md_light_green_400 = TypedRes[TypedResource.ResColor](R.color.md_light_green_400)
    final val md_teal_900 = TypedRes[TypedResource.ResColor](R.color.md_teal_900)
    final val md_teal_400 = TypedRes[TypedResource.ResColor](R.color.md_teal_400)
    final val md_blue_grey_500 = TypedRes[TypedResource.ResColor](R.color.md_blue_grey_500)
    final val cardBoardBackground = TypedRes[TypedResource.ResColor](R.color.cardBoardBackground)
    final val md_amber_300 = TypedRes[TypedResource.ResColor](R.color.md_amber_300)
    final val md_indigo_800 = TypedRes[TypedResource.ResColor](R.color.md_indigo_800)
    final val md_amber_700 = TypedRes[TypedResource.ResColor](R.color.md_amber_700)
    final val md_indigo_400 = TypedRes[TypedResource.ResColor](R.color.md_indigo_400)
    final val md_indigo_700 = TypedRes[TypedResource.ResColor](R.color.md_indigo_700)
    final val md_lime_900 = TypedRes[TypedResource.ResColor](R.color.md_lime_900)
    final val md_purple_600 = TypedRes[TypedResource.ResColor](R.color.md_purple_600)
    final val simpleDialogIconButtonDisable = TypedRes[TypedResource.ResColor](R.color.simpleDialogIconButtonDisable)
    final val md_light_blue_400 = TypedRes[TypedResource.ResColor](R.color.md_light_blue_400)
    final val md_grey_700 = TypedRes[TypedResource.ResColor](R.color.md_grey_700)
    final val md_deep_orange_900 = TypedRes[TypedResource.ResColor](R.color.md_deep_orange_900)
    final val md_deep_orange_500 = TypedRes[TypedResource.ResColor](R.color.md_deep_orange_500)
    final val md_cyan_400 = TypedRes[TypedResource.ResColor](R.color.md_cyan_400)
    final val backgroundColor = TypedRes[TypedResource.ResColor](R.color.backgroundColor)
    final val md_brown_500 = TypedRes[TypedResource.ResColor](R.color.md_brown_500)
    final val md_grey_800 = TypedRes[TypedResource.ResColor](R.color.md_grey_800)
    final val md_indigo_500 = TypedRes[TypedResource.ResColor](R.color.md_indigo_500)
    final val md_light_green_300 = TypedRes[TypedResource.ResColor](R.color.md_light_green_300)
    final val md_orange_900 = TypedRes[TypedResource.ResColor](R.color.md_orange_900)
    final val md_red_800 = TypedRes[TypedResource.ResColor](R.color.md_red_800)
    final val md_lime_700 = TypedRes[TypedResource.ResColor](R.color.md_lime_700)
    final val md_light_green_900 = TypedRes[TypedResource.ResColor](R.color.md_light_green_900)
    final val md_blue_300 = TypedRes[TypedResource.ResColor](R.color.md_blue_300)
    final val md_lime_400 = TypedRes[TypedResource.ResColor](R.color.md_lime_400)
    final val md_blue_grey_800 = TypedRes[TypedResource.ResColor](R.color.md_blue_grey_800)
    final val simpleDialogTextButtonDisable = TypedRes[TypedResource.ResColor](R.color.simpleDialogTextButtonDisable)
    final val md_cyan_600 = TypedRes[TypedResource.ResColor](R.color.md_cyan_600)
    final val md_light_blue_300 = TypedRes[TypedResource.ResColor](R.color.md_light_blue_300)
    final val md_light_blue_600 = TypedRes[TypedResource.ResColor](R.color.md_light_blue_600)
    final val md_brown_700 = TypedRes[TypedResource.ResColor](R.color.md_brown_700)
    final val md_orange_300 = TypedRes[TypedResource.ResColor](R.color.md_orange_300)
    final val md_green_300 = TypedRes[TypedResource.ResColor](R.color.md_green_300)
    final val basicFABColor = TypedRes[TypedResource.ResColor](R.color.basicFABColor)
    final val md_blue_grey_300 = TypedRes[TypedResource.ResColor](R.color.md_blue_grey_300)
    final val textWhiteColor = TypedRes[TypedResource.ResColor](R.color.textWhiteColor)
    final val md_lime_300 = TypedRes[TypedResource.ResColor](R.color.md_lime_300)
    final val simpleDialogIconButtonAssets = TypedRes[TypedResource.ResColor](R.color.simpleDialogIconButtonAssets)
    final val md_orange_400 = TypedRes[TypedResource.ResColor](R.color.md_orange_400)
    final val callTopColor = TypedRes[TypedResource.ResColor](R.color.callTopColor)
    final val md_amber_900 = TypedRes[TypedResource.ResColor](R.color.md_amber_900)
    final val basicFABTint = TypedRes[TypedResource.ResColor](R.color.basicFABTint)
    final val md_blue_400 = TypedRes[TypedResource.ResColor](R.color.md_blue_400)
    final val md_red_300 = TypedRes[TypedResource.ResColor](R.color.md_red_300)
    final val md_pink_800 = TypedRes[TypedResource.ResColor](R.color.md_pink_800)
    final val md_green_700 = TypedRes[TypedResource.ResColor](R.color.md_green_700)
    final val md_yellow_700 = TypedRes[TypedResource.ResColor](R.color.md_yellow_700)
    final val md_light_green_500 = TypedRes[TypedResource.ResColor](R.color.md_light_green_500)
    final val simpleDialogIcon = TypedRes[TypedResource.ResColor](R.color.simpleDialogIcon)
    final val md_deep_purple_300 = TypedRes[TypedResource.ResColor](R.color.md_deep_purple_300)
    final val md_lime_600 = TypedRes[TypedResource.ResColor](R.color.md_lime_600)
    final val simpleDialogTextButton = TypedRes[TypedResource.ResColor](R.color.simpleDialogTextButton)
    final val md_indigo_900 = TypedRes[TypedResource.ResColor](R.color.md_indigo_900)
    final val md_light_blue_500 = TypedRes[TypedResource.ResColor](R.color.md_light_blue_500)
    final val md_brown_300 = TypedRes[TypedResource.ResColor](R.color.md_brown_300)
    final val md_light_blue_900 = TypedRes[TypedResource.ResColor](R.color.md_light_blue_900)
    final val textLightColor = TypedRes[TypedResource.ResColor](R.color.textLightColor)
    final val md_teal_800 = TypedRes[TypedResource.ResColor](R.color.md_teal_800)
    final val md_deep_orange_600 = TypedRes[TypedResource.ResColor](R.color.md_deep_orange_600)
    final val md_pink_300 = TypedRes[TypedResource.ResColor](R.color.md_pink_300)
    final val md_brown_400 = TypedRes[TypedResource.ResColor](R.color.md_brown_400)
    final val md_brown_900 = TypedRes[TypedResource.ResColor](R.color.md_brown_900)
    final val md_pink_600 = TypedRes[TypedResource.ResColor](R.color.md_pink_600)
    final val homeColorStatusBar = TypedRes[TypedResource.ResColor](R.color.homeColorStatusBar)
    final val drawerTint = TypedRes[TypedResource.ResColor](R.color.drawerTint)
    final val md_yellow_600 = TypedRes[TypedResource.ResColor](R.color.md_yellow_600)
    final val md_teal_500 = TypedRes[TypedResource.ResColor](R.color.md_teal_500)
    final val md_grey_600 = TypedRes[TypedResource.ResColor](R.color.md_grey_600)
    final val statusAway = TypedRes[TypedResource.ResColor](R.color.statusAway)
    final val md_cyan_800 = TypedRes[TypedResource.ResColor](R.color.md_cyan_800)
    final val md_pink_700 = TypedRes[TypedResource.ResColor](R.color.md_pink_700)
    final val md_light_blue_800 = TypedRes[TypedResource.ResColor](R.color.md_light_blue_800)
    final val homeColorToolbar = TypedRes[TypedResource.ResColor](R.color.homeColorToolbar)
    final val md_blue_700 = TypedRes[TypedResource.ResColor](R.color.md_blue_700)
    final val md_pink_500 = TypedRes[TypedResource.ResColor](R.color.md_pink_500)
    final val textDarkColor = TypedRes[TypedResource.ResColor](R.color.textDarkColor)
    final val md_blue_800 = TypedRes[TypedResource.ResColor](R.color.md_blue_800)
    final val md_deep_purple_500 = TypedRes[TypedResource.ResColor](R.color.md_deep_purple_500)
    final val homeColorTabSelected = TypedRes[TypedResource.ResColor](R.color.homeColorTabSelected)
    final val md_light_green_800 = TypedRes[TypedResource.ResColor](R.color.md_light_green_800)
    final val drawerTextColor = TypedRes[TypedResource.ResColor](R.color.drawerTextColor)
    final val md_cyan_700 = TypedRes[TypedResource.ResColor](R.color.md_cyan_700)
    final val md_orange_800 = TypedRes[TypedResource.ResColor](R.color.md_orange_800)
    final val md_green_800 = TypedRes[TypedResource.ResColor](R.color.md_green_800)
    final val md_teal_700 = TypedRes[TypedResource.ResColor](R.color.md_teal_700)
    final val md_brown_800 = TypedRes[TypedResource.ResColor](R.color.md_brown_800)
    final val md_yellow_900 = TypedRes[TypedResource.ResColor](R.color.md_yellow_900)
    final val md_pink_400 = TypedRes[TypedResource.ResColor](R.color.md_pink_400)
    final val md_light_green_700 = TypedRes[TypedResource.ResColor](R.color.md_light_green_700)
    final val md_blue_900 = TypedRes[TypedResource.ResColor](R.color.md_blue_900)
    final val md_blue_grey_700 = TypedRes[TypedResource.ResColor](R.color.md_blue_grey_700)
    final val callItemDivider = TypedRes[TypedResource.ResColor](R.color.callItemDivider)
    final val md_deep_purple_700 = TypedRes[TypedResource.ResColor](R.color.md_deep_purple_700)
    final val md_amber_500 = TypedRes[TypedResource.ResColor](R.color.md_amber_500)
    final val md_amber_400 = TypedRes[TypedResource.ResColor](R.color.md_amber_400)
    final val md_orange_500 = TypedRes[TypedResource.ResColor](R.color.md_orange_500)
    final val md_purple_700 = TypedRes[TypedResource.ResColor](R.color.md_purple_700)
    final val md_deep_purple_900 = TypedRes[TypedResource.ResColor](R.color.md_deep_purple_900)
    final val md_green_500 = TypedRes[TypedResource.ResColor](R.color.md_green_500)
    final val md_blue_500 = TypedRes[TypedResource.ResColor](R.color.md_blue_500)
    final val md_deep_purple_400 = TypedRes[TypedResource.ResColor](R.color.md_deep_purple_400)
    final val md_teal_300 = TypedRes[TypedResource.ResColor](R.color.md_teal_300)
    final val md_red_900 = TypedRes[TypedResource.ResColor](R.color.md_red_900)
    final val md_pink_900 = TypedRes[TypedResource.ResColor](R.color.md_pink_900)
    final val md_blue_600 = TypedRes[TypedResource.ResColor](R.color.md_blue_600)
    final val md_grey_900 = TypedRes[TypedResource.ResColor](R.color.md_grey_900)
    final val md_indigo_600 = TypedRes[TypedResource.ResColor](R.color.md_indigo_600)
    final val simpleDialogIconButton = TypedRes[TypedResource.ResColor](R.color.simpleDialogIconButton)
    final val md_orange_700 = TypedRes[TypedResource.ResColor](R.color.md_orange_700)
    final val md_indigo_300 = TypedRes[TypedResource.ResColor](R.color.md_indigo_300)
    final val drawerBackgroundSelected = TypedRes[TypedResource.ResColor](R.color.drawerBackgroundSelected)
    final val md_purple_800 = TypedRes[TypedResource.ResColor](R.color.md_purple_800)
    final val md_teal_600 = TypedRes[TypedResource.ResColor](R.color.md_teal_600)
    final val md_red_400 = TypedRes[TypedResource.ResColor](R.color.md_red_400)
    final val md_lime_800 = TypedRes[TypedResource.ResColor](R.color.md_lime_800)
    final val contactsTransparentBar = TypedRes[TypedResource.ResColor](R.color.contactsTransparentBar)
    final val md_deep_orange_800 = TypedRes[TypedResource.ResColor](R.color.md_deep_orange_800)
    final val homeColorTab = TypedRes[TypedResource.ResColor](R.color.homeColorTab)
    final val md_cyan_500 = TypedRes[TypedResource.ResColor](R.color.md_cyan_500)
    final val md_light_blue_700 = TypedRes[TypedResource.ResColor](R.color.md_light_blue_700)
    final val md_green_600 = TypedRes[TypedResource.ResColor](R.color.md_green_600)
    final val md_yellow_300 = TypedRes[TypedResource.ResColor](R.color.md_yellow_300)
    final val md_amber_600 = TypedRes[TypedResource.ResColor](R.color.md_amber_600)
    final val snackBarColor = TypedRes[TypedResource.ResColor](R.color.snackBarColor)
    final val md_amber_800 = TypedRes[TypedResource.ResColor](R.color.md_amber_800)
    final val md_yellow_400 = TypedRes[TypedResource.ResColor](R.color.md_yellow_400)
    final val md_purple_500 = TypedRes[TypedResource.ResColor](R.color.md_purple_500)
    final val md_green_900 = TypedRes[TypedResource.ResColor](R.color.md_green_900)
    final val md_purple_900 = TypedRes[TypedResource.ResColor](R.color.md_purple_900)
    final val statusBusy = TypedRes[TypedResource.ResColor](R.color.statusBusy)
    final val md_red_700 = TypedRes[TypedResource.ResColor](R.color.md_red_700)
  }
  object string {
    final val call_is_calling = TypedRes[TypedResource.ResString](R.string.call_is_calling)
    final val profile_reject_messages = TypedRes[TypedResource.ResString](R.string.profile_reject_messages)
    final val dialog_edit_user_status = TypedRes[TypedResource.ResString](R.string.dialog_edit_user_status)
    final val action_settings = TypedRes[TypedResource.ResString](R.string.action_settings)
    final val call_answer = TypedRes[TypedResource.ResString](R.string.call_answer)
    final val dialog_leave_group = TypedRes[TypedResource.ResString](R.string.dialog_leave_group)
    final val app_name = TypedRes[TypedResource.ResString](R.string.app_name)
    final val search_hint = TypedRes[TypedResource.ResString](R.string.search_hint)
    final val message_hint_group = TypedRes[TypedResource.ResString](R.string.message_hint_group)
    final val contact_save_photo = TypedRes[TypedResource.ResString](R.string.contact_save_photo)
    final val drawer_chats = TypedRes[TypedResource.ResString](R.string.drawer_chats)
    final val dialog_delete_friend = TypedRes[TypedResource.ResString](R.string.dialog_delete_friend)
    final val dialog_confirm = TypedRes[TypedResource.ResString](R.string.dialog_confirm)
    final val profile_status_message = TypedRes[TypedResource.ResString](R.string.profile_status_message)
    final val dialog_status_away = TypedRes[TypedResource.ResString](R.string.dialog_status_away)
    final val dialog_adding_new_friends = TypedRes[TypedResource.ResString](R.string.dialog_adding_new_friends)
    final val home_search = TypedRes[TypedResource.ResString](R.string.home_search)
    final val contact_blocked = TypedRes[TypedResource.ResString](R.string.contact_blocked)
    final val dialog_delete_friend_end = TypedRes[TypedResource.ResString](R.string.dialog_delete_friend_end)
    final val call_decline = TypedRes[TypedResource.ResString](R.string.call_decline)
    final val attachment_camera = TypedRes[TypedResource.ResString](R.string.attachment_camera)
    final val contact_popup_edit_alias = TypedRes[TypedResource.ResString](R.string.contact_popup_edit_alias)
    final val contact_choose_color = TypedRes[TypedResource.ResString](R.string.contact_choose_color)
    final val message_group_contacts = TypedRes[TypedResource.ResString](R.string.message_group_contacts)
    final val dialog_change_color_end = TypedRes[TypedResource.ResString](R.string.dialog_change_color_end)
    final val dialog_status_online = TypedRes[TypedResource.ResString](R.string.dialog_status_online)
    final val drawer_connection_connecting = TypedRes[TypedResource.ResString](R.string.drawer_connection_connecting)
    final val message_snackbar_group_muted = TypedRes[TypedResource.ResString](R.string.message_snackbar_group_muted)
    final val files_send_title = TypedRes[TypedResource.ResString](R.string.files_send_title)
    final val contact_popup_delete_friend = TypedRes[TypedResource.ResString](R.string.contact_popup_delete_friend)
    final val action_rename_group = TypedRes[TypedResource.ResString](R.string.action_rename_group)
    final val message_snackbar_friend_muted = TypedRes[TypedResource.ResString](R.string.message_snackbar_friend_muted)
    final val sample_tox_id = TypedRes[TypedResource.ResString](R.string.sample_tox_id)
    final val attachment_image = TypedRes[TypedResource.ResString](R.string.attachment_image)
    final val menu_search = TypedRes[TypedResource.ResString](R.string.menu_search)
    final val action_mute_conversation = TypedRes[TypedResource.ResString](R.string.action_mute_conversation)
    final val action_mode_selected_multi = TypedRes[TypedResource.ResString](R.string.action_mode_selected_multi)
    final val action_undo = TypedRes[TypedResource.ResString](R.string.action_undo)
    final val message_hint_single = TypedRes[TypedResource.ResString](R.string.message_hint_single)
    final val sample_user_name = TypedRes[TypedResource.ResString](R.string.sample_user_name)
    final val action_leave_group = TypedRes[TypedResource.ResString](R.string.action_leave_group)
    final val attachment_location = TypedRes[TypedResource.ResString](R.string.attachment_location)
    final val contact_message = TypedRes[TypedResource.ResString](R.string.contact_message)
    final val profile_information = TypedRes[TypedResource.ResString](R.string.profile_information)
    final val sample_status_message = TypedRes[TypedResource.ResString](R.string.sample_status_message)
    final val home_tabs_friends = TypedRes[TypedResource.ResString](R.string.home_tabs_friends)
    final val contact_delete = TypedRes[TypedResource.ResString](R.string.contact_delete)
    final val profile_tox_id = TypedRes[TypedResource.ResString](R.string.profile_tox_id)
    final val profile_status = TypedRes[TypedResource.ResString](R.string.profile_status)
    final val home_tabs_chats = TypedRes[TypedResource.ResString](R.string.home_tabs_chats)
    final val new_message_attachments = TypedRes[TypedResource.ResString](R.string.new_message_attachments)
    final val contact_settings = TypedRes[TypedResource.ResString](R.string.contact_settings)
    final val action_mode_selected_single = TypedRes[TypedResource.ResString](R.string.action_mode_selected_single)
    final val attachment = TypedRes[TypedResource.ResString](R.string.attachment)
    final val new_message_selected_friends = TypedRes[TypedResource.ResString](R.string.new_message_selected_friends)
    final val action_delete_conversation = TypedRes[TypedResource.ResString](R.string.action_delete_conversation)
    final val contact_save_photo_snackbar = TypedRes[TypedResource.ResString](R.string.contact_save_photo_snackbar)
    final val action_recall_message = TypedRes[TypedResource.ResString](R.string.action_recall_message)
    final val action_file_list = TypedRes[TypedResource.ResString](R.string.action_file_list)
    final val new_message = TypedRes[TypedResource.ResString](R.string.new_message)
    final val contact_voice_call = TypedRes[TypedResource.ResString](R.string.contact_voice_call)
    final val attachment_record_sound = TypedRes[TypedResource.ResString](R.string.attachment_record_sound)
    final val dialog_change_color = TypedRes[TypedResource.ResString](R.string.dialog_change_color)
    final val contact_popup_edit_contact_color = TypedRes[TypedResource.ResString](R.string.contact_popup_edit_contact_color)
    final val attachment_camera_movie = TypedRes[TypedResource.ResString](R.string.attachment_camera_movie)
    final val dialog_edit_group_name = TypedRes[TypedResource.ResString](R.string.dialog_edit_group_name)
    final val dialog_android_share = TypedRes[TypedResource.ResString](R.string.dialog_android_share)
    final val profile_nickname = TypedRes[TypedResource.ResString](R.string.profile_nickname)
    final val menu_add_friend = TypedRes[TypedResource.ResString](R.string.menu_add_friend)
    final val profile_settings = TypedRes[TypedResource.ResString](R.string.profile_settings)
    final val call_reject_with_message = TypedRes[TypedResource.ResString](R.string.call_reject_with_message)
    final val action_group_members = TypedRes[TypedResource.ResString](R.string.action_group_members)
    final val contact_files = TypedRes[TypedResource.ResString](R.string.contact_files)
    final val contact_video_call = TypedRes[TypedResource.ResString](R.string.contact_video_call)
    final val new_message_title = TypedRes[TypedResource.ResString](R.string.new_message_title)
    final val contact_edit_alias = TypedRes[TypedResource.ResString](R.string.contact_edit_alias)
    final val drawer_profile = TypedRes[TypedResource.ResString](R.string.drawer_profile)
    final val dialog_cancel = TypedRes[TypedResource.ResString](R.string.dialog_cancel)
    final val drawer_connection_connected = TypedRes[TypedResource.ResString](R.string.drawer_connection_connected)
    final val dialog_qr_code = TypedRes[TypedResource.ResString](R.string.dialog_qr_code)
    final val contact_block = TypedRes[TypedResource.ResString](R.string.contact_block)
    final val call_input_message = TypedRes[TypedResource.ResString](R.string.call_input_message)
    final val drawer_settings = TypedRes[TypedResource.ResString](R.string.drawer_settings)
    final val dialog_share = TypedRes[TypedResource.ResString](R.string.dialog_share)
    final val dialog_status_busy = TypedRes[TypedResource.ResString](R.string.dialog_status_busy)
    final val drawer_connection_not_available = TypedRes[TypedResource.ResString](R.string.drawer_connection_not_available)
    final val dialog_delete_conversion = TypedRes[TypedResource.ResString](R.string.dialog_delete_conversion)
  }
  object dimen {
    final val activity_vertical_margin = TypedRes[TypedResource.ResDimen](R.dimen.activity_vertical_margin)
    final val activity_horizontal_margin = TypedRes[TypedResource.ResDimen](R.dimen.activity_horizontal_margin)
  }
  object mipmap {
    final val ic_launcher = TypedRes[TypedResource.ResMipMap](R.mipmap.ic_launcher)
  }
  object style {
    final val CircleUserStatus = TypedRes[TypedResource.ResStyle](R.style.CircleUserStatus)
    final val BasicFloatingActionButton = TypedRes[TypedResource.ResStyle](R.style.BasicFloatingActionButton)
    final val ContactsTextExtended = TypedRes[TypedResource.ResStyle](R.style.ContactsTextExtended)
    final val RecyclerView = TypedRes[TypedResource.ResStyle](R.style.RecyclerView)
    final val MessageAttachmentsItem = TypedRes[TypedResource.ResStyle](R.style.MessageAttachmentsItem)
    final val ContactsButtonIcon = TypedRes[TypedResource.ResStyle](R.style.ContactsButtonIcon)
    final val AppTheme = TypedRes[TypedResource.ResStyle](R.style.AppTheme)
    final val DialogAnimation = TypedRes[TypedResource.ResStyle](R.style.DialogAnimation)
    final val SmallUserImage = TypedRes[TypedResource.ResStyle](R.style.SmallUserImage)
    final val TitleTextStyle = TypedRes[TypedResource.ResStyle](R.style.TitleTextStyle)
    final val ContactsButtonText = TypedRes[TypedResource.ResStyle](R.style.ContactsButtonText)
    final val ActionMode = TypedRes[TypedResource.ResStyle](R.style.ActionMode)
    final val MessageAttachmentsItem_text = TypedRes[TypedResource.ResStyle](R.style.MessageAttachmentsItem_text)
    final val DialogSlideAnimation = TypedRes[TypedResource.ResStyle](R.style.DialogSlideAnimation)
  }
  object menu {
    final val menu_group_message = TypedRes[TypedResource.ResMenu](R.menu.menu_group_message)
    final val menu_main = TypedRes[TypedResource.ResMenu](R.menu.menu_main)
    final val menu_single_message = TypedRes[TypedResource.ResMenu](R.menu.menu_single_message)
    final val message_action_mode = TypedRes[TypedResource.ResMenu](R.menu.message_action_mode)
  }
  object anim {
    final val fab_from_right = TypedRes[TypedResource.ResAnim](R.anim.fab_from_right)
    final val fab_to_right = TypedRes[TypedResource.ResAnim](R.anim.fab_to_right)
    final val slide_out_top = TypedRes[TypedResource.ResAnim](R.anim.slide_out_top)
    final val slide_in_bottom = TypedRes[TypedResource.ResAnim](R.anim.slide_in_bottom)
    final val activity_fade_out = TypedRes[TypedResource.ResAnim](R.anim.activity_fade_out)
    final val activity_fade_in = TypedRes[TypedResource.ResAnim](R.anim.activity_fade_in)
  }
}

trait TypedFindView extends Any {
  protected def findViewById[V <: View](id: Int): V
  final def findView[A](tr: TypedResource[A]): A = findViewById(tr.id).asInstanceOf[A]
}

object TypedResource {
  sealed trait ResAnim
  sealed trait ResAnimator
  sealed trait ResAttr
  sealed trait ResBool
  sealed trait ResColor
  sealed trait ResDimen
  sealed trait ResDrawable
  sealed trait ResFraction
  sealed trait ResInteger
  sealed trait ResInterpolator
  sealed trait ResMenu
  sealed trait ResMenuItem
  sealed trait ResMipMap
  sealed trait ResPlurals
  sealed trait ResRaw
  sealed trait ResString
  sealed trait ResStyle
  sealed trait ResTransition
  sealed trait ResXml

  // specializations of ResArray
  sealed trait ResStringArray
  sealed trait ResIntegerArray
  sealed trait ResArray

  @implicitNotFound("don't know how to .value for ${A}, create a TypedResValueOp[${A}] manually")
  trait TypedResValueOp[A] {
    type T
    def resourceValue(resid: Int)(implicit c: Context): T
  }

  @implicitNotFound("didn't find an extractor for ${A},${T}; create an Extractor[${A},${T}] manually")
  trait Extractor[A, T] {
    def matches(resid: Int, t: T): Boolean
  }

  implicit class TypedView(val v: View) extends AnyVal with TypedFindView {
    def findViewById[V <: View](id: Int): V = v.findViewById(id)
  }
  implicit class TypedActivity(val a: Activity) extends AnyVal with TypedFindView {
    def findViewById[V <: View](id: Int): V = a.findViewById(id)
  }
  implicit class TypedDialog(val d: Dialog) extends AnyVal with TypedFindView {
    def findViewById[V <: View](id: Int): V = d.findViewById(id)
  }
  implicit class TypedLayoutInflater(val l: LayoutInflater) extends AnyVal {
    def inflate[A <: View](resource: TypedLayout[A], root: ViewGroup, attachToRoot: Boolean): A = {
      val v = l.inflate(resource.id, root, attachToRoot)
      val a = if (root != null && attachToRoot) root.getChildAt(root.getChildCount - 1) else v
      a.asInstanceOf[A]
    }
    def inflate[A <: View](resource: TypedLayout[A], root: ViewGroup): A =
      inflate(resource, root, true)
    def inflate[A <: View](resource: TypedLayout[A]): A =
      inflate(resource, null, false)
  }

  implicit class TypedResMenuInflater(val mi: MenuInflater) extends AnyVal {
    def inflate(menuRes: TypedRes[ResMenu], menu: Menu): Unit = {
      mi.inflate(menuRes.resid, menu)
    }
  }

  implicit class TypedResMenuOps(val m: Menu) extends AnyVal {
    def findItem(item: TypedRes[ResMenuItem]): MenuItem = {
      m.findItem(item.resid)
    }
  }

  implicit val trAnimValueOp: TypedResValueOp[ResAnim] { type T = Animation } = new TypedResValueOp[ResAnim] {
    type T = Animation
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      AnimationUtils.loadAnimation(c, resid)
  }
  implicit val trAnimatorValueOp: TypedResValueOp[ResAnimator] { type T = Animator } = new TypedResValueOp[ResAnimator] {
    type T = Animator
    @TargetApi(11)
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      if (android.os.Build.VERSION.SDK_INT >= 11)
        AnimatorInflater.loadAnimator(c, resid)
      else ???
  }
  implicit val trIntegerArrayValueOp: TypedResValueOp[ResIntegerArray] { type T = Array[Int] } = new TypedResValueOp[ResIntegerArray] {
    type T = Array[Int]
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.getIntArray(resid)
  }
  implicit val trStringArrayValueOp: TypedResValueOp[ResStringArray] { type T = Array[String] } = new TypedResValueOp[ResStringArray] {
    type T = Array[String]
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.getStringArray(resid)
  }
  implicit val trTypedArrayValueOp: TypedResValueOp[ResArray] { type T = TypedArray } = new TypedResValueOp[ResArray] {
    type T = TypedArray
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.obtainTypedArray(resid)
  }
  implicit val trBoolValueOp: TypedResValueOp[ResBool] { type T = Boolean } = new TypedResValueOp[ResBool] {
    type T = Boolean
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.getBoolean(resid)
  }
  implicit val trColorValueOp: TypedResValueOp[ResColor] { type T = Int } = new TypedResValueOp[ResColor] {
    type T = Int
    @TargetApi(23)
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      compat.getColor(c, resid)
  }
  implicit val trDimenValueOp: TypedResValueOp[ResDimen] { type T = Int } = new TypedResValueOp[ResDimen] {
    type T = Int
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.getDimensionPixelSize(resid)
  }
  implicit val trDrawableValueOp: TypedResValueOp[ResDrawable] { type T = Drawable } = new TypedResValueOp[ResDrawable] {
    type T = Drawable
    @TargetApi(21)
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      compat.getDrawable(c, resid)
  }
  implicit val trIntegerValueOp: TypedResValueOp[ResInteger] { type T = Int } = new TypedResValueOp[ResInteger] {
    type T = Int
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.getInteger(resid)
  }
  implicit val trInterpolatorValueOp: TypedResValueOp[ResInterpolator] { type T = Interpolator } = new TypedResValueOp[ResInterpolator] {
    type T = Interpolator
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      AnimationUtils.loadInterpolator(c, resid)
  }
  implicit val trMipMapValueOp: TypedResValueOp[ResMipMap] { type T = Drawable } = new TypedResValueOp[ResMipMap] {
    type T = Drawable
    @TargetApi(21)
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      compat.getDrawable(c, resid)
  }
  implicit val trRawValueOp: TypedResValueOp[ResRaw] { type T = java.io.InputStream } = new TypedResValueOp[ResRaw] {
    type T = java.io.InputStream
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.openRawResource(resid)
  }
  implicit val trStringValueOp: TypedResValueOp[ResString] { type T = String } = new TypedResValueOp[ResString] {
    type T = String
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getString(resid)
  }
  implicit val trXmlValueOp: TypedResValueOp[ResXml] { type T = XmlResourceParser } = new TypedResValueOp[ResXml] {
    type T = XmlResourceParser
    @inline final def resourceValue(resid: Int)(implicit c: Context): T =
      c.getResources.getXml(resid)
  }
  implicit val menuItemExtractor: Extractor[ResMenuItem, MenuItem] = new Extractor[ResMenuItem, MenuItem] {
    @inline final def matches(resid: Int, mi: MenuItem): Boolean = (mi.getItemId == resid)
  }

  // Helper object to suppress deprecation warnings as discussed in
  // https://issues.scala-lang.org/browse/SI-7934
  @deprecated("", "")
  private trait compat {

    @TargetApi(23)
    @inline def getColor(c: Context, resid: Int): Int = {
      if (Build.VERSION.SDK_INT >= 23)
        c.getColor(resid)
      else
        c.getResources.getColor(resid)
    }

    @TargetApi(21)
    @inline def getDrawable(c: Context, resid: Int): Drawable = {
      if (Build.VERSION.SDK_INT >= 21)
        c.getDrawable(resid)
      else
        c.getResources.getDrawable(resid)
    }
  }
  private object compat extends compat
}
