package th.ac.kku.cis.mobileapp.afinal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class ToDoItemAdapter (context: android.content.Context, toDoItemList: MutableList<note>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = toDoItemList


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // create object from view
        val Date: String = itemList.get(position).Date as String
        val detail: String = itemList.get(position).detail as String
        val title: String = itemList.get(position).title as String
//        val image_uri: String = itemList.get(position).image_uri as String
        val view: View
        val vh: ListRowHolder

        // get list view
        if (convertView == null) {
            view = mInflater.inflate(R.layout.listitem, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        // add text to view
        vh.label2.text = Date
        vh.label3.text = detail
        vh.label4.text = title
//        Picasso.get().load(image_uri).into(ImageView())

        return view
    }

    override fun getItem(index: Int): Any {
        return itemList.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    private class ListRowHolder(row: View?) {
        val label2: TextView = row!!.findViewById<TextView>(R.id.viewtitle) as TextView
        val label3: TextView = row!!.findViewById<TextView>(R.id.viewdate) as TextView
        val label4: TextView = row!!.findViewById<TextView>(R.id.viewdetail) as TextView
//        val label5: ImageView = row!!.findViewById<ImageView>(R.id.image) as ImageView
    }
}
