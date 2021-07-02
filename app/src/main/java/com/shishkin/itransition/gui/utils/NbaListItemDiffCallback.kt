package com.shishkin.itransition.gui.utils

import androidx.recyclerview.widget.DiffUtil

class NbaListItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        // TODO Evgeny не совсем ты понял суть пока как DiffUtil работает, глянь плиз примеры
        // Тут надо делать прямые проверки по id. Проверять мол (oldItem.item as? NbaPlayer)?.id == (newItem.item as? NbaPlayer)?.id ||
        // (oldItem.item as? Team)?.id ...
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        // А вот тут уже ок, .т.к. они = data классы.
        return oldItem == newItem
    }
}
