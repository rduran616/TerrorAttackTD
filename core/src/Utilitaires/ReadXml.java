package Utilitaires;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//import com.badlogic.gdx.utils.XmlReader.Element;

import java.io.File;

public class ReadXml 
{
	File xml_file_;// = new File("/Users/mkyong/staff.xml");
	DocumentBuilderFactory doc_builder_factory_;// = DocumentBuilderFactory.newInstance();
	DocumentBuilder doc_builder_;// = dbFactory.newDocumentBuilder();
	Document doc_;// = dBuilder.parse(fXmlFile);
	
	public ReadXml(String path)
	{
		if(path!=null)
		{
			try
			{
				xml_file_ = new File(path);
				doc_builder_factory_= DocumentBuilderFactory.newInstance();
				doc_builder_ = doc_builder_factory_.newDocumentBuilder();
				doc_ = doc_builder_.parse(xml_file_);
			}
			catch(Exception e)
			{
				System.err.println("Erreur chargement xml file");
			}
		}
	}

	//recupere tous les elements du noeud
	public NodeList read_Node(String node)
	{
		if(doc_ != null)
		{
			NodeList node_list = doc_.getElementsByTagName(node);
			return node_list;
		}
		
		return null;
	}
	
	//retourne le nombre d'item avec le nom node
	public int node_Item_Number(String node)
	{
		if(doc_ != null)
		{
			NodeList node_list = doc_.getElementsByTagName(node);
			if(node_list != null)
			{
				return node_list.getLength();
			}
		}
		
		return -1;
	}
	
	//retourne le nombre de sous elemnet du noeud node
	public int node_Item_Child_Number(String node)
	{
		int cpt =0;
		if(doc_ != null)
		{
			NodeList node_list = doc_.getElementsByTagName(node);
			if(node_list != null)
			{
				for(int i=0; i<node_list.getLength(); i++)
				{
					Node elem = node_list.item(0).getFirstChild();
					while(elem != null)
					{
						if(elem.getNodeType()== Node.ELEMENT_NODE)
						{
							cpt++;
						}
						
						elem = elem.getNextSibling();
					}
				}
				
				return cpt;
			}
		}
		
		return -1;
	}
	
	//methode qui retourne la valeur de la propri�t� value
	public String value(String id, String value )
	{
		if(doc_ != null)
		{
			Element element = doc_.getElementById(id);
			return element.getAttribute(value);
		}
		
		return null;
	}
	
	//test
	 public static void main(String argv[]) 
	 {
		 
		 ReadXml file = new ReadXml("../android/assets/units.xml");
		 System.out.println(file.node_Item_Child_Number("tower"));
		 System.out.println(file.node_Item_Child_Number("mobs"));
	 }

}