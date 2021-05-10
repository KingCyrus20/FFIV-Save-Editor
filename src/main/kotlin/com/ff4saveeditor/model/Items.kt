package com.ff4saveeditor.model

//Companion object storing map of item id-name pairs
class Items {
    companion object {
        private val itemMap = mutableMapOf<Int, String>(
                0x1389 to "Potion",
                0x138A to "Hi-Potion",
                0x138B to "X-Potion",
                0x138C to "Ether",
                0x138D to "Dry Ether",
                0x138E to "Elixir",
                0x138F to "Megalixir",
                0x1390 to "Phoenix Down",
                0x1391 to "Gold Needle",
                0x1392 to "Maiden's Kiss",
                0x1393 to "Mallet",
                0x1394 to "Diet Ration",
                0x1395 to "Echo Herbs",
                0x1396 to "Eye Drops",
                0x1397 to "Antidote",
                0x1398 to "Cross",
                0x1399 to "Remedy",
                0x139A to "Alarm Clock",
                0x139B to "Unicorn Horn",
                0x139C to "Tent",
                0x139D to "Cottage",
                0x139E to "Emergency Exit",
                0x139F to "Gnomish Bread",
                0x13A0 to "Gysahl Greens",
                0x13A1 to "Gysahl Whistle",
                0x13A2 to "Golden Apple",
                0x13A3 to "Silver Apple",
                0x13A4 to "Soma Drop",
                0x13A5 to "Siren",
                0x13A6 to "Lustful Lali-Ho",
                0x13A7 to "Ninja Sutra",
                0x13AB to "Red Fang",
                0x13AC to "White Fang",
                0x13AD to "Blue Fang",
                0x13AE to "Bomb Fragment",
                0x13AF to "Bomb Crank",
                0x13B0 to "Antarctic Wind",
                0x13B1 to "Arctic Wind",
                0x13B2 to "Zeus' Wrath",
                0x13B3 to "Heavenly Wrath",
                0x13B4 to "Gaia Drum",
                0x13B5 to "Bomb Core",
                0x13B6 to "Stardust",
                0x13B7 to "Lilith's Kiss",
                0x13B8 to "Vampire Fang",
                0x13B9 to "Spider Silk",
                0x13BA to "Silent Bell",
                0x13BB to "Coeurl Whisker",
                0x13BC to "Bestiary",
                0x13BD to "Bronze Hourglass",
                0x13BE to "Silver Hourglass",
                0x13BF to "Gold Hourglass",
                0x13C0 to "Bacchus's Wine",
                0x13C1 to "Hermes Sandals",
                0x13C2 to "Decoy",
                0x13C3 to "Light Curtain",
                0x13C4 to "Lunar Curtain",
                0x13C5 to "Crystal",
                0x13C6 to "Member's Writ",
                0x1447 to "Rainbow Pudding",
                0x1CE9 to "Shuriken",
                0x1CEA to "Fuma Shuriken"
        )

        val universalMap = (itemMap.asSequence() + Equipment.handMap.asSequence() + Equipment.headMap.asSequence() + Equipment.bodyMap.asSequence() +
                Equipment.armMap.asSequence())
                .distinct()
                .groupBy ({it.key}, {it.value})
                .mapValues { (_, values) -> values.joinToString() }

        private fun <String, Int> Map<String, Int>.inverseMap() = map { Pair(it.value, it.key) }.toMap()

        val inverseUniversal = universalMap.inverseMap()
    }
}