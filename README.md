Projet JAVA AVANCEE 2016/2016 - Gestion de produits

Des méthodes statics sont implémenter pour génerer et ou simuler des produits 
d'une façon simplifier afin de voir les limites des tests.

Utilisation d'une interface graphique pour interagir avec l'utilisateur.
- 2 espaces :
    - ADMIN : gestion des magasins.
    - MARKET : espace utilisateur d'un magasin.
- Espace ADMIN :
    - Ajout de magasin
    - Suppresion de magasin
- Espace MARKET :
    - Add PRODUCT
    - SHOW BY DISPLAYS :
        - par TYPE D'ETALAGE
        - en arriere plan :
            - afficher une liste de produit filtrer par PRIX saisie sur l'interface
    - SHOW STOCK :
        - affichage de la liste des produits
        - affichage de la liste des produits vendu la semaine derniere
    - CREATE PROMOTION
    - Transfert of Product to another market   
    - SHOW LIST BY PRODUCT SALE
       - affichage de la quantité des produits vendu la semaine derniere par Nom du PRODUIT
    - Vente des produits
        - ajouter le produit vendu dans le stock des produits vendu
        - update stock du produit vendu

- Les données de chaque magasin sont stockées dans un fichier TEXT qui :
    - est crée pour ce magasin lors de sa premiere connexion
    - Chaque produit ajouter est crée sur une ligne du fichier
    - Lors du chargement des données de ce magasin :
        - lire chaque ligne
        - Séparer chaque informations 
        - Ajouter au constructeur du Produit
        - Ajouter ce produit à la liste des Produits
- Vente de produit :
    - Créer le fichier de vente du magasin qui effectue la vente s'il n'existe pas
    - Ajouter le produit vendu dans ce fichier avec la Date de vente
    - Déduire la quantité du produit vendu du Stock du magasin
    - mise à jour du stock aprés la vente de ce produit
- Produit en promotion
    - parcourir la liste des produit du magasin
    - comparer les dates
        - Si date entre current_date et current_date + 1 semaine
            - alors ajout de ce produit dans la liste des produits en promotions
            - reduire le prix de "??%" selon ce qui est saisi par l'utilisateur
        - Sinon ne pas ajouter
        
        
Design patterns utilisées :
- Builder
- Observer
- Decorator
