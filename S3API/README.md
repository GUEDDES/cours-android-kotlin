# 📱 Architecture MVVM - TP Android (Session 3)

Ce projet implémente une architecture **MVVM (Model-View-ViewModel)** moderne pour récupérer et afficher une liste d'utilisateurs depuis une API REST.

## 🏗️ Vue d'ensemble de l'Architecture

L'architecture est construite autour de trois couches principales, comme illustré dans le diagramme ci-dessous :

![Architecture MVVM](architecture_mvvm.png)

### 1. MODEL (Données & Sources) - "Le Garde-manger" 🍎
Cette couche gère la récupération et la structure des données. Elle est totalement indépendante de l'interface utilisateur.

- **`User.kt` (Data Class)** :
  - *Analogie : La Recette / Le Moule.*
  - Définit la structure d'un utilisateur (nom, email, adresse, etc.). C'est le format de données que nous manipulons.

- **`ApiService.kt` (Interface Retrofit)** :
  - *Analogie : Le Menu.*
  - Définit les opérations possibles vers le serveur (ex: `GET /users`). C'est ici qu'on liste ce qu'on peut commander à l'API.

- **`RetrofitClient.kt` (Singleton)** :
  - *Analogie : Le Cuisine / Service de Livraison.*
  - Configure et crée l'instance de Retrofit. Il utilise **Gson** pour convertir le JSON reçu de l'API en objets Kotlin (`User`).

- **`UserRepository.kt` (Repository)** :
  - *Analogie : Le Garde-manger.*
  - C'est la source de vérité unique pour les données. Le ViewModel lui demande des données sans savoir d'où elles viennent (API, Base de données locale, etc.). Il retourne les données sous forme de `Flow`.

### 2. VIEWMODEL (Logique & État) - "Le Chef" 👨‍🍳
Cette couche fait le lien entre les données et l'écran. Elle prépare les données pour l'affichage.

- **`UserViewModel.kt` (ViewModel)** :
  - *Analogie : Le Chef Cuisinier.*
  - Il commande les ingrédients (données) au Repository.
  - Il prépare les données pour qu'elles soient prêtes à être "servies" à la Vue.
  - Il survit aux changements de configuration (rotation d'écran).

- **`UiState.kt` (Sealed Class)** :
  - *Analogie : Les Feux Tricolores.*
  - Représente l'état actuel de l'écran à tout moment :
    - 🟢 `Success` : Les données sont là, prêtes à être affichées.
    - 🔴 `Error` : Un problème est survenu (pas d'internet, erreur serveur).
    - 🟡 `Loading` : Chargement en cours (afficher une barre de progression).

- **`UserViewModelFactory.kt`** :
  - Un outil technique pour créer le ViewModel en lui injectant ses dépendances (le Repository).

### 3. VIEW (Présentation & UI) - "La Salle" 🍽️
Cette couche est responsable de ce que l'utilisateur voit et touche. Elle est "bête" : elle ne fait qu'afficher ce que le ViewModel lui dit.

- **`MainActivity.kt` (Activity)** :
  - Configure l'écran et observe le `UserViewModel`.
  - Quand l'état change (Loading -> Success), elle met à jour les éléments visuels.

- **`UserAdapter.kt` (RecyclerView Adapter)** :
  - *Analogie : Les Serveurs.*
  - Prend la liste des utilisateurs fournie par l'Activity et crée les vues individuelles pour chaque élément de la liste (`item_user.xml`).

---

## 🔄 Flux des Données (Data Flow)

1. **Lancement** : `MainActivity` démarre et demande des données au `UserViewModel`.
2. **Commande** : `UserViewModel` contacte `UserRepository` pour récupérer les utilisateurs.
3. **Appel Réseau** : `UserRepository` utilise `RetrofitClient` pour appeler l'API.
4. **Réception** : L'API répond, Retrofit convertit le JSON en objets `User`.
5. **Mise à jour** : `UserViewModel` reçoit les données et met à jour son `UiState` à `Success`.
6. **Affichage** : `MainActivity` (qui observe le `UiState`) voit le changement et donne la liste au `UserAdapter` pour l'afficher à l'écran.

## 🛠️ Bibliothèques Utilisées

- **Retrofit2** : Pour les appels API REST.
- **Gson** : Pour le parsing JSON.
- **Coroutines** : Pour la gestion asynchrone (ne pas bloquer l'écran pendant le chargement).
- **ViewModel & LiveData/StateFlow** : Pour l'architecture MVVM.
- **Glide / Coil** (Optionnel) : Pour le chargement d'images.
