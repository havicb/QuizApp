package com.example.quizapp.presentation.main.quiz

enum class QuizCategory(val category: String, val apiValue: Int) {
    GENERAL_KNOWLEDGE("General knowledge", 9),
    BOOKS("Entertainment: Books", 10),
    FILM("Entertainment: Film", 11),
    MUSIC("Entertainment: Music", 12),
    TELEVISION("Entertainment: Television", 14),
    VIDEO_GAMES("Entertainment: Video games", 15),
    SCIENCE_NATURE("Science & nature", 17),
    COMPUTERS("Science: Computers", 18),
    MATHEMATICS("Science: Mathematics", 19),
    SPORTS("Sports", 21),
    GEOGRAPHY("Geography", 22),
    HISTORY("History", 23),
    POLITICS("Politics", 24),
    ART("Art", 25),
    CELEBRITIES("Celebrities", 26),
    ANIMALS("Animals", 27)
}
