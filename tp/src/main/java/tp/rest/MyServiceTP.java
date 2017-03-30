package tp.rest;

import tp.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Source;
import javax.xml.ws.http.HTTPException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * 
 * 
 * @author juba & larbi
 *
 */
@Path("/zoo-manager/")
public class MyServiceTP {

    private Center center = new Center(new LinkedList<>(), new Position(49.30494d, 1.2170602d), "Biotropica");

    public MyServiceTP() {
        // Fill our center with some animals
        Cage usa = new Cage(
                "usa",
                new Position(49.305d, 1.2157357d),
                25,
                new LinkedList<>(Arrays.asList(
                        new Animal("Tic", "usa", "Chipmunk", UUID.randomUUID()),
                        new Animal("Tac", "usa", "Chipmunk", UUID.randomUUID())
                ))
        );

        Cage amazon = new Cage(
                "amazon",
                new Position(49.305142d, 1.2154067d),
                15,
                new LinkedList<>(Arrays.asList(
                        new Animal("Canine", "amazon", "Piranha", UUID.randomUUID()),
                        new Animal("Incisive", "amazon", "Piranha", UUID.randomUUID()),
                        new Animal("Molaire", "amazon", "Piranha", UUID.randomUUID()),
                        new Animal("De lait", "amazon", "Piranha", UUID.randomUUID())
                ))
        );

        center.getCages().addAll(Arrays.asList(usa, amazon));
    }

    /**
     * GET method bound to calls on /animals/{something}
     */
    
  //rechercher un animal par son id
    @GET
    @Path("/animals/{id}/")
    @Produces("application/xml")
    public Animal getAnimal(@PathParam("id") String animal_id) throws JAXBException {
        try {
            return center.findAnimalById(UUID.fromString(animal_id));
        } catch (AnimalNotFoundException e) {
            throw new HTTPException(404);
        }
    }
    
  //rechercher un animal par son nom
    @GET
    @Path("/find/byName/{name}/")
    @Produces("application/xml")
    public Animal find_animal_name(@PathParam("name") String name) throws JAXBException {
        try {
            return center.findAnimalByName(name);
        } catch (AnimalNotFoundException e) {
            throw new HTTPException(404);
        }
    }
    
    //rechercher les animaux proche d'une position

    @GET
    @Path("/find/near/{latitude}/{longitude}/")
    @Produces("application/xml")
    public Cage find_animal_near(@PathParam("latitude")String latitude,@PathParam("longitude") String longitude) throws JAXBException {
    	Position position=new Position();
    	position.setLatitude(Double.parseDouble(latitude));
    	position.setLongitude(Double.parseDouble(longitude));
    	try {
    		return this.center.findAnimalNearPosition(position);
        } catch (AnimalNotFoundException e) {
            throw new HTTPException(404);
        }
    }
    
 
    //rechercher un animal par sa position
    @GET
    @Path("/find/at/{latitude}/{longitude}/")
    @Produces("application/xml")
    public Animal find_animal_at(@PathParam("latitude")String latitude,@PathParam("longitude") String longitude) throws JAXBException {
    	Position position=new Position();
    	position.setLatitude(Double.parseDouble(latitude));
    	position.setLongitude(Double.parseDouble(longitude));
    	try {
            return this.center.findAnimalByPosition(position);
        } catch (AnimalNotFoundException e) {
            throw new HTTPException(404);
        }
    }
    
    /**
     * GET method bound to calls on /animals
     */
    @GET
    @Path("/animals/")
    @Produces("application/xml")
    public Center getAnimals(){
        return this.center;
    }

    /**
     * POST method bound to calls on /animals
     */
    @POST
    @Path("/animals/")
    @Consumes({"application/xml", "application/json" })
    public Center postAnimals(Animal animal) throws JAXBException {
        this.center.getCages()
                .stream()
                .filter(cage -> cage.getName().equals(animal.getCage()))
                .findFirst()
                .orElseThrow(() -> new HTTPException(404))
                .getResidents()
                .add(animal);
        return this.center;
    }
    
