# technologie utilisé

1-	creation d'un compte sur Bluemix (pour deployé le projet) et une base de donnée MySql sur bluemix aussi.
	le lien : http://tp-convolutedly-padnag.eu-gb.mybluemix.net
2-	configuration de cloud foundry
3-	utilisation de l'extention chrome nommée Advanced Rest Client
# pour le projet "tp">
# l'ensemble des fonctionalités
touste les requetes ont été tester en utilisant l'extention chrome nommée Advanced Rest Client
1-
	1- on introduit dans l'url l'adresse suivante : http://tp-convolutedly-padnag.eu-gb.mybluemix.net/zoo-manager/animals
   	2- on selectionne la methode delete
   	3- on envoi les information en cliquant sur send


2.  suppression de tous les animaux
   	1- on introduit dans l'url l'adresse suivante : http://tp-convolutedly-padnag.eu-gb.mybluemix.net/zoo-manager/animals
   	2- on selectionne la methode delete
   	3- on envoi les information en cliquant sur send

3.  creation d'un animal:
	1- l'url : http://tp-convolutedly-padnag.eu-gb.mybluemix.net/zoo-manager/animals/
	2- la methode : post
	3- Payload : {"name":"nouveau_nom","cage":"usa","species":"nouvelle_espèce","id":"<nouveau_id_animal>"}
	4- Content-Type : application/json

4.	modification d'un animal existant
	1- l'url : http://tp-convolutedly-padnag.eu-gb.mybluemix.net/zoo-manager/animals/
	2- la methode : put
	3- Payload : {"name":"nouveau_nom","cage":"usa","species":"nouvelle_espèce","id":"<son_id>"}
	4- Content-Type : application/json

5. suppresion d'un animal par son id
	1- l'url : http://tp-convolutedly-padnag.eu-gb.mybluemix.net/zoo-manager/animals/<id_animal_a_supprimé>
	2- la methode : delete

6. 	rechercher un animal par son nom
 	exemple d'utilisation : http://tp-convolutedly-padnag.eu-gb.mybluemix.net/zoo-manager/animals/find/byName/Canine

7. 	rechercher un animal par sa position
	exemple d'utilisation : http://tp-convolutedly-padnag.eu-gb.mybluemix.net/zoo-manager/animals/find/at/{latitude}/{longitude}

8. 	rechercher les animaux proche d'une position
	exemple d'utilisation : http://tp-convolutedly-padnag.eu-gb.mybluemix.net/zoo-manager/animals/find/near/{latitude}/{longitude}
9. suppresion d'un animal par ville
	1- l'url : http://tp-convolutedly-padnag.eu-gb.mybluemix.net/zoo-manager/animals/remove/<ville>
	2- la methode : delete

/**********************************************************************************************************************************************/

# pour le projet "tpAvecPersistance"

on a mis en place la base de donnée, mais on deployant l'application, on a eu une erreur lors de son execution sur bluemix.

conclusion:

-on a décider de deployé le projet "tp" au moins qu'il repond à l'ensemble des fonctionalités.
-on a fournie le projet "tpAvecPersistance" pour voir notre architecture d'utilisation de la base de donné. 



