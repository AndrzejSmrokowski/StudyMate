package com.studymate;

public interface SampleTestJson {
    default String bodyWithTestDataJson() {
        return """
                {
                  "testName": "Fizyka Kwantowa - Test Wstepny",
                  "questions": [
                    {
                      "questionId": "1",
                      "questionText": "Czy kiedykolwiek zostal udowodniony paradoks kota Schrodingera?",
                      "options": [
                        "Tak",
                        "Nie",
                        "Tylko teoretycznie",
                        "Tylko eksperymentalnie"
                      ],
                      "correctAnswer": "B"
                    },
                    {
                      "questionId": "2",
                      "questionText": "Co to jest dualizm korpuskularno-falowy?",
                      "options": [
                        "Zjawisko majace miejsce tylko w mikroskali",
                        "Teoria mowiaca, ze swiatlo ma zarowno charakter falowy, jak i czastkowy",
                        "Hipoteza, ze materia ma tylko charakter czastkowy",
                        "Zjawisko majace miejsce tylko w makroskali"
                      ],
                      "correctAnswer": "B"
                    },
                    {
                      "questionId": "3",
                      "questionText": "Ktora z tych osob przyczynila sie do rozwoju fizyki kwantowej?",
                      "options": [
                        "Isaac Newton",
                        "Galileo Galilei",
                        "Max Planck",
                        "Nikola Tesla"
                      ],
                      "correctAnswer": "C"
                    }
                  ]
                }
                """.trim();
    }
}