    //suppression de tous les animaux
    @DELETE
    @Path(value="animals")
 public void supprimer_tout_animal(){
     System.out.println("!!supprimer ou non !!!");
     /* recuperer toutes les cages */
     	System.out.println("rmimez");
    	 Collection<Cage> collection_cages = this.center.getCages();
         Iterator<Cage> iter_cages = collection_cages.iterator();
         /*parcourir chaque cage*/
         while(iter_cages.hasNext()){
             Cage cage = iter_cages.next();
             /*supprimer tout les animaux*/
             cage.getResidents().removeAll(cage.getResidents());
         }

 }
 
    
    //suppresion d'un animal par son id
    @DELETE
    @Path(value="animals/{animal_id}")
    public void supprimer_animal_par_id(@PathParam(value="animal_id")String animal_id){
    	{
        	/* recuperer toutes les cages */
        	Collection<Cage> cages = this.center.getCages();
            Cage cage;
            Collection<Animal> collection_animals;
            Iterator<Cage> iter_cage = cages.iterator();
            Iterator<Animal> iter_animal;
            
           /*parcourir la collection des cage*/
            while(iter_cage.hasNext()){
            	cage = iter_cage.next();
            	collection_animals = cage.getResidents();
            	iter_animal = collection_animals.iterator();
            	/*parcourir la listes des animaux par cage jusqu'a trouv� 
            	 * l'animal rechercher (� supprimer)*/
            	while(iter_animal.hasNext()){
            		Animal animal= iter_animal.next();
            	
            		if(animal.getId().equals(UUID.fromString(animal_id))){
            			collection_animals.remove(animal);
            		}
            	}
            }
        }
    }
    
    @DELETE
    @Path(value="animals/remove/{ville}")
    public void supprimer_animal_by_ville(@PathParam("ville")String ville){
    	Collection<Cage> collection_cages = this.center.getCages();
        Iterator<Cage> iter_cages = collection_cages.iterator();
        /*parcourir chaque cage*/
        while(iter_cages.hasNext()){
            Cage cage = iter_cages.next();
            /*supprimer tout les animaux*/
            if(cage.getName().equals(ville))
            cage.getResidents().removeAll(cage.getResidents());   
        }
    }
    
    
    //modifier un animal
    @PUT
    @Path(value="animals/")
    @Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
    public void put_animal_by_id(Animal animal_put){
    	
        	/* recuperer les information a mettre a jour selon l'id de l'animal � modifier*/
        	//Animal animal_put;
        	
        	/* recuperer toutes les cages */
            Collection<Cage> collection_cages = this.center.getCages();
             Cage cage;
             Collection<Animal> collection_animals;
             Iterator<Cage> iter_cages = collection_cages.iterator();
             Iterator<Animal> iter_animals;
             
             /*parcourir chaque cage*/
             while(iter_cages.hasNext()){
             	cage = iter_cages.next();
             	collection_animals = cage.getResidents();
             	iter_animals=collection_animals.iterator();
             	
             	/*parcourir les animaux de chaque cage*/
             	while(iter_animals.hasNext()){
             		Animal animal= iter_animals.next();
             		/*si l'animal recherch� est retrouv� par son Id alors le modifier:*/
             		if(animal.getId().equals(animal_put.getId())){
             			/**/
             			animal.setCage(animal_put.getCage());
             			animal.setName(animal_put.getName());
             			animal.setSpecies(animal_put.getSpecies());
             		}
             	}
             } 
    }
    
    
    //modifier un ensembles d'animaux
    @PUT
    @Path(value="/animals/all")
    public void put_animal(Animal animal){
      	/*recuperer l'animal envoy� dans source*/
       // Animal animal = unmarshalAnimal(source);
        /* recuperer toutes les cages */
    	 Collection<Cage> collection_cages = this.center.getCages();
         Collection<Animal> collection_animals;
         Iterator<Cage> iter_cages = collection_cages.iterator();
         Iterator<Animal> iter_animals;
        
        while(iter_cages.hasNext()){
            Cage cage = iter_cages.next();
            	  if(cage.getName().equals(animal.getCage())){
                      for(Animal a: cage.getResidents()){
                      	//if(a.getId()==centre.getCages().)
                      	/*modifier tout les animaux*/
                          a.setName(animal.getName());
                          //a.setCage(animal.getCage());
                          //a.setSpecies(animal.getSpecies());
                      }
                  }
            }
          
        }
}
    
   


    

