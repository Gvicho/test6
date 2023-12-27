package com.example.test6.presenter.passcode

class Event(
    val number: Int = -1,
    val eventType: EventType
) {
}

enum class EventType{
    DELETE,
    FINGERPRINT,
    ADD
}