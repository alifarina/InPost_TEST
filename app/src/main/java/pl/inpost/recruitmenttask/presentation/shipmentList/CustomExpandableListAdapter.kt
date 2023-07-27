package pl.inpost.recruitmenttask.presentation.shipmentList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.network.model.ShipmentStatus


class CustomExpandableListAdapter(
    private val context: Context,
    private val titleList: List<String>,
    private val dataList: HashMap<String, List<ShipmentNetwork>>, private val callback: ListCallback
) :
    BaseExpandableListAdapter() {
    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return this.dataList[this.titleList[listPosition]]!![expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val shipmentData = getChild(listPosition, expandedListPosition) as ShipmentNetwork
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.shipment_item, null)
        }
        val shipmentNumber: TextView = convertView!!.findViewById(R.id.shipmentNumber)
        val status: TextView = convertView.findViewById(R.id.status)
        val senderEmail: TextView = convertView.findViewById(R.id.sender_email)
        val statusImg: ImageView = convertView.findViewById(R.id.status_img)
        val archive: ImageView = convertView.findViewById(R.id.archive)

        status.text = shipmentData.status
        senderEmail.text = shipmentData.sender?.email ?: ""
        shipmentNumber.text = shipmentData.number
        if (shipmentData.status == ShipmentStatus.OUT_FOR_DELIVERY.name) {
            statusImg.setImageResource(R.drawable.truck)
        } else {
            statusImg.setImageResource(R.drawable.grid)
        }
        archive.setOnClickListener { callback.setItemArchived(shipment_number = shipmentData.number) }
        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.dataList[this.titleList[listPosition]]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition]
    }

    override fun getGroupCount(): Int {
        return this.titleList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.group_view, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.group_value)
        listTitleTextView.text = listTitle

        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return false
    }

    companion object {
        interface ListCallback {
            fun setItemArchived(shipment_number: String)
        }
    }
}