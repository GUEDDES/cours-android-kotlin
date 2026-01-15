# Cours Android Avancé avec Kotlin

[![GitHub](https://img.shields.io/badge/GitHub-GUEDDES-blue)](https://github.com/GUEDDES)
[![License](https://img.shields.io/badge/License-Educational-green)](LICENSE)

## 📚 Description

Ce dépôt contient l'ensemble du matériel pédagogique pour le cours **Android Avancé avec Kotlin**. Il est destiné aux étudiants ayant déjà des connaissances de base en développement Android avec Java.

## 🎯 Objectifs du Cours

- Maîtriser le langage Kotlin pour le développement Android
- Comprendre les équivalences entre Java et Kotlin
- Apprendre les meilleures pratiques de développement Android moderne
- Utiliser les composants d'architecture Android (ViewModel, LiveData, Room, etc.)
- Développer des applications Android robustes et performantes

## 📂 Structure du Dépôt

```
cours-android-kotlin/
│
├── README.md                          # Ce fichier
├── .gitignore                         # Fichiers à ignorer
├── COMPARAISON_JAVA_KOTLIN.md        # Guide complet de comparaison Java-Kotlin
│
├── Seance1/                          # Séance 1: Rappel Android et Transition Java-Kotlin
│   ├── cours_seance1.tex             # Cours théorique en LaTeX
│   ├── cours_seance1.pdf             # PDF du cours (généré)
│   ├── EXERCICES.md                  # Exercices guidés
│   └── ContactsApp/                  # Code source de l'application du TP
│       ├── app/
│       ├── build.gradle
│       └── README.md
│
├── Seance2/                          # Séance 2: (À venir)
│   └── ...
│
└── ressources/                       # Ressources communes
    ├── images/
    └── templates/
```

## 📖 Contenu des Séances

### Séance 1: Rappel Android et Transition Java-Kotlin
**Durée:** 3 heures (1h30 théorie + 1h30 pratique)

**Partie Théorique:**
- Introduction à Kotlin
- Syntaxe de base: variables, fonctions, classes
- Null Safety en Kotlin
- Activity et cycle de vie
- Intent et navigation
- RecyclerView et Adapters
- Extensions Kotlin

**Partie Pratique:**
- Application de gestion de contacts
- Utilisation de View Binding
- Navigation entre Activities
- RecyclerView avec adapter personnalisé

**Exercices:**
1. Ajouter un contact
2. Supprimer un contact
3. Rechercher des contacts

### Séance 2: Architecture MVVM et ViewModel (À venir)
- Architecture Components
- ViewModel et LiveData
- Data Binding
- Repository Pattern

### Séance 3: Persistance des Données avec Room (À venir)
- Introduction à Room
- Entities, DAO et Database
- Migrations
- Coroutines avec Room

### Séance 4: Navigation Component (À venir)
- Navigation Graph
- Safe Args
- Deep Links
- Bottom Navigation

## 🚀 Prérequis

### Logiciels requis:
- **Android Studio** Jellyfish (2023.3.1) ou supérieur
- **JDK** 17 ou supérieur
- **SDK Android** API 24 (Android 7.0) minimum
- **Git** pour cloner le dépôt

### Connaissances requises:
- Programmation Java (niveau intermédiaire)
- Développement Android de base:
  - Activities et Fragments
  - Layouts XML
  - Gestion des événements
  - Intent
  - ListView/RecyclerView

## 📥 Installation

### 1. Cloner le dépôt
```bash
git clone https://github.com/GUEDDES/cours-android-kotlin.git
cd cours-android-kotlin
```

### 2. Ouvrir un projet dans Android Studio
```bash
cd Seance1/ContactsApp
# Puis ouvrir ce dossier dans Android Studio
```

### 3. Synchroniser Gradle
- Android Studio synchronisera automatiquement les dépendances
- Attendre la fin de la synchronisation avant de lancer l'application

## 📝 Compilation des Documents LaTeX

Pour compiler les documents de cours:

```bash
cd Seance1
pdflatex cours_seance1.tex
pdflatex cours_seance1.tex  # Deux fois pour la table des matières
```

Ou utilisez votre éditeur LaTeX préféré (TeXstudio, Overleaf, etc.)

## 📚 Ressources Complémentaires

### Documentation officielle:
- [Kotlin Language](https://kotlinlang.org/docs/home.html)
- [Android Developers](https://developer.android.com/kotlin)
- [Android Codelabs](https://developer.android.com/courses)

### Tutoriels:
- [Kotlin Koans](https://play.kotlinlang.org/koans) - Exercices interactifs
- [Android Kotlin Fundamentals](https://developer.android.com/courses/kotlin-android-fundamentals/overview)
- [Udacity - Developing Android Apps with Kotlin](https://www.udacity.com/course/developing-android-apps-with-kotlin--ud9012)

### Livres recommandés:
- "Kotlin in Action" par Dmitry Jemerov et Svetlana Isakova
- "Android Development with Kotlin" par Marcin Moskala et Igor Wojda
- "Head First Kotlin" par Dawn Griffiths et David Griffiths

## 🤝 Contribution

Les contributions sont les bienvenues! Si vous trouvez des erreurs ou souhaitez améliorer le contenu:

1. Fork le projet
2. Créez une branche pour votre fonctionnalité (`git checkout -b feature/amelioration`)
3. Committez vos changements (`git commit -m 'Ajout d'une amélioration'`)
4. Pushez vers la branche (`git push origin feature/amelioration`)
5. Ouvrez une Pull Request

## 📧 Contact

**Enseignant:** Dr. Abdelweheb GUEDDES
- GitHub: [@GUEDDES](https://github.com/GUEDDES)
- Email: [Votre email professionnel]
- Institution: [Votre institution]

## 📄 Licence

Ce projet est destiné à un usage éducatif. Tous les droits sont réservés à l'enseignant et à l'institution.

## 🙏 Remerciements

- Google pour Android et Kotlin
- JetBrains pour Kotlin et IntelliJ IDEA
- La communauté Android et Kotlin
- Tous les étudiants qui contribuent à améliorer ce cours

---

## 📅 Calendrier des Séances

| Séance | Date | Sujet | Statut |
|--------|------|-------|--------|
| 1 | Semaine 1 | Rappel Android et Transition Java-Kotlin | ✅ Disponible |
| 2 | Semaine 2 | Architecture MVVM et ViewModel | 🚧 En cours |
| 3 | Semaine 3 | Persistance avec Room | 📋 Planifié |
| 4 | Semaine 4 | Navigation Component | 📋 Planifié |
| 5 | Semaine 5 | Coroutines et Flow | 📋 Planifié |
| 6 | Semaine 6 | Retrofit et API REST | 📋 Planifié |
| 7 | Semaine 7 | Tests Unitaires | 📋 Planifié |
| 8 | Semaine 8 | Projet Final | 📋 Planifié |

---

**Dernière mise à jour:** Janvier 2026  
**Version:** 1.0.0

⭐ N'oubliez pas de mettre une étoile au dépôt si vous le trouvez utile!