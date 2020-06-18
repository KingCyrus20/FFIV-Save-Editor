package com.ff4saveeditor.model

//Companion object storing map of equipment id-name pairs
class Equipment {
    companion object {
        val handMap = mapOf(
                //Swords
                0x1771 to "Dark Sword",
                0x1772 to "Shadowblade",
                0x1773 to "Deathbringer",
                0x1774 to "Mythgraven Sword",
                0x1775 to "Lustrous Sword",
                0x1776 to "Excalibur",
                0x1777 to "Ragnarok",
                0x1778 to "Ancient Sword",
                0x1779 to "Blood Sword",
                0x177A to "Mythril Sword",
                0x177B to "Sleep Blade",
                0x177C to "Flame Sword",
                0x177D to "Icebrand",
                0x177E to "Stone Blade",
                0x177F to "Avenger",
                0x1780 to "Defender",
                0x1781 to "Fireshard",
                0x1782 to "Frostshard",
                0x1783 to "Thundershard",
                0x1784 to "Onion Sword",

                //Spears
                0x17D5 to "Spear",
                0x17D6 to "Wind Spear",
                0x17D7 to "Flame Lance",
                0x17D8 to "Ice Lance",
                0x17D9 to "Blood Lance",
                0x17DA to "Gungnir",
                0x17DB to "Wyvern Lance",
                0x17DC to "Holy Lance",

                //Knives
                0x1839 to "Mythril Knife",
                0x183A to "Dancing Dagger",
                0x183B to "Mage Masher",
                0x183C to "Knife",

                //Harps
                0x189D to "Dream Harp",
                0x189E to "Lamia Harp",

                //Claws
                0x1901 to "Flame Claw",
                0x1902 to "Ice Claw",
                0x1903 to "Lightning Claw",
                0x1904 to "Faerie Claw",
                0x1905 to "Hell Claw",
                0x1906 to "Cat Claw",

                //Hammers
                0x1965 to "Wooden Hammer",
                0x1966 to "Mythril Hammer",
                0x1967 to "Gaia Hammer",

                //Axes
                0x19C9 to "Dwarven Axe",
                0x19CA to "Ogrekiller",
                0x19CB to "Poison Axe",
                0x19CC to "Rune Axe",

                //Katanas
                0x1A2D to "Kunai",
                0x1A2E to "Ashura",
                0x1A2F to "Kotetsu",
                0x1A30 to "Kiku-ichimonji",
                0x1A31 to "Murasame",
                0x1A32 to "Masamune",

                //Rods
                0x1A91 to "Rod",
                0x1A92 to "Flame Rod",
                0x1A93 to "Ice Rod",
                0x1A94 to "Thunder Rod",
                0x1A95 to "Lilith Rod",
                0x1A96 to "Polymorph Rod",
                0x1A97 to "Faerie Rod",
                0x1A98 to "Stardust Rod",

                //Staves
                0x1AF5 to "Staff",
                0x1AF6 to "Healing Staff",
                0x1AF7 to "Mythril Staff",
                0x1AF8 to "Power Staff",
                0x1AF9 to "Aura Staff",
                0x1AFA to "Sage's Staff",
                0x1AFB to "Rune Staff",

                //Bows
                0x1B59 to "Bow",
                0x1B5A to "Power Bow",
                0x1B5B to "Great Bow",
                0x1B5C to "Killer Bow",
                0x1B5D to "Elven Bow",
                0x1B5E to "Yoichi Bow",
                0x1B5F to "Artemis Bow",

                //Arrows
                0x1BBD to "Medusa Arrows",
                0x1BBE to "Iron Arrows",
                0x1BBF to "Holy Arrows",
                0x1BC0 to "Fire Arrows",
                0x1BC1 to "Ice Arrows",
                0x1BC2 to "Lightning Arrows",
                0x1BC3 to "Blinding Arrows",
                0x1BC4 to "Poison Arrows",
                0x1BC5 to "Silencing Arrows",
                0x1BC6 to "Angel Arrows",
                0x1BC7 to "Yoichi Arrows",
                0x1BC8 to "Artemis Arrows",

                //Whips
                0x1C21 to "Whip",
                0x1C22 to "Chain Whip",
                0x1C23 to "Blitz Whip",
                0x1C24 to "Flame Whip",
                0x1C25 to "Dragon Whisker",

                //Boomerangs
                0x1C85 to "Boomerang",
                0x1C86 to "Moonring Blade",

                //Shields
                0x1F41 to "Iron Shield",
                0x1F42 to "Dark Shield",
                0x1F43 to "Demon Shield",
                0x1F44 to "Lustrous Shield",
                0x1F45 to "Mythril Shield",
                0x1F46 to "Flame Shield",
                0x1F47 to "Ice Shield",
                0x1F48 to "Diamond Shield",
                0x1F49 to "Aegis Shield",
                0x1F4A to "Genji Shield",
                0x1F4B to "Dragon Shield",
                0x1F4C to "Crystal Shield",
                0x1F4D to "Onion Shield"
        )

        val headMap = mapOf(
                //Helms
                0x1FA5 to "Leather Cap",
                0x1FA6 to "Headband",
                0x1FA7 to "Feathered Cap",
                0x1FA8 to "Iron Helm",
                0x1FA9 to "Wizard's Hat",
                0x1FAA to "Green Beret",
                0x1FAB to "Dark Helm",
                0x1FAC to "Hades Helm",
                0x1FAD to "Sage's Miter",
                0x1FAE to "Black Cowl",
                0x1FAF to "Demon Helm",
                0x1FB0 to "Lustrous Helm",
                0x1FB1 to "Gold Hairpin",
                0x1FB2 to "Mythril Helm",
                0x1FB3 to "Diamond Helm",
                0x1FB4 to "Ribbon",
                0x1FB5 to "Genji Helm",
                0x1FB6 to "Dragon Helm",
                0x1FB7 to "Crystal Helm",
                0x1FB8 to "Glass Mask",
                0x1FB9 to "Onion Helm"
        )

        val bodyMap = mapOf(
                //Armor
                0x2009 to "Clothing",
                0x200A to "Prison Garb",
                0x200B to "Leather Clothing",
                0x200C to "Bard's Tunic",
                0x200D to "Gaia Gear",
                0x200E to "Iron Armor",
                0x200F to "Dark Armor",
                0x2010 to "Sage's Surplice",
                0x2011 to "Kenpo Gi",
                0x2012 to "Hades Armor",
                0x2013 to "Black Robe",
                0x2014 to "Demon Armor",
                0x2015 to "Black Belt Gi",
                0x2016 to "Knight's Armor",
                0x2017 to "Luminous Robe",
                0x2018 to "Mythril Armor",
                0x2019 to "Flame Mail",
                0x201A to "Power Sash",
                0x201B to "Ice Armor",
                0x201C to "White Robe",
                0x201D to "Diamond Armor",
                0x201E to "Minerva Bustier",
                0x201F to "Genji Armor",
                0x2020 to "Dragon Mail",
                0x2021 to "Black Garb",
                0x2022 to "Crystal Mail",
                0x2023 to "Adamant Armor",
                0x2024 to "Onion Armor"
        )

        val armMap = mapOf(
                //Gloves
                0x206D to "Ruby Ring",
                0x206E to "Cursed Ring",
                0x206F to "Iron Gloves",
                0x2070 to "Dark Gloves",
                0x2071 to "Iron Armlet",
                0x2072 to "Power Armlet",
                0x2073 to "Hades Gloves",
                0x2074 to "Demon Gloves",
                0x2075 to "Silver Armlet",
                0x2076 to "Gauntlets",
                0x2077 to "Rune Armlet",
                0x2078 to "Mythril Gloves",
                0x2079 to "Diamond Armlet",
                0x207A to "Diamond Gloves",
                0x207B to "Genji Gloves",
                0x207C to "Dragon Gloves",
                0x207D to "Giant's Gloves",
                0x207E to "Crystal Gloves",
                0x207F to "Protect Ring",
                0x2080 to "Crystal Ring",
                0x2081 to "Onion Gloves"
        )
    }
}