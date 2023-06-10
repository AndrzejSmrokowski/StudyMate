package com.studymate;

import com.studymate.domain.educationalmaterial.Comment;
import com.studymate.domain.educationalmaterial.EducationalMaterial;
import com.studymate.domain.educationalmaterial.MaterialStatus;
import com.studymate.domain.testingmodule.AnswerChoice;
import com.studymate.domain.testingmodule.Exam;
import com.studymate.domain.testingmodule.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public interface DataProviderForIntegrationTests {
    default Exam provideSampleExam() {
        Question question1 = Question.builder()
                .questionId("1")
                .questionText("Czy kiedykolwiek zostal udowodniony paradoks kota Schrodingera?")
                .options(Arrays.asList("Tak", "Nie", "Tylko teoretycznie", "Tylko eksperymentalnie"))
                .correctAnswer(AnswerChoice.B)
                .build();

        Question question2 = Question.builder()
                .questionId("2")
                .questionText("Co to jest dualizm korpuskularno-falowy?")
                .options(Arrays.asList("Zjawisko majace miejsce tylko w mikroskali", "Teoria mowiaca, ze swiatlo ma zarowno charakter falowy, jak i czastkowy", "Hipoteza, ze materia ma tylko charakter czastkowy", "Zjawisko majace miejsce tylko w makroskali"))
                .correctAnswer(AnswerChoice.B)
                .build();

        Question question3 = Question.builder()
                .questionId("3")
                .questionText("Ktora z tych osob przyczynila sie do rozwoju fizyki kwantowej?")
                .options(Arrays.asList("Isaac Newton", "Galileo Galilei", "Max Planck", "Nikola Tesla"))
                .correctAnswer(AnswerChoice.C)
                .build();

        List<Question> questions = Arrays.asList(question1, question2, question3);

        return Exam.builder()
                .id("sample_exam_id")
                .examName("Fizyka Kwantowa - Test Wstepny")
                .questions(questions)
                .build();
    }

    default String bodyWithTestSubmissionDataJson(String testId) {
        return """
                {
                  "testId": "%s",
                  "userId": "",
                  "answers": [
                    {
                      "questionId": "1",
                      "answerChoice": "B"
                    },
                    {
                      "questionId": "2",
                      "answerChoice": "B"
                    },
                    {
                      "questionId": "3",
                      "answerChoice": "C"
                    }
                  ]
                }
                """.formatted(testId).trim();
    }

    default EducationalMaterial getMaterialAboutBiology() {
        return EducationalMaterial.builder()
                .id("2")
                .title("Podstawy biologii")
                .description("Podstawowe informacje na temat biologii")
                .content("Tresc materialu edukacyjnego o biologii")
                .comments(new ArrayList<>())
                .status(MaterialStatus.APPROVED)
                .likes(15)
                .likedBy(new HashSet<>())
                .build();

    }

    default EducationalMaterial getExpectedMaterialAboutQuantumPhysics() {
        Comment comment = Comment.builder()
                .text("Bardzo ciekawy material!")
                .author("User1")
                .build();

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        return EducationalMaterial.builder()
                .id("1")
                .title("Wprowadzenie do fizyki kwantowej")
                .description("Krotkie wprowadzenie do podstaw fizyki kwantowej")
                .content("Tresc materialu edukacyjnego o fizyce kwantowej")
                .comments(comments)
                .status(MaterialStatus.APPROVED)
                .likes(10)
                .likedBy(new HashSet<>())
                .build();
    }
    default String bodyWithEducationalMaterialDataJson() {
        return """
                {
                "title": "Wprowadzenie do fizyki kwantowej",
                "description": "Krotkie wprowadzenie do podstaw fizyki kwantowej",
                "content": "Tresc materialu edukacyjnego o fizyce kwantowej"
                }
                """.trim();
    }
    default String bodyWithUpdatedEducationalMaterialDataJson() {
        return """
                {
                "title": "Wprowadzenie do fizyki klasycznej",
                "description": "Krotkie wprowadzenie do podstaw fizyki klasyczenj",
                "content": "Tresc materialu edukacyjnego o fizyce klasycznej"
                }
                """.trim();
    }
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
