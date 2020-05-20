package com.example.whatsappstatussaver

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class adapter(
    var mctx: Context, var resources:Int,
    var items: MutableList<customclass>
): ArrayAdapter<customclass>(mctx,resources,items) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        var Holder: ViewHolder
        if (row == null){
            val inflater =(mctx as Activity).layoutInflater
            row= inflater.inflate(resources,parent,false)
            Holder = ViewHolder()
            Holder.img=row!!.findViewById(R.id.img) as ImageView
            Holder.txt=row!!.findViewById(R.id.name) as TextView
            row.tag = Holder

        }else{
            Holder =  row.tag as ViewHolder


        }
        val item =items[position]
        Holder.img?.setImageBitmap(item.image)
        Holder.txt?.text = item.name



        return row






    }
    class ViewHolder{
        internal var img : ImageView? = null
        internal var txt : TextView? = null
    }
}