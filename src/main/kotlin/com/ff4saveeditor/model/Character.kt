package com.ff4saveeditor.model

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*

class Character() {
    val levelProperty = SimpleIntegerProperty()
    val currHPProperty = SimpleIntegerProperty()
    val maxHPProperty = SimpleIntegerProperty()
    val currMPProperty = SimpleIntegerProperty()
    val maxMPProperty = SimpleIntegerProperty()
    val strengthProperty = SimpleIntegerProperty()
    val speedProperty = SimpleIntegerProperty()
    val staminaProperty = SimpleIntegerProperty()
    val intellectProperty = SimpleIntegerProperty()
    val spiritProperty = SimpleIntegerProperty()
}

class CharacterModel(character: Character) : ItemViewModel<Character>(character) {
    val level = bind(Character::levelProperty)
    val currHP = bind(Character::currHPProperty)
    val maxHP = bind(Character::maxHPProperty)
    val currMP = bind(Character::currMPProperty)
    val maxMP = bind(Character::maxMPProperty)
    val strength = bind(Character::strengthProperty)
    val speed = bind(Character::speedProperty)
    val stamina = bind(Character::staminaProperty)
    val intellect = bind(Character::intellectProperty)
    val spirit = bind(Character::spiritProperty)
}

class CharacterScope: Scope() {
    val character = CharacterModel(Character())
}

class CharacterController: Controller() {
    val characterScope = CharacterScope()
    val character = characterScope.character

    init {
        character.level.value = 0
        character.currHP.value = 0
        character.maxHP.value = 0
        character.currMP.value = 0
        character.maxMP.value = 0
        character.strength.value = 0
        character.speed.value = 0
        character.stamina.value = 0
        character.intellect.value = 0
        character.spirit.value = 0
    }
}