package com.example.myapplication.domain

enum class EventType {
    ITEM_CLICK, ICON_CLICK
}

data class Event(val type: EventType, val obj: Any?)