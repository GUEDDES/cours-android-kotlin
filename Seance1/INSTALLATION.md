# Guide d'Installation - ContactsApp (Séance 1)

## Méthode 1: Créer un Nouveau Projet et Copier les Fichiers

### Étape 1: Créer le Projet
1. Ouvrir **Android Studio**
2. **File > New > New Project**
3. Sélectionner **Empty Activity**
4. Configurer:
   - **Name**: ContactsApp
   - **Package name**: com.example.contactsapp
   - **Language**: **Kotlin**
   - **Minimum SDK**: API 24 (Android 7.0)
5. Cliquer sur **Finish**

### Étape 2: Activer View Binding

Dans `app/build.gradle.kts`, ajouter dans le bloc `android`:

```kotlin
android {
    // ...
    buildFeatures {
        viewBinding = true
    }
}
```

Cliquer sur **Sync Now**

### Étape 3: Copier les Fichiers Sources

#### A. Créer la structure des packages

Dans `app/src/main/java/com/example/contactsapp/`:
- Créer package `model`
- Créer package `adapter`

#### B. Copier les fichiers Kotlin

1. **model/Contact.kt** → `app/src/main/java/com/example/contactsapp/model/`
2. **adapter/ContactAdapter.kt** → `app/src/main/java/com/example/contactsapp/adapter/`
3. **MainActivity.kt** → `app/src/main/java/com/example/contactsapp/`
4. **ContactDetailActivity.kt** → `app/src/main/java/com/example/contactsapp/`

#### C. Copier les fichiers de layout

1. **layouts/activity_main.xml** → `app/src/main/res/layout/activity_main.xml`
2. **layouts/item_contact.xml** → `app/src/main/res/layout/item_contact.xml`
3. **layouts/activity_contact_detail.xml** → `app/src/main/res/layout/activity_contact_detail.xml`

#### D. Copier le fichier strings.xml

1. **res/values/strings.xml** → `app/src/main/res/values/strings.xml`

### Étape 4: Mettre à jour AndroidManifest.xml

Remplacer le contenu de `app/src/main/AndroidManifest.xml` par le fichier fourni **AndroidManifest.xml**

### Étape 5: Ajouter les Dépendances

Dans `app/build.gradle.kts`, vérifier que ces dépendances sont présentes:

```kotlin
dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
}
```

Cliquer sur **Sync Now**

### Étape 6: Lancer l'Application

1. **Build > Make Project** (Ctrl+F9)
2. Sélectionner un émulateur ou connecter un appareil
3. **Run > Run 'app'** (Shift+F10)

---

## Méthode 2: Cloner le Dépôt GitHub

```bash
# Cloner le dépôt
git clone https://github.com/GUEDDES/cours-android-kotlin.git

# Naviguer vers la séance
cd cours-android-kotlin/Seance1
```

Puis ouvrir le dossier `Seance1` comme projet Android dans Android Studio.

---

## Vérification

L'application doit:
- ✅ Afficher une liste de 5 contacts
- ✅ Permettre de cliquer sur un contact
- ✅ Afficher les détails du contact sélectionné
- ✅ Avoir un bouton "Retour" fonctionnel
- ✅ Avoir un FAB qui affiche un Toast

---

## Dépannage

### Erreur: "Unresolved reference: databinding"
**Solution**: Activer View Binding dans `build.gradle.kts` et synchroniser

### Erreur: "Cannot find symbol ItemContactBinding"
**Solution**: 
1. Build > Clean Project
2. Build > Rebuild Project
3. File > Invalidate Caches / Restart

### Erreur: "Activity not found in manifest"
**Solution**: Vérifier que `ContactDetailActivity` est bien déclarée dans `AndroidManifest.xml`

### L'application crashe au lancement
**Solution**: Vérifier les logs (Logcat) et s'assurer que:
- Les packages sont corrects
- Les layouts sont bien nommés
- View Binding est activé

---

## Ressources

- [Documentation Android](https://developer.android.com)
- [Guide Kotlin](https://kotlinlang.org/docs/home.html)
- [View Binding](https://developer.android.com/topic/libraries/view-binding)
