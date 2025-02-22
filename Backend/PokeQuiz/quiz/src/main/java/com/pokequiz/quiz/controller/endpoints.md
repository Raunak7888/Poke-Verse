# 🎮 PokeQuiz API - Quiz & Game Service

Welcome to **PokeQuiz API**! This API provides Pokémon-themed quizzes, allowing users to fetch quizzes, filter them by difficulty and region, and even add their own quiz questions.

---

## 🛠️ Base URL
[http://localhost:8080/api/quiz](http://localhost:8080/api/quiz)
(Update this if your deployment URL is different)

---
## 🚀 Endpoints Overview

### **1️⃣ GameController - Quiz Retrieval**
Base Path: `/api/quiz/game`

#### 🟢 Test API
```http
GET /api/quiz/game/hello
```

**Response:**

```json
"Hello World"
```
#### 📜 Get All Quizzes

```http
GET /api/quiz/game/all
```

**Response:**
```json
[
  {
    "id": 1,
    "question": "What type is Pikachu?",
    "options": ["Electric", "Water", "Fire", "Grass"],
    "correctAnswer": "Electric",
    "difficulty": "easy",
    "region": "Kanto"
  }
]
```

#### 🎚️ Get Quizzes by Difficulty

```http
GET /api/quiz/game/difficulty/all?difficulty=easy
```

**Response:**

```json
[
  { "id": 2, "question": "What is Bulbasaur's type?", "difficulty": "easy" }
]
```

#### 🌍 Get Quizzes by Region

```http
GET /api/quiz/game/region/all?region=Kanto
```

#### 🎚️🌍 Get Quizzes by Region & Difficulty

```http
GET /api/quiz/game/region/difficulty/all?region=Kanto&difficulty=medium
```

#### 🎲 Get Random Quizzes

```http
GET /api/quiz/game/random?limit=3
```

#### 🎲🎚️🌍 Get Random Quizzes by Region & Difficulty

```http
GET /api/quiz/game/random/difficulty/region?region=Johto&difficulty=hard&limit=2
```
----------

### **2️⃣ QuizController - Manage Quizzes**

Base Path: `/api/quiz`

#### 🟢 Test API

```http
GET /api/quiz/hello
```

#### ➕ Add a New Quiz

```http
POST /api/quiz/add
Content-Type: application/json
```
**Request Body:**
```json
{
"question":  "Which Legendary Pokémon is known as the 'Sea Basin Pokémon'?",
"difficulty":  "hard",
"region":  "Hoenn",
"quizType":  "Story",
"options":  ["Groudon",  "Kyogre",  "Rayquaza",  "Lugia"],
"correctAnswer":  "Kyogre"
}
```

**Response:**

```json
"Quiz added successfully"
```

#### 📜 Get All Quizzes

```http
GET /api/quiz/all
```

#### 🎚️ Get Quizzes by Difficulty

```http
GET /api/quiz/difficulty/all?difficulty=hard
```

#### 🌍 Get Quizzes by Region

```http
GET /api/quiz/region/all?region=Sinnoh
```

#### 🎚️🌍 Get Quizzes by Region & Difficulty

```http
GET /api/quiz/region/difficulty/all?region=Kalos&difficulty=hard
```

#### 🎲 Get Random Quizzes

```http
GET /api/quiz/random?limit=5
```

#### 🎲🎚️🌍 Get Random Quizzes by Region & Difficulty

```http
GET /api/quiz/random/difficulty/region?region=Unova&difficulty=easy&limit=3
```

----------

## 🏆 Conclusion

This API powers the **PokeQuiz** game by providing quiz questions based on Pokémon regions and difficulty levels. It allows fetching quizzes in multiple ways and adding new quizzes dynamically.

💡 Feel free to expand the API, add more regions, and integrate it with your frontend! 🚀
