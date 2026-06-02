# 📚 KinShelf

KinShelf est une API REST développée avec Spring Boot permettant de gérer une bibliothèque de livres et les interactions des utilisateurs autour de celle-ci. L'application est faites de sortes à ce qu'un petit groupe d'utilisateurs puissent partager leurs bibliothèques et leur passion pour la lecture.

L’application permet de :

* gérer un catalogue de livres (auteurs, genres, catégories, séries, éditeurs)
* créer et organiser une bibliothèque personnelle
* suivre l’état de lecture (lu, possédé, note, commentaire)
* gérer le prêt de livres entre utilisateurs

Ce projet met en œuvre une architecture propre basée sur :

* Spring Boot
* Spring Data JPA
* Lombok
* une séparation claire entre DTO, services, contrôleurs et entités

L’objectif est de proposer une base solide, évolutive et maintenable pour une application complète de gestion de bibliothèque.

---

## Repositories

| Partie | Repository |
|---|---|
| Docker | [kinshelf-back](https://github.com/JGifapme/KinShelf-Docker) |
| 🖥️ Back-end | [kinshelf-back](https://github.com/JGifapme/KinShelf) |
| 🎨 Front-end | [kinshelf-front](https://github.com/JGifapme/KinShelf-Front) |

## Stack technique

| Côté | Technologies |
|---|---|
| Back-end | Java 21, Spring Boot 4, Spring Security, JWT |
| Front-end | Vue.js 3, TypeScript, Pinia, Axios |
| Base de données | MySQL 8 |

## Prérequis

- Java 21
- Maven
- MySQL 8

## Installation

### 1. Cloner le repository

```bash
git clone <url-back>
cd kinshelf-back
```

### 2. Créer la base de données

Se connecter à MySQL et créer la base :

```sql
CREATE DATABASE kinshelf CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

Utiliser le fichier kinshelf.sql pour créer les tables
```

### 3. Configurer les variables d'environnement

Créer un fichier `.env` à la racine du projet :

```env
DB_URL=jdbc:mysql://localhost:3306/kinshelf
DB_USERNAME=root
DB_PASSWORD=tonmotdepasse
JWT_SECRET=taclesecrete
```

> **Note :** La `JWT_SECRET` doit être une chaîne Base64 d'au moins 256 bits.
> Tu peux en générer une avec la commande :
> ```bash
> openssl rand -base64 32
> ```

### 4. Configurer le profil de développement

Dans `src/main/resources/application-dev.yml`, vérifier que les cookies sont en mode non-sécurisé pour le développement local :

```yaml
app:
  cookie:
    secure: false
  cors:
    allowed-origins:
      - http://localhost:5173
```

### 5. Lancer l'application

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

L'API est accessible sur `http://localhost:8080`.

## Endpoints principaux

| Méthode | Route | Description | Auth |
|---|---|---|---|
| POST | `/api/auth/register` | Inscription | Non |
| POST | `/api/auth/login` | Connexion | Non |
| POST | `/api/auth/logout` | Déconnexion | Non |
| GET | `/api/books` | Liste des livres | Oui |
| GET | `/api/books/:slug` | Détail d'un livre | Oui |
| GET | `/api/users/me` | Profil connecté | Oui |
| GET | `/api/users/me/stats` | Statistiques | Oui |
| PATCH | `/api/users/:id` | Modifier le profil | Oui |
| GET | `/api/loans` | Mes prêts | Oui |
| POST | `/api/loans/:bookId/to/:borrowerId` | Créer un prêt | Oui |
| PATCH | `/api/loans/:id` | Retourner un prêt | Oui |

## Sécurité

L'authentification est basée sur des **JWT stockés dans un cookie HttpOnly**. Le token est automatiquement envoyé par le navigateur à chaque requête — aucune gestion manuelle côté client n'est nécessaire.

## Tests avec Postman

Postman gère les cookies automatiquement via son Cookie Jar. Il suffit de :
1. Appeler `POST /api/auth/login` avec les identifiants
2. Les requêtes suivantes envoient automatiquement le cookie
