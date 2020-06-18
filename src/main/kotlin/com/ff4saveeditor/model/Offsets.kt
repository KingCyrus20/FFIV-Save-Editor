package com.ff4saveeditor.model

//Companion object for save file offset constants
class Offsets {
    companion object {
        //Save slot general info
        const val GIL = 0x88L
        const val FIRST_CHAR = 0x9CL
        const val TIME = 0x2304L
        const val SLOT_SEPARATION = 0x3DC0L

        //Character info
        const val CURR_HP = 0xCL
        const val MAX_HP = 0x10L
        const val CURR_MP = 0x14L
        const val MAX_MP = 0x18L
        const val STRENGTH = 0x1CAL
        const val STAMINA = 0x1CBL
        const val SPEED = 0x1CCL
        const val INTELLECT = 0x1CDL
        const val SPIRIT = 0x1CEL
        const val RIGHT_HAND = 0x26L
        const val LEFT_HAND = 0x28L
        const val HEAD = 0x2AL
        const val BODY = 0x2CL
        const val ARM = 0x2EL
        const val CHAR_SEPARATION = 0x1D4L

        //Inventory info
        const val ITEM_COUNT = 0x20F4L
        const val FIRST_ITEM = 0x1AF0L
        const val FIRST_QUANTITY = 0x1AF2L
        const val ITEM_SEPARATION = 0x4L
    }
}