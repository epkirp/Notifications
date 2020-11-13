package ru.webant.domain

sealed class Action(open val action: String) {
    object DefaultAction : Action(STRING_DEFAULT_ACTION)
    object DrinkWater : Action(STRING_DRINK_WATER)
    object DoExercises : Action(STRING_DO_EXERCISES)

    companion object {

        private const val STRING_DEFAULT_ACTION = "Default action"
        private const val STRING_DRINK_WATER = "Drink water"
        private const val STRING_DO_EXERCISES = "Do exercises"

        fun stringActionToAction(action: String): Action {
            return when (action) {
                STRING_DEFAULT_ACTION -> DefaultAction
                STRING_DRINK_WATER -> DrinkWater
                STRING_DO_EXERCISES -> DoExercises
                else -> throw IllegalStateException(WRONG_ACTION_ERROR)
            }
        }

        fun actionToStringAction(action: Action): String {
            return when (action) {
                DefaultAction -> STRING_DEFAULT_ACTION
                DrinkWater -> STRING_DRINK_WATER
                DoExercises -> STRING_DO_EXERCISES
            }
        }

        fun getAllActions(): List<String> {
            return arrayListOf(STRING_DEFAULT_ACTION, STRING_DRINK_WATER, STRING_DO_EXERCISES)
        }
    }
}