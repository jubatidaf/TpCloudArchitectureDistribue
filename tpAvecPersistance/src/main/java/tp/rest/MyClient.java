package tp.rest;

import tp.model.Animal;
import tp.model.Cage;
import tp.model.Center;
import tp.model.Position;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class MyClient {
    private Service service;
    private JAXBContext jc;

    private static final QName qname = new QName("", "");
    private static final String url = "tp-convolutedly-padnag.eu-gb.mybluemix.net/zoo-manager";

    private MyClient() {
        try {
            jc = JAXBContext.newInstance(Center.class, Cage.class, Animal.class, Position.class);
        } catch (JAXBException je) {
            System.out.println("Cannot create JAXBContext " + je);
        }
    }

    private void get_animals() throws JAXBException {
        sendRequest("/animals", "GET", null);
    }

    private void add_animal(Animal animal) throws JAXBException {
        sendRequest("/animals", "POST", new JAXBSource(jc, animal));
    }

    private void add_cage(Cage cage) throws JAXBException {
        sendRequest("/cages", "POST", new JAXBSource(jc, cage));
    }

    private void remove_animals() throws JAXBException {
        sendRequest("/animals", "DELETE", null);
    }

    private void update_animals(Animal animal) throws JAXBException {
        sendRequest("/animals", "PUT", new JAXBSource(jc, animal));
    }

    private void update_animals() throws JAXBException {
        sendRequest("/animals", "PUT", null);
    }

    private void add_animal_with_id(Animal animal, String id) throws JAXBException {
        sendRequest("/animals/" + id, "POST", new JAXBSource(jc, animal));
    }

    private void remove_animal_with_id(String id) throws JAXBException {
        sendRequest("/animals/" + id, "DELETE", null);
    }

    private void update_animal_with_id(Animal animal, String id) throws JAXBException {
        sendRequest("/animals/" + id, "PUT", new JAXBSource(jc, animal));
    }

    private void find_by_name(String name) throws JAXBException, UnsupportedEncodingException {
        sendRequest("/find/byName/" + URLEncoder.encode(name, "UTF-8"), "GET", null);
    }

    private void find_at(Position position) throws Exception {
        sendRequest("/find/at/", "POST", new JAXBSource(jc, position));
    }

    private void find_near(Position position) throws Exception {
        sendRequest("/find/near/", "POST", new JAXBSource(jc, position));
    }

    private void printSource(Source s) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(s, new StreamResult(System.out));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void sendRequest(String urlPrefixe, String method, Source source) {
        try {
            URL mainurl = new URL(url + urlPrefixe);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) mainurl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(method);
            if (source != null) {
                conn.setRequestProperty("Content-Type", "application/xml");
                conn.setRequestProperty("Accept", "application/xml");
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();
                OutputStream os = conn.getOutputStream();
                transformer.transform(source, new StreamResult(os));
                os.flush();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {
        MyClient client = new MyClient();
//        client.add_animal(new Animal("Bob", "amazon", "Arapaima gigas", UUID.randomUUID()));
//        client.remove_animals();
//        client.update_animals();
//        client.add_animal_with_id(new Animal("Bob", "amazon", "Arapaima gigas", UUID.randomUUID()), UUID.randomUUID().toString());
//        client.remove_animal_with_id(new Animal(UUID.randomUUID().toString());
//        client.update_animal_with_id(new Animal("Bob", "amazon", "Arapaima gigas", UUID.randomUUID()), UUID.randomUUID().toString());
//        client.find_by_name("Tic");
//        client.find_at(new Position(49.305d, 1.2157357d));
//        client.find_near(new Position(49.305d, 1.2157357d));
        //client.senario();
        //MyClient client = new MyClient();
        client.get_animals();
        //client.remove_animals();
        //client.get_animals();

       // client.add_cage(new Cage("Rouen", new Position(49.443889,1.103333), 1000));
        client.add_animal(new Animal("Panda", "Rouen", "Panda"));

    }

}
