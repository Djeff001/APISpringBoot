# SpringBootAPI

# Présentation de l'API
Cette API permet de gérer l'enregistrement d'utilisateur et l'affichage de ses informations

URL de base : 'http://localhost:8080/api/v1/users

# Ressources

## User
représente un utilisateur

## Méthodes
### 'POST /users'
### Description : créer un nouvel utilisateur
### Paramètres : 
    'name'(corp de la requète) : le nom de l'utilisateur
    'birthdate'(corp de la requète) : la date de naissance de l'utilisteur
    'country'(corp de la requète) : le pays de résidence de l'utilisteur
    'phone'(corp de la requète) : le numéro de téléphone de l'utilisteur
    'gender'(corp de la requète) : le sexe de l'utilisateur

### Réponse
    201 Created:        - détail de l'utilisateur au format json
    
    400 Bad Request:    - les champ est obligatoire
                        - le numéro de téléphone est invalid

    406 Not Acceptable: - le format de la date de naissance est invalide
                        - l'utilisateur doit être adulte et résident en France

    500 Internal Server Error: - autre erreurs(gender incorecte, etc.)


### 'GET /users/{id}'
### Description : récuperer un utilisateur spécifique par son ID
### Paramètres :
    '{id}'(parametre de l'url) : L'ID de l'utilisateur
### Réponse
    200 OK:        - détail de l'utilisateur au format json
    
    404 Not Found: - l'utilisateur n'a pas été trouvé

## La gestion des erreurs

    {
        "message": "field must not be null",
        "code": 400
    }
    
    {
        "message": "phone not valid",
        "code": 400
    }

    {
        "message": "Birthdate should be in this format d/MM/yyyy",
        "code": 406
    }

    {
        "message": "Only adult French residents are allowed to create an account!",
        "code": 406
    }

    {
        "message": "No enum constant springboot.api.model.Gender.MAL3E",
        "code": 500
    }


# Base de données
L'API utilise une base de données intégrée H2 pour la persistance des données.

Bdd : base de données intégrée H2
URL de connexion de l"API :jdbc:h2:mem:db
URL de connexion de Test :jdbc:h2:mem:testdb
Nom d'utilisateur:sa
Mot de passe Api : password
Mot de passe Test : (pas de mot de passe)

# Tests

L'API comprend une suite complète de tests pour garantir sa fonctionnalité et ses performances.

### Tests unitaires :

#### UserServiceTest: tester la logique métier du service

### Tests d'integration

#### UserControllerTest : tester l'integration du controlleur de l'utilisateur avec les sous couches service et repository


# AOP Logging

Le logging est appliqué à tous les appels de l'API pour capturer les détails de la demande et de la réponse.

### Les Logs

#### Log avec exception

    --- : createUser started with parameters: {userDto=UserDto(id=null, name=toto, birthdate=1/12/2000, country=Franc, phone=+33652955856, gender=MALE)}
    --- : createUser failed with exception message: Only adult French residents are allowed to create an account!
    --- : createUser executed in 0 ms

#### Log avec réponse

    --- : createUser started with parameters: {userDto=UserDto(id=null, name=toto, birthdate=1/12/2000, country=France, phone=+33652469856, gender=MALE)}
    --- : createUser finished with return value: UserDto(id=3, name=toto, birthdate=2000-12-01, country=France, phone=+33652469856, gender=MALE)
    --- : createUser executed in 2 ms

