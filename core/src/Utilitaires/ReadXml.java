package Utilitaires;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;

public class ReadXml 
{
	File xml_file_;// = new File("/Users/mkyong/staff.xml");
	DocumentBuilderFactory doc_builder_factory_;// = DocumentBuilderFactory.newInstance();
	DocumentBuilder doc_builder_;// = dbFactory.newDocumentBuilder();
	Document doc_;// = dBuilder.parse(fXmlFile);
	
	public ReadXml(String path)
	{
		try
		{
			xml_file_ = new File("/Users/mkyong/staff.xml");
			doc_builder_factory_= DocumentBuilderFactory.newInstance();
			doc_builder_ = doc_builder_factory_.newDocumentBuilder();
			doc_ = doc_builder_.parse(xml_file_);
		}
		catch(Exception e)
		{
			System.err.println("Erreur chargement xml file");
		}
	}

	//recupere tout les elements du noeud
	public NodeList read_Node(String node)
	{
		if(doc_ != null)
		{
			NodeList node_list = doc_.getElementsByTagName(node);
			return node_list;
		}
		
		return null;
	}
	
	//retourne le nombre de sous element du noeud
	public int node_Item_Number(String node)
	{
		if(doc_ != null)
		{
			NodeList node_list = doc_.getElementsByTagName(node);
			if(node_list != null)
				return node_list.getLength();
		}
		
		return -1;
	}
	
	 public static void main(String argv[]) 
	 {
		 
		 ReadXml file = new ReadXml("../android/assets/units.xml");
		 System.out.println(file.node_Item_Number("tower"));
	 }

}
