# DatabaseProject — SQLite et Android

Laboratoire de la semaine 6 du cours SEG2505 : intégration de SQLite dans une application Android (Java), en suivant les slides 42 à 53 et les tutoriels Android Developers « Enregistrer des données avec SQLite » et « Tester et déboguer votre base de données ».

L'écran principal permet d'ajouter, de rechercher et de supprimer un produit (nom + SKU) dans la table `products` de la base `productDB.db`.

## Classes principales

- `Product` : un produit (id, nom, SKU)
- `MyDBHandler` : étend `SQLiteOpenHelper`, crée la table `products` (`onCreate` / `onUpgrade`) et fournit `addProduct`, `findProduct` et `removeProduct`
- `MainActivity` : relie les boutons Add / Find / Delete à `newProduct`, `lookupProduct` et `deleteProduct`
- `FeedReaderContract` et `FeedReaderDbHelper` : le contrat et l'outil d'aide du tutoriel Android Developers (insertion, lecture avec un `Cursor`, mise à jour, suppression, connexion fermée dans `onDestroy`)

## Tests

`app/src/androidTest` contient des tests instrumentés pour `MyDBHandler` et `FeedReaderDbHelper` :

```
./gradlew connectedDebugAndroidTest
```

## Exécution

Ouvrir le projet dans Android Studio et lancer le module `app` sur un émulateur ou un appareil (minSdk 24). La base peut ensuite être inspectée avec App Inspection > Database Inspector.
