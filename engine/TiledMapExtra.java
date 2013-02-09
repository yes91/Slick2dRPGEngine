/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author redblast71
 */
public class TiledMapExtra extends TiledMap {
    
    public ArrayList<Event> eventGroups = new ArrayList<>();

    public TiledMapExtra(String ref, String param2) throws SlickException {
        super(ref, param2);
        load(ResourceLoader.getResourceAsStream(ref));
    }

    private void load(InputStream in) throws SlickException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            factory.setValidating(false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(String publicId,
                        String systemId) throws SAXException, IOException {
                    return new InputSource(
                            new ByteArrayInputStream(new byte[0]));
                }
            });

            Document doc = builder.parse(in);
            org.w3c.dom.Element docElement = doc.getDocumentElement();

            NodeList objectGroupNodes = docElement
                    .getElementsByTagName("eventgroup");
            /*for (int i = 0; i < objectGroupNodes.getLength(); i++) {
                org.w3c.dom.Element current = (org.w3c.dom.Element) objectGroupNodes.item(i);
                NodeList events = current.getElementsByTagName("event");
                //Event e = new Event();
                for(int j = 0; j < events.getLength(); j++){
                    events.item(j);
                }
            }*/
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Log.error(e);
            throw new SlickException("Failed to parse tilemap", e);
        }
    }
}
