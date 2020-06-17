package com.ff4saveeditor.model

//Companion object for save file offset constants
class Offsets {
    companion object {
        //Save slot general info
        val gil = 0x88
        val firstChar = 0x9C
        val time = 0x2304
        val slotSeparation = 0x3DC0

        //Character info
        val currHP = 0xC
        val maxHP = 0x10
        val currMP = 0x14
        val maxMP = 0x18
        val strength = 0x1CA
        val stamina = 0x1CB
        val speed = 0x1CC
        val intellect = 0x1CD
        val spirit = 0x1CE
        val rightHand = 0x26
        val leftHand = 0x28
        val head = 0x2A
        val body = 0x2C
        val arm = 0x2E
        val charSeparation = 0x1D4
    }
}