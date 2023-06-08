package com.studymate;

public interface SampleEducationalMaterialJson {
    default String bodyWithZeroEducationalMaterialsJson() {
        return "[]";
    }
    default String bodyWithTwoEducationalMaterialsJson() {
        return """
                [
                {
                "id": "1",
                "title": "Wprowadzenie do fizyki kwantowej",
                "description": "Krotkie wprowadzenie do podstaw fizyki kwantowej",
                "content": "Tresc materialu edukacyjnego o fizyce kwantowej",
                "comments": [
                {
                "text": "Bardzo ciekawy material!",
                "author": "User1"
                }
                ],
                "status": "APPROVED",
                "likes": 10
                },
                {
                "id":"2",
                "title":"Podstawy biologii",
                "description":"Podstawowe informacje na temat biologii",
                "content":"Treść materiału edukacyjnego o biologii",
                "comments": [],
                "status":"APPROVED",
                "likes":15
                }
                ]
                """.trim();
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
}

