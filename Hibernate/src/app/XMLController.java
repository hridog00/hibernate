package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLController {
	public XMLController() {
		
	}
		public static void generar() throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, TransformerException {
		
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			//Document doc = db.parse(new FileInputStream(new File("in.xml")));
			
			
			Element rootElement = doc.createElement("Trabajadores");
			doc.appendChild(rootElement);
			guardar(doc);
			
		
		

		}
		public static void guardar(Document doc) throws TransformerException {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("resources/errores.xml"));
			//StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
		}
		public static void add(String id,String nombre, String ap1, String ap2, String cat, String empresa) throws SAXException, IOException, ParserConfigurationException, TransformerException {
			File inputFile = new File("resources/Errores.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse("resources/Errores.xml");
	        Node root = doc.getFirstChild();
	        
	        Element staff = doc.createElement("Trabajador");
			root.appendChild(staff);
			
			Attr attr = doc.createAttribute("id");
			attr.setValue(id);
			staff.setAttributeNode(attr);

			// shorten way
			// staff.setAttribute("id", "1");

		
			Element nombreElement = doc.createElement("Nombre");
			nombreElement.appendChild(doc.createTextNode(nombre));
			staff.appendChild(nombreElement);

			
			Element primerAp = doc.createElement("PrimerApellido");
			primerAp.appendChild(doc.createTextNode(ap1));
			staff.appendChild(primerAp);

			// nickname elements
			Element secAp = doc.createElement("SegundoApellido");
			secAp.appendChild(doc.createTextNode(ap2));
			staff.appendChild(secAp);

			// salary elements
			Element categoria = doc.createElement("Categoria");
			categoria.appendChild(doc.createTextNode(cat));
			staff.appendChild(categoria);
			
			Element emp = doc.createElement("Empresa");
			emp.appendChild(doc.createTextNode(empresa));
			staff.appendChild(emp);
			
			
			guardar(doc);
	        
		}
	
}
