package com.lasley.demo1.adapter

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.circleCropTransform
import com.lasley.demo1.R
import com.lasley.demo1.model.Children
import kotlinx.android.synthetic.main.post_layout.view.*


class PostAdapter() : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    var userList: ArrayList<Children> = arrayListOf()
    var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.post_layout, parent, false)

        val font = Typeface.createFromAsset(parent.context.assets, "fonts/bebasneue.ttf")
        val vh = ViewHolder(v)
        vh.author.typeface = font

        return vh
    }

    fun UpdateData(newUserList: ArrayList<Children>) {
        if (newUserList.isEmpty())
            return
        userList.clear()
        userList.addAll(newUserList);
        lastPosition = -1;
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
        setAnimation(holder.itemView, position);
    }

    override fun getItemCount(): Int = userList.size

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.item_anim_fade_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.AvatarImg
        val author: TextView = itemView.Author_View
        val title: TextView = itemView.Title_View

        val stat_comments: TextView = itemView.Comments_text
        val stat_up: TextView = itemView.Up_text
        val stat_down: TextView = itemView.Downs_text

        fun bindItems(post: Children) {
            val postData = post.data

            itemView.setOnClickListener {
                val email = Intent(Intent.ACTION_SEND)
                email.putExtra(Intent.EXTRA_SUBJECT, postData?.title)
                email.type = "message/rfc822"
                email.putExtra(Intent.EXTRA_TEXT, postData?.url)

                val list = itemView.context.packageManager.queryIntentActivities(email, 0)
                if (list.size > 0) {// check if the intent can continue
                    itemView.context.startActivity(email)
                }
            }

            Glide.with(itemView)
                    .load(postData?.thumbnail)
                    .apply(circleCropTransform().error(R.drawable.no_img))
                    .into(avatar)
            avatar.setBackgroundResource(R.drawable.avatar_bg)


            author.text = postData?.author
            title.text = postData?.title
            stat_comments.text = postData?.numComments.toString().plus(" Comments")
            stat_up.text = postData?.ups.toString().plus(" Ups")
            stat_down.text = postData?.downs.toString().plus(" Downs")
        }
    }
}