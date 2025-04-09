
# CRUD Docker Spring boot Angular

Simple CRUD avec API Spring boot 3.4.4 et front-end Angular 19 et MySQL et phpMyAdmin sous Docker.


## Prérequis
- Docker
- Docker Compose


## Installation

- Construction du projet :
```bash  
git clone https://github.com/ghyslain12/spring-boot-docker-angular.git
sudo chmod -R 777 spring-boot-docker-angular/
cd spring-boot-docker-angular
docker-compose up --build -d
```  

## Utilisation docker

- Monter le conteneur :
```bash  
docker-compose up
```  
- Démonter le conteneur :
```bash  
docker-compose down
```  

## Fonctionnalités
- Back-end: Spring boot Api JWT
- Front-end: Angular & JWT connexion / inscription
- Testing
- Swagger
- Docker Apache Mysql

## Services
- Angular (front-end): http://localhost:4200
- Spring boot (API): http://localhost:8741/api


## API [utilisateur, client, material, ticket, sale...]

Api NelmioApiDocBundle: http://localhost:8741/api/doc

### Créer un utilisateur
![POST](https://img.shields.io/badge/POST-%23ff9800?style=flat-square&logo=git&logoColor=white)  **`/utilisateur`** Ajoute un nouvel utilisateur dans le système.

### Lister tous les utilisateurs
![GET](https://img.shields.io/badge/GET-%2300c853?style=flat-square&logo=git&logoColor=white)  **`/utilisateur`** Récupère la liste de tous les utilisateurs.

### Récupérer un utilisateur
![GET](https://img.shields.io/badge/GET-%2300c853?style=flat-square&logo=git&logoColor=white)  **`/utilisateur/{id}`** Récupère les détails d’un utilisateur spécifique par son ID.

### Mettre à jour un utilisateur
![PUT](https://img.shields.io/badge/PUT-%23009688?style=flat-square&logo=git&logoColor=white)  **`/utilisateur/{id}`** Met à jour les informations d’un utilisateur existant.

### Supprimer un utilisateur
![DELETE](https://img.shields.io/badge/DELETE-%23f44336?style=flat-square&logo=git&logoColor=white)  **`/utilisateur/{id}`** Supprime un utilisateur spécifique par son ID.

## JWT

### Récupérer un token
![POST](https://img.shields.io/badge/POST-%23ff9800?style=flat-square&logo=git&logoColor=white)  **`/login`** Authentifie un utilisateur et retourne un token.

- Activer (application.properties): jwt.enable=true
- Désactiver (application.properties): jwt.enable=false

## Aperçu

![appercu crud](ressources/preview-angular.png)

![appercu crud](ressources/login.png)

![appercu crud](ressources/swagger.png)

![appercu crud](ressources/jwt_ok.png)

![appercu crud](ressources/jwt_nok.png)