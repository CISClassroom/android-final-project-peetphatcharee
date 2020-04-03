package th.ac.kku.cis.mobileapp.phamacyfinal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CategoryAdapter(context: android.content.Context, toDoItemList: MutableList<CatePhamacy>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = toDoItemList


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // create object from view

        val Namephamacy: String = itemList.get(position).Namephamacy as String
        val view: View
        val vh: ListRowHolder

        // get list view
        if (convertView == null) {
            view = mInflater.inflate(R.layout.activity_category, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        // add text to view
        vh.label2.text = Namephamacy


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
        val label2: TextView = row!!.findViewById<TextView>(R.id.textView) as TextView


    }
}


class PhamacyAdapter (context: android.content.Context, toDoPhamacyList: MutableList<Phamacy>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = toDoPhamacyList


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // create object from view
       val Newname: String = itemList.get(position).NewName as String
        val itemPha: String = itemList.get(position).itemPha as String
        val cure: String = itemList.get(position).cure as String
        val more: String = itemList.get(position).more as String

        val view: View
        val vh: ListRowHolder

        // get list view
        if (convertView == null) {
            view = mInflater.inflate(R.layout.activity_phamacy_item, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        // add text to view
        vh.label2.text = itemPha
       vh.label5.text = Newname

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
        val label2: TextView = row!!.findViewById<TextView>(R.id.textView2) as TextView
        val label5: TextView = row!!.findViewById<TextView>(R.id.textView3) as TextView


    }
}





