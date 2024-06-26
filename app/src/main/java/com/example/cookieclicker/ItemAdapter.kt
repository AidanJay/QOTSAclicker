package com.example.cookieclicker

import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.widget.PopupMenu
import android.widget.Toast

class ItemAdapter(var itemsList: List<Item>, var activity: MainActivity) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewPrice: TextView
        val textViewName: TextView
        val textViewDesc: TextView
        val layout: ConstraintLayout

        init {
            // Define click listener for the ViewHolder's View
            textViewPrice = view.findViewById(R.id.textView_item_price)
            textViewName = view.findViewById(R.id.textView_item_name)
            textViewDesc = view.findViewById(R.id.textView_item_description)
            layout = view.findViewById(R.id.layout_item)
        }
    }

    companion object {
        val TAG = "ItemAdapter"
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val item = itemsList[position]
        val context = viewHolder.layout.context

        viewHolder.textViewPrice.text = item.price.toString()
        viewHolder.textViewName.text = item.name
        viewHolder.textViewDesc.text = item.description

        viewHolder.layout.setOnClickListener {
            //upping the price, adding the item to the player inventory
            Log.d(TAG, "onClick: Tapped item")

            if (MainActivity.points >= item.price) {
                Log.d(TAG, "onClick: Points > Price")
                //taking away price of item
                MainActivity.points = MainActivity.points - item.price

                //increasing price exponentially
                item.price = (item.price * 1.3).toInt()
                viewHolder.textViewPrice.text = item.price.toString()

                //adding purchased bonus
                activity.addBonuses(item.clickBonus, item.autoClick)

                //updating textviews
                activity.updateDisplay()
            }
            else {
                Log.d(TAG, "onClick: ${MainActivity.points}")
                Toast.makeText(this.activity, "Not enough points", Toast.LENGTH_SHORT).show()
            }

        }

        viewHolder.layout.isLongClickable = true
        viewHolder.layout.setOnLongClickListener {
            val popMenu = PopupMenu(context, viewHolder.textViewName)
            popMenu.inflate(R.menu.menu_item_list_context)
            //update total costs
            popMenu.menu.getItem(0).title = "Buy 5 (${(item.price + (item.price * 1.3) + (item.price * 1.3 * 1.3) +
                    (item.price * 1.3 * 1.3 * 1.3) + (item.price * 1.3 * 1.3 * 1.3 * 1.3)).toInt()})"
            popMenu.menu.getItem(1).title = "Buy 10 (${(item.price + (item.price * 1.3) + (item.price * 1.3 * 1.3) +
                    (item.price * 1.3 * 1.3 * 1.3) + (item.price * 1.3 * 1.3 * 1.3 * 1.3) +
                    (item.price * 1.3 * 1.3 * 1.3 * 1.3 * 1.3) +
                    (item.price * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3) +
                    (item.price * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3) +
                    (item.price * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3) +
                    (item.price * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3)).toInt()})"
            popMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menu_itemlist_buyFive -> {
                        //buy 5
                        var fiveCost = (item.price + (item.price * 1.3) + (item.price * 1.3 * 1.3) +
                                (item.price * 1.3 * 1.3 * 1.3) + (item.price * 1.3 * 1.3 * 1.3 * 1.3)).toInt()
                        if (MainActivity.points >= fiveCost) {
                            MainActivity.points = MainActivity.points - fiveCost
                            item.price = (item.price * 1.3 * 1.3 * 1.3 * 1.3 * 1.3).toInt()
                            for (i in 1..5) {
                                activity.addBonuses(item.clickBonus, item.autoClick)
                            }
                            viewHolder.textViewPrice.text = item.price.toString()
                            activity.updateDisplay()
                        }
                        else {
                            Toast.makeText(this.activity, "Not enough points", Toast.LENGTH_SHORT).show()
                        }
                        true
                    }
                    R.id.menu_itemlist_buyTen -> {
                        //buy 10
                        var tenCost = (item.price + (item.price * 1.3) + (item.price * 1.3 * 1.3) +
                                (item.price * 1.3 * 1.3 * 1.3) + (item.price * 1.3 * 1.3 * 1.3 * 1.3) +
                                (item.price * 1.3 * 1.3 * 1.3 * 1.3 * 1.3) +
                                (item.price * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3) +
                                (item.price * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3) +
                                (item.price * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3) +
                                (item.price * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3)).toInt()
                        if (MainActivity.points >= tenCost) {
                            MainActivity.points = MainActivity.points - tenCost
                            item.price = (item.price * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3 * 1.3).toInt()
                            for (i in 1..10) {
                                activity.addBonuses(item.clickBonus, item.autoClick)
                            }
                            viewHolder.textViewPrice.text = item.price.toString()
                            activity.updateDisplay()
                        }
                        else {
                            Toast.makeText(this.activity, "Not enough points", Toast.LENGTH_SHORT).show()
                        }
                        true
                    }

                    else -> true
                }
            }
            popMenu.show()
            true
        }
    }

    override fun getItemCount() = itemsList.size

}