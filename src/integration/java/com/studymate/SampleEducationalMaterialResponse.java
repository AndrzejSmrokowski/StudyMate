package com.studymate;

public interface SampleEducationalMaterialResponse {
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
}
