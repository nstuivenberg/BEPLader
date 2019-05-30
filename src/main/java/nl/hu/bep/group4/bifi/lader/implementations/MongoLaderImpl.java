package nl.hu.bep.group4.bifi.lader.implementations;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.lader.MongoLader;
import nl.hu.bep.group4.bifi.model.Factuur;
import nl.hu.bep.group4.bifi.model.FactuurRegel;
import nl.hu.bep.group4.bifi.model.FactuurRegel.BTWcode;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoLaderImpl implements MongoLader {
	MongoDatabase db = null;

	public MongoCollection<Document> connectToMongoDB() {
		MongoCollection<Document> BEPBifi = null;
		String database = "BEPBifi";
		
		MongoClientURI uri = new MongoClientURI("mongodb+srv://dbUser:112112@cluster0-vk3z3.mongodb.net/test?retryWrites=true");
		MongoClient mongoClient = new MongoClient(uri);
		db = mongoClient.getDatabase(database);
		BEPBifi = db.getCollection("BEPBifi");
		return BEPBifi;
	}

	/**
	 * Accepteert maandnummer (0-11)
	 */
	@Override
	public List<Factuur> getFacturenVoorMaand(int maandNummer) throws GarbageDataException {
		
		List<Factuur> facturen = new ArrayList<>();
		
		MongoCollection<Document> collection = connectToMongoDB();
		Iterator<Document> it = collection.find().iterator();
		while(it.hasNext()) {
			
			Document factuurVanMongo = it.next();
			
			Date date = factuurVanMongo.getDate("date");
			Calendar factuurDate = Calendar.getInstance();
			factuurDate.setTime(date);
			
			if (factuurDate.get(Calendar.MONTH) == maandNummer) {
				Factuur factuur = new Factuur();
				factuur.setDatumtijd(date.toString());
				factuur.setFactuurNummer(factuurVanMongo.getInteger("invoiceId"));
				factuur.setOpmerking(factuurVanMongo.getString("note"));
				factuur.setContactPersoon(new Persoon(factuurVanMongo.getInteger("personId")));
				factuur.setKlant(new Klant(factuurVanMongo.getInteger("customerId")));
				
				
				List<FactuurRegel> factuurRegels = new ArrayList<>();
				
				List<Document> linesOfFactuur = factuurVanMongo.getList("invoiceLines", Document.class);
				for(Document line : linesOfFactuur) {
					FactuurRegel factuurRegel = new FactuurRegel();
					factuurRegel.setAantal(line.getInteger("quantity"));
					factuurRegel.setProductID(line.getInteger("productId"));
					factuurRegel.setProductNaam(line.getString("productName"));
					Object prijsObj = line.get("totalPrice");
					if(prijsObj instanceof Double) {
						factuurRegel.setTotaalprijsExBTW((Double)prijsObj);	
					} else if(prijsObj instanceof Integer) {
						factuurRegel.setTotaalprijsExBTW(((Integer)prijsObj).doubleValue());
					} else {
						throw new GarbageDataException("totalPrice heeft als type "+prijsObj.getClass().getName());
					}
					
					String btwCode = line.getString("btwCode").toLowerCase();
					switch (btwCode) {
						case "hoog":
						case "high":
							factuurRegel.setBtwCode(BTWcode.HOOG);
							break;
						case "laag":
						case "low":
							factuurRegel.setBtwCode(BTWcode.LAAG);
							break;
						case "geen":
						case "none":
							factuurRegel.setBtwCode(BTWcode.GEEN);
							break;
						default:
							throw new GarbageDataException("Onbekende btwCode " + btwCode);
					}
					factuurRegels.add(factuurRegel);	
				}
				factuur.setFactuurregels(factuurRegels);
				facturen.add(factuur);
			}
		}
		for(Factuur f : facturen) {
			System.out.println(f.toString());
		}
		System.out.println("End of getFacturenVoorMaand()");
		return facturen;	
	}
	
}